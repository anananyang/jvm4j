package runtime.instructions.base;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.rtda.priv.Frame;

/**
 * 存储和加载类指令需要索引存取局部变量表，索引由单字节操作数给出。
 */
public abstract class Index8Instruction implements Instruction {

    protected int index;

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        this.index = reader.readUint8();
    }

    @Override
    public abstract void execute(Frame frame);

    public void setIndex(int index) {
        this.index = index;
    }
}
