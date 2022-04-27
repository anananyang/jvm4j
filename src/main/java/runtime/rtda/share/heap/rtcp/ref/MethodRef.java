package runtime.rtda.share.heap.rtcp.ref;

import classFile.constants.ConstantMemberrefInfo;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JField;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;

public class MethodRef extends MemberRef {

    // 缓存解析后的方法
    private JMethod method;

    public MethodRef(RuntimeConstantPool constantPool, ConstantMemberrefInfo memberrefInfo) {
        super(constantPool);
        copyMemberRefInfo(memberrefInfo);
    }

    public JMethod resolveMethod() {
        if (method == null) {
            resolveMethodRef();
        }
        return method;
    }


    private void resolveMethodRef() {
        // 当前类
        JClass curClass = constantPool.getjClass();
        // 方法所属的类
        JClass methodClass = resolveClass();
        // 无法直接调用接口的方法
        if (methodClass.isInterface()) {
            throw new IncompatibleClassChangeError();
        }
        JMethod jMethod = methodClass.lookupMethod(name, descriptor);
        if(jMethod == null) {
            throw new NoSuchMethodError();
        }
        // 判断该方法是否对当前类可访问
        if(!jMethod.isAccessibleTo(curClass)) {
            throw new IllegalAccessError();
        }
        this.method = jMethod;
    }

}
