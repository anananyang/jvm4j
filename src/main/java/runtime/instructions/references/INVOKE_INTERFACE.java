package runtime.instructions.references;

import runtime.instructions.ByteCodeReader;
import runtime.instructions.Instruction;
import runtime.instructions.base.InvokeMethodLogic;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;
import runtime.rtda.share.heap.rtcp.ref.InterfaceMethodRef;
import runtime.rtda.share.heap.rtcp.ref.MethodRef;

public class INVOKE_INTERFACE implements Instruction {
    private int index;
    private int count;
    private int zero;


    @Override
    public void fetchOperands(ByteCodeReader reader) {
        this.index = reader.readUint16();
        this.count = reader.readUint8();
        this.zero = reader.readUint8();
    }

    @Override
    public void execute(Frame frame) {
        InterfaceMethodRef methodRef = this.getInterfaceMethodRef(frame);
        JMethod resolvedMethod = methodRef.resolveInterfaceMethod();
        if(resolvedMethod.isStatic() || resolvedMethod.isPrivate()) {
            throw new IncompatibleClassChangeError();
        }
        // 从操作数栈弹出 this 指针
        JObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            throw new NullPointerException();
        }
        if(!ref.getjClass().isImplements(methodRef.resolveClass())) {
            throw new IncompatibleClassChangeError();
        }
        JMethod methodToBeInvoked = ref.getjClass().lookupMethod(methodRef.getName(), methodRef.getDescriptor());
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }
        if(!methodToBeInvoked.isPulic()) {
            throw new IllegalAccessError();
        }
        InvokeMethodLogic.invokeMethod(frame, methodToBeInvoked);

    }

    private InterfaceMethodRef getInterfaceMethodRef(Frame frame) {
        JMethod jMethod = frame.getjMethod();
        JClass jClass = jMethod.getjClass();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        return (InterfaceMethodRef) constantPool.getValueByIndex(index);
    }

}
