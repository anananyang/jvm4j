package runtime.rtda.share.heap.rtcp.ref;

import classFile.constants.ConstantMemberrefInfo;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;

public class InterfaceMethodRef extends MemberRef {

    // 缓存解析后的方法
    private JMethod method;

    public InterfaceMethodRef(RuntimeConstantPool constantPool, ConstantMemberrefInfo memberrefInfo) {
        super(constantPool);
        copyMemberRefInfo(memberrefInfo);
    }

    public JMethod resolveInterfaceMethod() {
        if (method == null) {
            resolveInterfaceMethodRef();
        }
        return method;
    }

    private void resolveInterfaceMethodRef() {
        // 当前类
        JClass curClass = constantPool.getjClass();
        // 方法所属的类
        JClass methodClass = resolveClass();
        // 如果不是接口的方法
        if (!methodClass.isInterface()) {
            throw new IncompatibleClassChangeError();
        }
        JMethod jMethod = methodClass.lookupMethod(name, descriptor);
        if (jMethod == null) {
            throw new NoSuchMethodError();
        }
        // 判断该方法是否对当前类可访问
        if (!jMethod.isAccessibleTo(curClass)) {
            throw new IllegalAccessError();
        }
        this.method = jMethod;
    }
}
