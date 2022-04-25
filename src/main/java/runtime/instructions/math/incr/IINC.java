package runtime.instructions.math.incr;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.LocalVariableTable;

/**
 * 从局部变量表读取一个变量，给这个变量加上一个常量值
 */
public class IINC implements Instruction {
    private int index;
    private int constant;

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        this.index = reader.readUint8();
        this.constant = reader.readInt8();
    }

    @Override
    public void execute(Frame frame) {
        LocalVariableTable table = frame.getLocalVaribleTable();
        int val = table.getInt(index);
        val += constant;
        table.setInt(index, val);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setConstant(int constant) {
        this.constant = constant;
    }
}
