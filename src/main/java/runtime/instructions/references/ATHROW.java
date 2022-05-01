package runtime.instructions.references;

import jNative.java.lang.JStackTraceElement;
import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.JThread;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.StringPool;

public class ATHROW extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        JObject exception = frame.getOperandStack().popRef();
        if (exception == null) {
            throw new NullPointerException();
        }
        JThread jThread = frame.getjThread();
        if (!handleException(jThread, exception)) {
            handleUncaughtException(jThread, exception);
        }
    }

    /**
     * 一层一层处理，查询那一层调用者处理了该异常
     *
     * @param jThread
     * @param exception
     * @return
     */
    private boolean handleException(JThread jThread, JObject exception) {
        do {
            Frame frame = jThread.topFrame();
            int PC = frame.getNextPC() - 1;
            int handlePC = frame.getjMethod().findExceptionHandlerPC(exception.getjClass(), PC);
            if (handlePC > 0) {
                OperandStack stack = frame.getOperandStack();
                stack.clear();
                stack.pushRef(exception);
                frame.setNextPC(handlePC);
                return true;
            }

            jThread.popFrame();
        } while (jThread.isStackEmpty());

        return false;
    }

    /**
     * 打印栈信息
     *
     * @param jThread
     * @param exception
     */
    private void handleUncaughtException(JThread jThread, JObject exception) {
        jThread.clearStack();
        JObject jstring = exception.getRefVar("detailMessage", "Ljava/lang/String;");
        String errMsg = StringPool.getRealString(jstring);
        System.out.println(exception.getjClass().getJavaName() + ": " + errMsg);
        // 获取虚拟机栈信息, 通过本地方法 fillStackTrac 获取
        JStackTraceElement[] stackTraceElements = (JStackTraceElement[])exception.getExtra();
        for(JStackTraceElement stackTraceElement : stackTraceElements) {
            System.out.println("\t" + stackTraceElement.toString());
        }
    }
}
