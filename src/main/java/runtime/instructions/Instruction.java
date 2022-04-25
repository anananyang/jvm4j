package runtime.instructions;

import runtime.rtda.priv.Frame;

public interface Instruction {

    /**
     * 从字节码中提取操作数
     */
    void fetchOperands(ByteCodeReader reader);

    /**
     * 执行指令逻辑
     *
     * @param frame
     */
    void execute(Frame frame);
}
