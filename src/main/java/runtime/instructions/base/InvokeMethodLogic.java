package runtime.instructions.base;

import runtime.rtda.Slot;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.JThread;
import runtime.rtda.share.heap.JMethod;

public abstract class InvokeMethodLogic {
    /**
     * 方法调用约定
     * 1、构造一个新的栈帧
     * 2、如果方法有参数，则从当前栈栈的操作数栈中 pop 参数，并设置到新栈帧的局部变量表中
     *
     * @param frame
     * @param jMethod
     */
    public static void invokeMethod(Frame frame, JMethod jMethod) {
        // 暂时跳过本地方法调用
        if(jMethod.isNative()) {
            if("registerNatives".equals(jMethod.getName())) {
                frame.getjThread().popFrame();
            } else {
                System.out.println(String.format("native method: %s.%s%s",
                        jMethod.getjClass().getThisClassName(),
                        jMethod.getName(),
                        jMethod.getDescriptor()));
            }
            return;
        }
        JThread jThread = frame.getjThread();
        Frame newFrame = jThread.newFrame(jMethod);
        setArgToLocalVarTable(jMethod, frame, newFrame);
        jThread.pushFrame(newFrame);
    }

    private static void setArgToLocalVarTable(JMethod jMethod, Frame frame, Frame newFrame) {
        int argCount = jMethod.getArgSlotCount();
        if (argCount == 0) {
            return;
        }
        for (int i = argCount - 1; i >= 0; i--) {
            // 从当前的操作数栈中取出参数
            Slot slot = frame.getOperandStack().popSlot();
            // 将参数设置到新栈帧的局部变量表
            newFrame.getLocalVaribleTable().setSlot(i, slot);
        }

    }
}
