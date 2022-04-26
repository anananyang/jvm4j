package runtime.rtda.share.heap;

import classFile.ClassFile;
import classFile.MemberInfo;
import eum.AccessFlag;
import runtime.rtda.Slot;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;


public class JClass {
    // 16 位访问标志
    private int accessFlags;
    // 当前类的全限定名
    private String thisClassName;
    // 父类的全限定名
    private String superClassName;
    // 实现的所有接口的名称
    private String[] interfaceNames;
    // 运行时常量池
    private RuntimeConstantPool constantPool;
    // 字段信息
    private JField[] fields;
    // 方法信息
    private JMethod[] methods;
    // 类加载器
    private JClassLoader loader;
    // 父类
    private JClass superClass;
    private JClass[] interfaces;
    // 实例变量占据的空间大小
    private int instanceSlotCount;
    // 静态变量占据的空间大小
    private int staticSlotCount;
    // 存放静态变量
    private Slots staticVars;

    public JClass(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlag();
        this.thisClassName = classFile.getClassName();
        this.superClassName = classFile.getSuperName();
        this.interfaceNames = classFile.getInterfaceNames();
        this.constantPool = new RuntimeConstantPool(this, classFile.getConstantPool());
        this.fields = getFields(classFile.getFileds());
        this.methods = getMethods(classFile.getMethods());
    }

    private JField[] getFields(MemberInfo[] memberInfos) {
        if (memberInfos == null) {
            return null;
        }
        int size = memberInfos.length;
        JField[] fields = new JField[size];
        for (int i = 0; i < memberInfos.length; i++) {
            fields[i] = new JField(this, memberInfos[i]);
        }
        return fields;
    }

    private JMethod[] getMethods(MemberInfo[] memberInfos) {
        if (memberInfos == null) {
            return null;
        }
        int size = memberInfos.length;
        JMethod[] methods = new JMethod[size];
        for (int i = 0; i < memberInfos.length; i++) {
            methods[i] = new JMethod(this, memberInfos[i]);
        }
        return methods;
    }

    public String getThisClassName() {
        return thisClassName;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String[] getInterfaceNames() {
        return interfaceNames;
    }

    public JClass getSuperClass() {
        return superClass;
    }

    public JClass[] getInterfaces() {
        return interfaces;
    }

    public int getInstanceSlotCount() {
        return instanceSlotCount;
    }

    public int getStaticSlotCount() {
        return staticSlotCount;
    }

    public JField[] getFields() {
        return fields;
    }

    public JMethod[] getMethods() {
        return methods;
    }

    public Slots getStaticVars() {
        return staticVars;
    }

    public RuntimeConstantPool getConstantPool() {
        return constantPool;
    }

    public JClassLoader getLoader() {
        return loader;
    }

    public void setLoader(JClassLoader loader) {
        this.loader = loader;
    }

    public void setSuperClass(JClass superClass) {
        this.superClass = superClass;
    }

    public void setInterfaces(JClass[] interfaces) {
        this.interfaces = interfaces;
    }

    public void setInstanceSlotCount(int instanceSlotCount) {
        this.instanceSlotCount = instanceSlotCount;
    }

    public void setStaticVars(Slots staticVars) {
        this.staticVars = staticVars;
    }

    public void setStaticSlotCount(int staticSlotCount) {
        this.staticSlotCount = staticSlotCount;
    }

    public boolean isPulic() {
        return (accessFlags & AccessFlag.ACC_PUBLIC.getValue()) != 0;
    }

    public boolean isSynthetic() {
        return (accessFlags & AccessFlag.ACC_SYNTHETIC.getValue()) != 0;
    }

    public boolean isFinal() {
        return (accessFlags & AccessFlag.ACC_FINAL.getValue()) != 0;
    }

    public boolean isSuper() {
        return (accessFlags & AccessFlag.ACC_SUPER.getValue()) != 0;
    }

    public boolean isInterface() {
        return (accessFlags & AccessFlag.ACC_INTERFACE.getValue()) != 0;
    }

    public boolean isAbstract() {
        return (accessFlags & AccessFlag.ACC_ABSTRACT.getValue()) != 0;
    }

    public boolean isAnnotation() {
        return (accessFlags & AccessFlag.ACC_ANNOTATION.getValue()) != 0;
    }

    public boolean isEnum() {
        return (accessFlags & AccessFlag.ACC_ENUM.getValue()) != 0;
    }

    public boolean isAccessibleTo(JClass other) {
        if (isPulic()) {
            return true;
        }
        // 判断包访问权限
        return isSamePackage(other);
    }

    /**
     * 判断是否在同一个包内
     *
     * @param other
     * @return
     */
    public boolean isSamePackage(JClass other) {
        String packageName = this.getPakcageName();
        String otherPackageName = other.getPakcageName();
        return packageName.equals(otherPackageName);
    }


    public String getPakcageName() {
        String className = thisClassName;
        int idx = className.lastIndexOf("/");
        if (idx >= 0) {
            return className.substring(0, idx);
        }
        // 定义在默认包中
        return "";
    }

    public JField lookupField(String name, String descriptor) {
        // 先搜索当前类
        JField field = lookupThisClassField(name, descriptor);
        if (field != null) {
            return field;
        }
        field = lookupInterfacesField(name, descriptor);
        if (field != null) {
            return field;
        }
        return lookupSuperClassField(name, descriptor);
    }

    private JField lookupThisClassField(String name, String descriptor) {
        JField[] fields = this.fields;
        if (fields == null || fields.length == 0) {
            return null;
        }
        for (JField field : fields) {
            if (name.equals(field.getName()) && descriptor.equals(field.getDescriptor())) {
                return field;
            }
        }
        return null;
    }

    private JField lookupInterfacesField(String name, String descriptor) {
        JClass[] interfaces = this.interfaces;
        if (interfaces == null || interfaces.length == 0) {
            return null;
        }
        JField jField = null;
        for (JClass jInterface : interfaces) {
            jField = jInterface.lookupField(name, descriptor);
            if (jField != null) {
                return jField;
            }
        }
        return null;
    }

    private JField lookupSuperClassField(String name, String descriptor) {
        JClass superClass = this.superClass;
        if (superClass == null) {
            return null;
        }
        return superClass.lookupField(name, descriptor);
    }

    public boolean isSubClassOf(JClass parent) {
        for (JClass c = superClass; c != null; c = c.getSuperClass()) {
            if (parent == c) {
                return true;
            }
        }
        return false;
    }

    public JObject newObject() {
        return new JObject(this);
    }

    public boolean isAssignableFrom(JClass other) {
        JClass curClass = this;
        // 两个 class 是同一个
        if (other == curClass) {
            return true;
        }
        return isInterface() ? other.isImplements(curClass) : other.isSubClassOf(curClass);
    }

    public boolean isImplements(JClass iface) {
        for (JClass c = this; c != null; c = c.superClass) {
            JClass[] interfaces = c.getInterfaces();
            if (interfaces == null) {
                continue;
            }
            for (JClass i : interfaces) {
                if (i == iface || i.isSubInterfaceOf(iface)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isSubInterfaceOf(JClass iface) {
        JClass[] interfaces = this.interfaces;
        if (interfaces == null) {
            return false;
        }
        for(JClass superInterface : interfaces) {
            if(superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }
    public JMethod getMainMethod() {
        return getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    public JMethod getStaticMethod(String name, String descriptor) {
        JMethod[] methods = this.methods;
        if(methods == null) {
            return null;
        }
        for(JMethod method : methods) {
            if(method.isStatic()) {
                if(name.equals(method.getName()) && descriptor.equals(method.getDescriptor())) {
                    return method;
                }
            }
        }
        return null;
    }
}
