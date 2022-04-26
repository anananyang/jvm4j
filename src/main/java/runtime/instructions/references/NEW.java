package runtime.instructions.references;

import runtime.instructions.base.Index16Instruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.ClassRef;

public class NEW extends Index16Instruction {

    /**
     * 1、根据 index 从常量池拿到一个类符号引用。
     * 2、解析类符号引用，获取到类数据
     * 3、创建对象
     * 4、将对象引用推入栈顶
     *
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        ClassRef classRef = this.getClassRef(frame);
        JClass jClass = classRef.resolveClass();
        if(jClass.isAbstract() || jClass.isInterface()) {
            throw new RuntimeException("java.lang.InstantiationError");
        }
        JObject jObject = jClass.newObject();
        frame.getOperandStack().pushRef(jObject);
    }

    private ClassRef getClassRef(Frame frame) {
        // 当前运行的方法
        JMethod method = frame.getjMethod();
        JClass jClass = method.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (ClassRef) constantPool.getValueByIndex(index);
    }
}
