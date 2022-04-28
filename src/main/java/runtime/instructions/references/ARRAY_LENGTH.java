package runtime.instructions.references;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JObject;

public class ARRAY_LENGTH extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        JObject arrayRef = frame.getOperandStack().popRef();
        if (arrayRef == null) {
            throw new NullPointerException();
        }
        int len = arrayRef.getArrayLength();
        frame.getOperandStack().pushInt(len);
    }
}
