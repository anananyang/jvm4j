package runtime.rtda.share.heap.rtcp.ref;

import classFile.constants.ConstantMemberrefInfo;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;

public class InterfaceMethodRef extends MemberRef {

    // 缓存解析后的方法
    private JMethod method;

    public InterfaceMethodRef(RuntimeConstantPool constantPool, ConstantMemberrefInfo memberrefInfo) {
        super(constantPool);
        copyMemberRefInfo(memberrefInfo);
    }
}
