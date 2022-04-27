package runtime.instructions.constants;

import eum.ConstantType;
import runtime.instructions.base.Index8Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.rtcp.JConstant;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;

/**
 * 从运行时常量池取出一个常量，压到操作数栈
 */
public class LDC extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        RuntimeConstantPool constantPool = frame.getjMethod().getjClass().getConstantPool();
        ConstantType constantType = constantPool.getTypeByIndex(index);
        switch (constantType) {
            case CONSTANT_Integer:
                stack.pushInt((int) constantPool.getValueByIndex(index));
                break;
            case CONSTANT_Float:
                stack.pushFloat((float) constantPool.getValueByIndex(index));
                break;
            // 字符串、jClass
            default:
                throw new RuntimeException("not support ldc [" + constantType.name() + "]");
        }
    }
}
