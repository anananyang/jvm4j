package runtime.rtda.share.heap;

import classFile.ClassFile;
import classFile.MemberInfo;
import eum.AccessFlag;
import eum.ArrayType;
import eum.PrimitiveType;
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
    // class 对象， java 中的每个对象都与其 class 关联，而 JClass 关联着 class 对象，这样对象就可以通过 getClass 方法或者到 class 对象，进行相关的反射处理
    private JObject classObj;

    public JClass(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlag();
        this.thisClassName = classFile.getClassName();
        this.superClassName = classFile.getSuperName();
        this.interfaceNames = classFile.getInterfaceNames();
        this.constantPool = new RuntimeConstantPool(this, classFile.getConstantPool());
        this.fields = getFields(classFile.getFileds());
        this.methods = getMethods(classFile.getMethods());
    }

    public JClass(int accessFlags,
                  String name,
                  String superClassName,
                  String[] interfaceNames,
                  JClassLoader loader,
                  boolean initStarted
    ) {
        this.accessFlags = accessFlags;
        this.thisClassName = name;
        this.loader = loader;
        this.superClassName = superClassName;
        this.interfaceNames = interfaceNames;
        if (superClassName != null) {
            this.superClass = loader.loadClass(superClassName);
        }
        if (interfaceNames != null) {
            this.interfaces = new JClass[interfaceNames.length];
            for (int i = 0; i < interfaceNames.length; i++) {
                this.interfaces[i] = loader.loadClass(interfaceNames[i]);
            }

        }
        this.initStarted = initStarted;
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

    public JObject getClassObj() {
        return classObj;
    }

    public void setClassObj(JObject classObj) {
        this.classObj = classObj;
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
            JField[] fields = c.fields;
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

    public boolean isAssignableFrom(JClass source) {
        JClass target = this;
        // 两个 class 是同一个
        if (target == source) {
            return true;
        }
        // 如果是数组
        if (!source.isArray()) {
            if (!source.isInterface()) {
                if (!target.isInterface()) {
                    return source.isSubClassOf(source);
                } else {
                    return source.isImplements(target);
                }
            } else {
                if (!target.isInterface()) {
                    return target.isJavaLangObject();  // 任何非基础类型可以向上转型为 java/lang/Object
                } else {
                    return target.isSuperInterfaceOf(source);  // 两个都是接口
                }
            }
        } else {
            if (!target.isArray()) {
                if (!target.isInterface()) {
                    return target.isJavaLangObject();  // 数组可以向上转型为 Object
                } else {
                    // 数组实现了以下两个接口，所以可以向上转型成这两个接口类型
                    return target.isJavaLangCloneable() || target.isJavaIoSerializable();
                }
            } else {
                // 判断两个数组的组建类型是否相等（包含了多维数组的判断）
                JClass sourceClass = source.getComponentClass();
                JClass targetClass = target.getComponentClass();
                return sourceClass == targetClass || targetClass.isAssignableFrom(sourceClass);
            }
        }
    }

    public boolean isJavaLangObject() {
        return "java/lang/Object".equals(thisClassName);
    }

    public boolean isJavaLangCloneable() {
        return "java/lang/Cloneable".equals(thisClassName);
    }

    public boolean isJavaIoSerializable() {
        return "java/io/Serializable".equals(thisClassName);
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

    public boolean isSuperInterfaceOf(JClass iface) {
        return iface.isSubInterfaceOf(this);
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

    public JMethod getMethod(String name, String descriptor, boolean isStatic) {
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


    public JField getField(String name, String descriptor, boolean isStatic) {
        JField[] fields = this.fields;
        if (fields == null) {
            return null;
        }
        for (JField field : fields) {
            if (field.isStatic() == isStatic) {
                if (name.equals(field.getName()) && descriptor.equals(field.getDescriptor())) {
                    return field;
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
        if (cinitMethod != null) {
            Frame frame = jThread.newFrame(cinitMethod);
            jThread.pushFrame(frame);
        }
        // 先初始化父类(后入栈的先初始化)
        if (!this.isInterface()) {
            if (superClass != null && !superClass.isInitStarted()) {
                superClass.initClass(jThread);
            }
        }
    }

    /**
     * 构造一个新数组
     *
     * @param count
     * @return
     */
    public JObject newArray(int count) {
        if (!isArray()) {
            throw new RuntimeException("Not array classs [" + thisClassName + "]");
        }
        switch (thisClassName) {
            // 布尔类型数组
            case "[Z":
                return new JObject(this, new Boolean[count]);
            case "[B":
                return new JObject(this, new Byte[count]);
            case "[C":
                return new JObject(this, new Character[count]);
            case "[S":
                return new JObject(this, new Short[count]);
            case "[I":
                return new JObject(this, new Integer[count]);
            case "[J":
                return new JObject(this, new Long[count]);
            case "[F":
                return new JObject(this, new Float[count]);
            case "[D":
                return new JObject(this, new Double[count]);
            // 对象数组
            default:
                return new JObject(this, new JObject[count]);
        }
    }

    public JClass getArrayClass() {
        if (isArray()) {
            return this;
        }
        String name = getArrayClassName();
        return loader.loadClass(name);
    }

    private String getArrayClassName() {
        if (thisClassName.startsWith("[")) {
            return "[" + thisClassName;
        }
        // 原始类型
        String typeDesc = ArrayType.getTypeDescByType(thisClassName);
        if (typeDesc != null) {
            return typeDesc;
        }
        // 对象类型数组
        return "[L" + thisClassName + ";";
    }

    /**
     * 判断当前类是不是数组类
     *
     * @return
     */
    public boolean isArray() {
        return thisClassName.startsWith("[");
    }

    /**
     * 创建多维数组
     *
     * @param counts
     * @return
     */
    public JObject newMultiDimensionalArray(int[] counts, int dimensions) {
        // 先创建当前层的数组
        int count = counts[dimensions];
        JObject arrayRef = this.newArray(count);
        dimensions++;
        // 还有嵌套
        if (dimensions < counts.length) {
            Object[] arr = arrayRef.getArray();
            for (int i = 0; i < count; i++) {
                JClass componentClass = getComponentClass();
                arr[i] = newMultiDimensionalArray(counts, dimensions);
            }
        }
        return arrayRef;
    }

    public JClass getComponentClass() {
        String componentClassName = this.getComponentClassName();
        return loader.loadClass(componentClassName);
    }

    private String getComponentClassName() {
        if (!thisClassName.startsWith("[")) {
            throw new RuntimeException("No array: " + thisClassName);
        }
        String componentTypeDesc = thisClassName.substring(1);
        return toClassName(componentTypeDesc);
    }

    private String toClassName(String descriptor) {
        if (descriptor.startsWith("[")) {
            return descriptor;
        }
        if (descriptor.startsWith("L")) {
            return descriptor.substring(1, descriptor.length() - 1);
        }
        // 原始类型
        String className = ArrayType.getTypeByTypeDesc("[" + descriptor);
        if (className == null) {
            throw new RuntimeException("Invalid descriptor: " + descriptor);
        }
        return className;
    }

    public String getJavaName() {
        return thisClassName.replace("/", ".");
    }

    public boolean isPrimitiveType() {
        return PrimitiveType.getByType(thisClassName) != null;
    }

    /**
     * 获取静态变量
     *
     * @param name
     * @param descriptor
     * @return
     */
    public JObject getRefVar(String name, String descriptor) {
        JField jField = getField(name, descriptor, true);
        Slots slots = (Slots) staticVars;
        return slots.getRef(jField.getSlotId());
    }

}
