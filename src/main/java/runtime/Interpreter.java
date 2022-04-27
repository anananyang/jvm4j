package runtime;

import classFile.MemberInfo;
import classFile.attributes.CodeAttributeInfo;
import eum.AttributeType;
import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.instructions.InstructionFactory;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.JThread;
import runtime.rtda.share.heap.JMethod;

public class Interpreter {

    /**
     * 为指令集测试专用
     *
     * @param memberInfo
     */
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
        Frame frame = jThread.topFrame();
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

            System.out.println(String.format("pc: %d, instruction: %s", pc, instruction.getClass().getSimpleName()));

            // 执行指令
            instruction.execute(frame);
        }
    }


    public static void interpret(JMethod jMethod, boolean logInstructionInfo) {
        JThread jThread = new JThread();
        Frame frame = jThread.newFrame(jMethod);
        jThread.pushFrame(frame);
        try {
            loop(jThread, logInstructionInfo);   // 目前没有执行 ret 指令，所以会抛出异常
        } catch (Exception e) {
            e.printStackTrace();
            // 打印本地局部变量表的内容
            System.out.println(String.format(">> pc: %d, %s.%s(%s)",
                    jThread.topFrame().getNextPC(),
                    jThread.topFrame().getjMethod().getjClass().getThisClassName(),
                    jThread.topFrame().getjMethod().getName(),
                    jThread.topFrame().getjMethod().getDescriptor()));
            jThread.topFrame().getLocalVaribleTable().printSlots();
        }
    }

    private static void loop(JThread jThread, boolean logInstructionInfo) {
        do {
            Frame frame = jThread.topFrame();
            ByteCodeReader byteCodeReader = frame.getByteCodeReader();
            int pc = frame.getNextPC();
            // 记录一下，在类初始化时可以用于恢复
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
            if(logInstructionInfo) {
                System.out.println(String.format("pc: %d, instruction: %s", pc, instruction.getClass().getSimpleName()));
            }
            // 执行指令
            instruction.execute(frame);

        } while (!jThread.isStackEmpty());
    }


}
