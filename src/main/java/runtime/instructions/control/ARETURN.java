package runtime.instructions.control;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.JThread;
import runtime.rtda.share.heap.JObject;

public class ARETURN extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        JThread jThread = frame.getjThread();
        // 与参数中 frame 是同一个
        Frame curFrame = jThread.popFrame();
        Frame invokerFrame = jThread.topFrame();
        JObject returnVal = curFrame.getOperandStack().popRef();
        invokerFrame.getOperandStack().pushRef(returnVal);
    }
}
