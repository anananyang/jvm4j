package runtime.rtda.share.heap.rtcp.ref;

import classFile.constants.ConstantClassInfo;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;

public class ClassRef extends SymRef {

    public ClassRef(RuntimeConstantPool constantPool, ConstantClassInfo constantClassInfo) {
        super(constantPool, constantClassInfo.getClassName());
    }


}
