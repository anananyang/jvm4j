package runtime.rtda.share.heap.rtcp.ref;

import classFile.constants.ConstantMemberrefInfo;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;

public class MemberRef extends SymRef {
    // 字段名或者方法名
    protected String name;
    protected String descriptor;

    public MemberRef(RuntimeConstantPool constantPool) {
        super(constantPool);
    }

    void copyMemberRefInfo(ConstantMemberrefInfo memberrefInfo) {
        this.className = memberrefInfo.getClassName();
        this.name = memberrefInfo.getName();
        this.descriptor = memberrefInfo.getDescriptor();
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }
}
