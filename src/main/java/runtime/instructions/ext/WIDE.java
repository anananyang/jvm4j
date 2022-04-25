package runtime.instructions.ext;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.instructions.loads.aload.ALOAD;
import runtime.instructions.loads.dload.DLOAD;
import runtime.instructions.loads.fload.FLOAD;
import runtime.instructions.loads.iload.ILOAD;
import runtime.instructions.loads.lload.LLOAD;
import runtime.instructions.math.incr.IINC;
import runtime.instructions.store.astore.ASTORE;
import runtime.instructions.store.dstore.DSTORE;
import runtime.instructions.store.fstore.FSTORE;
import runtime.instructions.store.istore.ISTORE;
import runtime.instructions.store.lstore.LSTORE;
import runtime.rtda.priv.Frame;

/**
 * wide 指令改变其他指令的行为
 */
public class WIDE implements Instruction {
    /**
     * 对原来的指令进行扩展，大部分的扩展都是操作数，比如操作数由1一个字节变成2个字节
     */
    private Instruction instruction;

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        int opCode = reader.readUint8();
        switch (opCode) {
            case 0x15:
                ILOAD iload = new ILOAD();
                iload.setIndex(reader.readUint16());
                this.instruction = iload;
                break;

            case 0x16:
                LLOAD lload = new LLOAD();
                lload.setIndex(reader.readUint16());
                this.instruction = lload;
                break;

            case 0x17:
                FLOAD fload = new FLOAD();
                fload.setIndex(reader.readUint16());
                this.instruction = fload;
                break;

            case 0x18:
                DLOAD dload = new DLOAD();
                dload.setIndex(reader.readUint16());
                this.instruction = dload;
                break;

            case 0x19:
                ALOAD aload = new ALOAD();
                aload.setIndex(reader.readUint16());
                this.instruction = aload;
                break;

            case 0x36:
                ISTORE istore = new ISTORE();
                istore.setIndex(reader.readUint16());
                this.instruction = istore;
                break;

            case 0x37:
                LSTORE lstore = new LSTORE();
                lstore.setIndex(reader.readUint16());
                this.instruction = lstore;
                break;

            case 0x38:
                FSTORE fstore = new FSTORE();
                fstore.setIndex(reader.readUint16());
                this.instruction = fstore;
                break;

            case 0x39:
                DSTORE dstore = new DSTORE();
                dstore.setIndex(reader.readUint16());
                this.instruction = dstore;
                break;

            case 0x3A:
                ASTORE astore = new ASTORE();
                astore.setIndex(reader.readUint16());
                this.instruction = astore;
                break;

            case 0x84:
                IINC iinc = new IINC();
                iinc.setIndex(reader.readUint16());
                iinc.setConstant(reader.readUint16());
                this.instruction = iinc;
                break;

            case 0xa9:
                throw new RuntimeException("Unsupported opcode: 0xa9!");
        }
    }

    @Override
    public void execute(Frame frame) {
        instruction.execute(frame);
    }
}
