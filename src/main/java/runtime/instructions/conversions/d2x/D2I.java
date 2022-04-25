package runtime.instructions.conversions.d2x;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class D2I extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double val = stack.popDouble();
        int result = Double.valueOf(val).intValue();
        stack.pushInt(result);
    }
}