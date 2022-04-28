package runtime.instructions.store.xstore;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JObject;

public class DASTORE extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double val = stack.popDouble();
        int index = stack.popInt();
        JObject arrRef = stack.popRef();
        if (arrRef == null) {
            throw new NullPointerException();
        }
        Double[] array = arrRef.getDoubleArray();
        if (index < 0 || index >= array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        array[index] = val;
    }
}
