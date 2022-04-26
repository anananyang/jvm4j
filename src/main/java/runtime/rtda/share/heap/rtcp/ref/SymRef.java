package runtime.rtda.share.heap.rtcp.ref;

import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JClassLoader;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;

public class SymRef {
    protected RuntimeConstantPool constantPool;
    // 类的完全限定名
    protected String className;
    protected JClass jClass;

    public SymRef(RuntimeConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public SymRef(RuntimeConstantPool constantPool, String className) {
        this.constantPool = constantPool;
        this.className = className;
    }

    public JClass resolveClass() {
        if(this.jClass == null) {
            resolveClassRef();
        }
        return jClass;
    }

    private void resolveClassRef() {
        JClass d = constantPool.getjClass();
        JClassLoader classLoader = d.getLoader();
        JClass c = classLoader.loadClass(className);
        // 判断 c 是否可以被 d 访问
        if(!c.isAccessibleTo(d)) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }
        this.jClass = c;
    }

}
