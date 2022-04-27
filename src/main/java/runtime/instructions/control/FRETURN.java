package runtime.instructions.control;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.JThread;

public class FRETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        JThread jThread = frame.getjThread();
        // 与参数中 frame 是同一个
        Frame curFrame = jThread.popFrame();
        Frame invokerFrame = jThread.topFrame();
        float returnVal = curFrame.getOperandStack().popFloat();
        invokerFrame.getOperandStack().pushFloat(returnVal);
    }
}
