package runtime.instructions.loads.xload;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JObject;

public class BALOAD extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int index = stack.popInt();
        JObject arrRef = stack.popRef();
        if (arrRef == null) {
            throw new NullPointerException();
        }
        Byte[] array = arrRef.getByteArray();
        if (index < 0 || index >= array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        stack.pushInt(array[index]);
    }
}
