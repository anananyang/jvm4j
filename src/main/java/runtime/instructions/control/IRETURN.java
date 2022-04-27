package runtime.instructions.control;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.JThread;

public class IRETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        JThread  jThread = frame.getjThread();
        // 与参数中 frame 是同一个
        Frame curFrame = jThread.popFrame();
        Frame invokerFrame = jThread.topFrame();
        int returnVal = curFrame.getOperandStack().popInt();
        invokerFrame.getOperandStack().pushInt(returnVal);
    }
}
