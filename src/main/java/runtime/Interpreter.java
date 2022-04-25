package runtime;

import classFile.MemberInfo;
import classFile.attributes.CodeAttributeInfo;
import classFile.emu.AttributeType;
import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.instructions.InstructionFactory;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.JThread;

public class Interpreter {


    public static void interpret(MemberInfo memberInfo) {
        CodeAttributeInfo codeAttribute = (CodeAttributeInfo) memberInfo.getFirstAttrByType(AttributeType.Code);
        if (codeAttribute == null) {
            throw new RuntimeException("code attribute not found");
        }
        int maxLocal = codeAttribute.getMaxLocal();
        int maxStack = codeAttribute.getMaxStack();

        JThread jThread = new JThread();
        Frame frame = new Frame(jThread, maxLocal, maxStack);
        jThread.pushFrame(frame);
        try {
            loop(jThread, codeAttribute.getCode());   // 目前没有执行 ret 指令，所以会抛出异常
        } catch (Exception e) {
            e.printStackTrace();
            // 打印本地局部变量表的内容
            frame.getLocalVaribleTable().printSlots();
        }
    }

    private static void loop(JThread jThread, byte[] byteCode) {
        Frame frame = jThread.curFrame();
        ByteCodeReader byteCodeReader = new ByteCodeReader(byteCode);
        while (true) {
            int pc = frame.getNextPC();
            jThread.setPc(pc);
            byteCodeReader.setPC(pc);
            // 读取字节码标识符
            int opCode = byteCodeReader.readUint8();
            // 获取指令
            Instruction instruction = InstructionFactory.get(opCode);
            // 读取操作数
            instruction.fetchOperands(byteCodeReader);
            // 重新设置 PC，PC 在 reader 之后已经重新置位
            frame.setNextPC(byteCodeReader.getPc());
            // 执行指令
            instruction.execute(frame);

            System.out.println(String.format("pc: %d, instruction: %s", pc, instruction.getClass().getSimpleName()));
        }
    }

}
