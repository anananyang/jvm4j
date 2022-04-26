package runtime.rtda.share.heap.rtcp.ref;

import classFile.constants.ConstantMemberrefInfo;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JField;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;

public class FieldRef extends MemberRef {
    // 缓存解析后的字段
    private JField field;

    public FieldRef(RuntimeConstantPool constantPool, ConstantMemberrefInfo memberrefInfo) {
        super(constantPool);
        copyMemberRefInfo(memberrefInfo);
    }

    public JField resolveField() {
        if (field == null) {
            resolveFieldRef();
        }
        return field;
    }

    private void resolveFieldRef() {
        // 先解析 className 指向的类
        JClass c = this.resolveClass();
        JField jField = c.lookupField(name, descriptor);
        if(jField == null) {
            throw new NoSuchFieldError();
        }
        // 判断当前类是否有访问权限
        JClass d = constantPool.getjClass();
        if(!jField.isAccessibleTo(d)) {
            throw new IllegalAccessError();
        }
        this.field = jField;
    }
}
