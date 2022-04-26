package runtime.rtda.share.heap;
import classFile.MemberInfo;

/**
 * 字段信息
 */
public class JClassMember {
    protected int accessFlags;
    protected String name;
    protected String descriptor;
    protected JClass jClass;


    public JClassMember(JClass jClass, MemberInfo memberInfo) {
        this.jClass = jClass;
        this.name = memberInfo.getName();
        this.descriptor = memberInfo.getDescriptor();
        this.accessFlags = memberInfo.getAccessFlag();
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public JClass getjClass() {
        return jClass;
    }
}
