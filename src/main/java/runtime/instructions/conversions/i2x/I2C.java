package runtime.instructions.conversions.i2x;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class I2C extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack =  frame.getOperandStack();
        int val = stack.popInt();
        char result = (char)val;
        stack.pushInt(result);
    }
}
