package runtime.instructions.constants;

import eum.ConstantType;
import runtime.instructions.base.Index8Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JClassLoader;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.StringPool;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.ClassRef;

/**
 * 从运行时常量池取出一个常量，压到操作数栈
 */
public class LDC extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        RuntimeConstantPool constantPool = frame.getjMethod().getjClass().getConstantPool();
        ConstantType constantType = constantPool.getTypeByIndex(index);
        Object val = constantPool.getValueByIndex(index);
        switch (constantType) {
            case CONSTANT_Integer:
                stack.pushInt((int) val);
                break;
            case CONSTANT_Float:
                stack.pushFloat((float) val);
                break;
            // 字符串
            case CONSTANT_String:
                // 从字符串常量池取出字符串对象
                JClassLoader loader = constantPool.getjClass().getLoader();
                JObject jstring = StringPool.getJString(loader, (String) val);
                stack.pushRef(jstring);
                break;
            // 类对象，注意基本类型的类对象通过包装类的 TYPE 字段获取，该字段是 static，可以在 GET_STATIC 指令处理
            case CONSTANT_Class:
                ClassRef classRef = (ClassRef) val;
                JObject classObj = classRef.resolveClass().getClassObj();
                stack.pushRef(classObj);
                break;
            default:
                throw new RuntimeException("not support ldc [" + constantType.name() + "]");
        }
    }
}
