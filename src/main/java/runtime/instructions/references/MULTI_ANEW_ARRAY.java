package runtime.instructions.references;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.ClassRef;

public class MULTI_ANEW_ARRAY implements Instruction {
    // 类符号引用在常量池的索引
    private int index;
    // 数组维度
    private int dimensions;

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        this.index = reader.readUint16();
        this.dimensions = reader.readUint8();
    }

    @Override
    public void execute(Frame frame) {
        ClassRef classRef = getClassRef(frame);
        JClass arrClass = classRef.resolveClass();
        int[] counts = popAndCheckCount(frame);
        JObject arr = arrClass.newMultiDimensionalArray(counts, 0);
        frame.getOperandStack().pushRef(arr);
    }

    private ClassRef getClassRef(Frame frame) {
        // 当前运行的方法
        JMethod method = frame.getjMethod();
        JClass jClass = method.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (ClassRef) constantPool.getValueByIndex(index);
    }

    /**
     * 计算每个纬度的长度
     *
     * @param frame
     * @return
     */
    private int[] popAndCheckCount(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int[] counts = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            counts[i] = stack.popInt();
            if (counts[i] < 0) {
                throw new NegativeArraySizeException();
            }
        }
        return counts;
    }
}
