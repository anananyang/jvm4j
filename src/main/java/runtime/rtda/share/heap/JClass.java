package runtime.rtda.share.heap;

import classFile.ClassFile;
import classFile.MemberInfo;
import eum.AccessFlag;
import runtime.rtda.Slot;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.JThread;
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
    // 类的初始化是否已经开始
    private boolean initStarted = false;

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
        // 先搜索当前类以及父类
        JField field = lookupThisClassField(name, descriptor);
        if (field == null) {
            field = lookupInterfacesField(name, descriptor);
        }
        return field;
    }

    private JField lookupThisClassField(String name, String descriptor) {
        for (JClass c = this; c != null; c = c.superClass) {
            JField[] fields = this.fields;
            if (fields == null) {
                return null;
            }
            for (JField field : fields) {
                if (name.equals(field.getName()) && descriptor.equals(field.getDescriptor())) {
                    return field;
                }
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


    public JMethod lookupMethod(String name, String descriptor) {
        // 先搜索当前类以及父类的方法
        JMethod jMethod = lookupThisClassMethod(name, descriptor);
        // 再搜索接口中的方法
        if (jMethod == null) {
            jMethod = lookupInterfaceMethod(name, descriptor);
        }
        return jMethod;
    }

    private JMethod lookupThisClassMethod(String name, String descriptor) {
        for (JClass c = this; c != null; c = c.superClass) {
            JMethod[] methods = c.getMethods();
            if (methods == null) {
                continue;
            }
            for (JMethod method : methods) {
                if (name.equals(method.getName()) && descriptor.equals(method.getDescriptor())) {
                    return method;
                }
            }
        }
        return null;
    }

    private JMethod lookupInterfaceMethod(String name, String descriptor) {
        JClass[] interfaces = this.interfaces;
        if (interfaces == null) {
            return null;
        }
        JMethod jMethod = null;
        for (JClass jInterface : interfaces) {
            jMethod = jInterface.lookupMethod(name, descriptor);
            if (jMethod != null) {
                return jMethod;
            }
        }
        return null;
    }


    public boolean isSubClassOf(JClass parent) {
        for (JClass c = superClass; c != null; c = c.getSuperClass()) {
            if (parent == c) {
                return true;
            }
        }
        return false;
    }

    public boolean isSuperClassOf(JClass child) {
        return child.isSubClassOf(this);
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
        for (JClass superInterface : interfaces) {
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }

    public JMethod getMainMethod() {
        return getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    public JMethod getStaticMethod(String name, String descriptor) {
        return getMethod(name, descriptor, true);
    }

    private JMethod getMethod(String name, String descriptor, boolean isStatic) {
        JMethod[] methods = this.methods;
        if (methods == null) {
            return null;
        }
        for (JMethod method : methods) {
            if (method.isStatic() == isStatic) {
                if (name.equals(method.getName()) && descriptor.equals(method.getDescriptor())) {
                    return method;
                }
            }
        }
        return null;
    }

    public boolean isInitStarted() {
        return initStarted;
    }

    public void setInitStarted(boolean initStarted) {
        this.initStarted = initStarted;
    }

    /**
     * 调用类到初始化方法对类进行初始化
     */
    public void initClass(JThread jThread) {
        // 初始化开始标记
        this.initStarted = true;
        // 再初始化当前类（先入栈的后初始化）
        JMethod cinitMethod = getMethod("<clinit>", "()V", true);
        if(cinitMethod != null) {
            Frame frame = jThread.newFrame(cinitMethod);
            jThread.pushFrame(frame);
        }
        // 先初始化父类(后入栈的先初始化)
        if(!this.isInterface()) {
            if(superClass != null && !superClass.isInitStarted()) {
                superClass.initClass(jThread);
            }
        }
    }
}
