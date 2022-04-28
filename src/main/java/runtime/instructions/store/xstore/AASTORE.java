package runtime.instructions.store.xstore;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JObject;

public class AASTORE extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        JObject val = stack.popRef();
        int index = stack.popInt();
        JObject arrRef = stack.popRef();
        if (arrRef == null) {
            throw new NullPointerException();
        }
        JObject[] array = arrRef.getRefArray();
        if (index < 0 || index >= array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        array[index] = val;
    }
}