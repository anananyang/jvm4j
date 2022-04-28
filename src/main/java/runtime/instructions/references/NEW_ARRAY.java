package runtime.instructions.references;

import eum.ArrayType;
import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JClassLoader;
import runtime.rtda.share.heap.JObject;

/**
 * 创建基础类型数组
 */
public class NEW_ARRAY implements Instruction {
    // 需要创建的数组类型的枚举值
    private int atype;

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        this.atype = reader.readUint8();
    }

    @Override
    public void execute(Frame frame) {
        // 数组的长度
        OperandStack stack = frame.getOperandStack();
        int count = frame.getOperandStack().popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }
        ArrayType arrayType = ArrayType.getByTypeVal(atype);
        if(arrayType == null) {
            throw new RuntimeException("Invalid array type [" + atype + "]");
        }
        JClassLoader loader = frame.getjMethod().getjClass().getLoader();
        JClass jClass = loader.loadClass(arrayType.getTypeDesc());
        JObject array = jClass.newArray(count);
        stack.pushRef(array);
    }
}
