package runtime.rtda.share.heap;

import classFile.ClassFile;
import classFile.ClassFileParser;
import classpath.ClassPath;
import eum.AccessFlag;
import eum.PrimitiveType;
import runtime.rtda.share.heap.rtcp.RuntimeConstantPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JClassLoader {
    private ClassPath classPath;
    // 可以把 classMap 当前方法区
    Map<String, JClass> classMap = new HashMap<>();

    private static final String[] arrayInterfaces = new String[]{"java/lang/Cloneable", "java/io/Serializable"};

    public JClassLoader(ClassPath classPath) {
        this.classPath = classPath;
        // 加载 java/lang/Class 类
        loadBasicClasses();
        // 加载基本类型
        loadPrimiviteClasses();
    }

    private void loadBasicClasses() {
        JClass jlclass = this.loadClass("java/lang/Class");
        if (classMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, JClass> entry : classMap.entrySet()) {
            JClass jClass = entry.getValue();
            // 加载完成的类未绑定 class 对象
            if (jClass.getClassObj() != null) {
                continue;
            }
            JObject classObj = jlclass.newObject();
            classObj.setExtra(jClass);
            jClass.setClassObj(classObj);
        }
    }

    private void loadPrimiviteClasses() {
        for (PrimitiveType primitiveType : PrimitiveType.values()) {
            loadPrimiviteClass(primitiveType);
        }
    }

    private void loadPrimiviteClass(PrimitiveType primitiveType) {
        JClass jClass = new JClass(AccessFlag.ACC_PUBLIC.getValue(),
                primitiveType.getType(),
                null,
                null,
                this,
                true);
        JClass jlclass = classMap.get("java/lang/Class");
        JObject classObj = jlclass.newObject();
        classObj.setExtra(jClass);
        jClass.setClassObj(classObj);

        // 放入方法区
        classMap.put(primitiveType.getType(), jClass);
    }

    public JClass loadClass(String className) {
        JClass jClass = classMap.get(className);
        if (jClass == null) {
            jClass = className.startsWith("[")
                    ? loadArrayClass(className)   // 加载数组类
                    : loadNonArrayClass(className);
        }
        // 设置 class 对象（Class 类已经加载完成的情况下）
        JClass jlclass = classMap.get("java/lang/Class");
        if (jlclass != null) {
            JObject classObj = jlclass.newObject();
            classObj.setExtra(jClass);
            jClass.setClassObj(classObj);
        }

        return jClass;
    }

    private JClass loadNonArrayClass(String className) {
        // 将 classs 文件读取到内存
        byte[] bytes = readClass(className);
        // 解析 class 文件，生成虚拟机可以使用的类数据，并放入方法区
        JClass jClass = defineClass(bytes);
        // 链接
        link(jClass);

        return jClass;
    }

    private byte[] readClass(String className) {
        try {
            byte[] bytes = classPath.readClass(className);
            if (bytes == null) {
                throw new RuntimeException("java.lang.ClassNotFoundException: " + className);
            }
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JClass defineClass(byte[] bytes) {
        JClass jClass = parseClass(bytes);
        jClass.setLoader(this);
        jClass.setSuperClass(resolveSupperClass(jClass));
        jClass.setInterfaces(resolveInterfaces(jClass));
        // 将 class 加入方法区
        classMap.put(jClass.getThisClassName(), jClass);

        return jClass;
    }

    private JClass parseClass(byte[] bytes) {
        ClassFile classFile = ClassFileParser.parse(bytes);
        JClass jClass = new JClass(classFile);
        return jClass;
    }

    private JClass resolveSupperClass(JClass jClass) {
        String superClassName = jClass.getSuperClassName();
        if (superClassName == null) {
            return null;
        }
        return loadClass(superClassName);
    }


    private JClass[] resolveInterfaces(JClass jClass) {
        String[] interfaceNames = jClass.getInterfaceNames();
        if (interfaceNames == null || interfaceNames.length == 0) {
            return null;
        }
        JClass[] jClasses = new JClass[interfaceNames.length];
        for (int i = 0; i < interfaceNames.length; i++) {
            jClasses[i] = loadClass(interfaceNames[i]);
        }
        return jClasses;
    }

    /**
     * 类的链接分为两个必要的阶段：验证、准备
     *
     * @param jClass
     */
    private void link(JClass jClass) {
        verify(jClass);
        prepare(jClass);
    }

    /**
     * 验证阶段未实现
     *
     * @param jClass
     */
    private void verify(JClass jClass) {
        // do verify
    }

    /**
     * 我们使用 Slots 对象来存放变量信息
     *
     * @param jClass
     */
    private void prepare(JClass jClass) {
        // 计算普通变量的个数，以及相应的变量的编号
        calcInstanceFieldSlotIds(jClass);
        // 计算普通变量的个数，以及相应的变量的编号
        calcStaticFieldSlotIds(jClass);
        // 初始化静态变量
        allocAndInitStaticVars(jClass);
    }

    private void calcInstanceFieldSlotIds(JClass jClass) {
        JClass superClass = jClass.getSuperClass();
        // id 从 0 开始编号
        int slotId = 0;
        if (superClass != null) {
            slotId = superClass.getInstanceSlotCount();
        }
        JField[] fields = jClass.getFields();
        // 记录字段的数量（包含父类）
        if (fields != null) {
            for (JField jField : fields) {
                if (!jField.isStatic()) {
                    jField.setSlotId(slotId);  // long、double 都只占一个 slot
                    slotId++;
                }
            }
        }

        jClass.setInstanceSlotCount(slotId);
    }

    private void calcStaticFieldSlotIds(JClass jClass) {
        JClass superClass = jClass.getSuperClass();
        // id 从 0 开始编号
        int slotId = 0;
        JField[] fields = jClass.getFields();
        // 记录字段的数量（包含父类）
        if (fields != null) {
            for (JField jField : fields) {
                if (jField.isStatic()) {
                    jField.setSlotId(slotId);  // long、double 都只占一个 slot
                    slotId++;
                }
            }
        }

        jClass.setStaticSlotCount(slotId);
    }

    private void allocAndInitStaticVars(JClass jClass) {
        jClass.setStaticVars(new Slots(jClass.getStaticSlotCount()));
        JField[] fields = jClass.getFields();
        // 记录字段的数量（包含父类）
        if (fields != null) {
            for (JField jField : fields) {
                if (jField.isStatic() && jField.isFinal()) {
                    initStaticFinalVar(jClass, jField);
                }
            }
        }
    }

    private void initStaticFinalVar(JClass jClass, JField jField) {
        Slots staticVars = jClass.getStaticVars();
        RuntimeConstantPool constantPool = jClass.getConstantPool();
        int valueIndex = jField.getConstValueIndex();
        // 运行时常量池从 1 开始计数
        if (valueIndex <= 0) {
            return;
        }
        Object val = constantPool.getValueByIndex(valueIndex);
        int slotId = jField.getSlotId();
        switch (jField.getDescriptor()) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I":
                staticVars.setInt(slotId, (int) val);
                break;
            case "J":
                staticVars.setLong(slotId, (long) val);
                break;
            case "F":
                staticVars.setFloat(slotId, (float) val);
                break;
            case "D":
                staticVars.setDouble(slotId, (double) val);
                break;
            case "Ljava/lang/String;":
                // 将字符串转换成当前虚拟机的字符串对象
                JObject jstring = StringPool.getJString(jClass.getLoader(), (String) val);
                staticVars.setRef(slotId, jstring);
                break;
        }
    }

    private JClass loadArrayClass(String className) {
        JClass jClass = new JClass(AccessFlag.ACC_PUBLIC.getValue(),
                className,
                "java/lang/Object",
                arrayInterfaces,
                this,
                true);
        // 加入方法区
        classMap.put(className, jClass);
        return jClass;
    }

}
