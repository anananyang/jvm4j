package classFile;

import classFile.attributes.AttributeInfo;

import java.util.Arrays;

public class ClassFile {

    //    priv int magic;                       // 魔数
    // 主版本号
    private int minorVersion;
    // 次版本号
    private int majroVersion;
    private ConstantPool constantPool;

    private int accessFlag;                // 访问标记，16位的 bitmask
    private int thisClass;
    private int superClass = -1;           // superClass 父类名称在常量池的索引，默认没有父类
    private int[] interfaces;
    private MemberInfo[] fileds;
    private MemberInfo[] methods;
    private AttributeInfo[] attributes;


//    public void setMagic(int magic) {
//        this.magic = magic;
//    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public void setMajroVersion(int majroVersion) {
        this.majroVersion = majroVersion;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public void setAccessFlag(int accessFlag) {
        this.accessFlag = accessFlag;
    }

    public void setThisClass(int thisClass) {
        this.thisClass = thisClass;
    }

    public void setSuperClass(int superClass) {
        this.superClass = superClass;
    }

    public void setInterfaces(int[] interfaces) {
        this.interfaces = interfaces;
    }

    public void setFileds(MemberInfo[] fileds) {
        this.fileds = fileds;
    }

    public void setMethods(MemberInfo[] methods) {
        this.methods = methods;
    }

    public void setAttributes(AttributeInfo[] attributes) {
        this.attributes = attributes;
    }


    public int getMinorVersion() {
        return minorVersion;
    }

    public int getMajroVersion() {
        return majroVersion;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public int getAccessFlag() {
        return accessFlag;
    }

    /**
     * 从常量池中获取类名
     *
     * @return
     */
    public String getClassName() {
        return constantPool.getClassName(thisClass);
    }

    /**
     * 从常量池获取父类的名称
     *
     * @return
     */
    public String getSuperName() {
        // 没有父类
        if(superClass == 0) {
            return null;
        }
        return constantPool.getClassName(superClass);
    }

    /**
     * 从常量池获取所有实现的接口名称
     *
     * @return
     */
    public String[] getInterfaceNames() {
        int len = interfaces.length;
        if (len == 0) {
            return null;
        }
        String[] interfaceNames = new String[len];
        for (int i = 0; i < len; i++) {
            int interfaceNameIndex = interfaces[i];
            interfaceNames[i] = constantPool.getClassName(interfaceNameIndex);
        }
        return interfaceNames;
    }

    public MemberInfo[] getFileds() {
        return fileds;
    }

    public MemberInfo[] getMethods() {
        return methods;
    }
}
