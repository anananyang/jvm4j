package classFile;

import classFile.attributes.AttributeInfo;

public class ClassFile {

//    private int magic;                       // 魔数
    // 主版本号
    private short minorVersion;
    // 次版本号
    private short majroVersion;
    private ConstantPool constantPool;

    private short accessFlag;                // 访问标记，16位的 bitmask
    private short thisClass;
    private short superClass = -1;           // superClass 父类名称在常量池的索引，默认没有父类
    private short[] interfaces;
    private MemberInfo[] fileds;
    private MemberInfo[] methods;
    private AttributeInfo[] attributes;


//    public void setMagic(int magic) {
//        this.magic = magic;
//    }

    public void setMinorVersion(short minorVersion) {
        this.minorVersion = minorVersion;
    }

    public void setMajroVersion(short majroVersion) {
        this.majroVersion = majroVersion;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public void setAccessFlag(short accessFlag) {
        this.accessFlag = accessFlag;
    }

    public void setThisClass(short thisClass) {
        this.thisClass = thisClass;
    }

    public void setSuperClass(short superClass) {
        this.superClass = superClass;
    }

    public void setInterfaces(short[] interfaces) {
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

    /**
     * 从常量池中获取类名
     *
     * @return
     */
    public String getClassName() {
        return null;
    }

    /**
     * 从常量池获取父类的名称
     *
     * @return
     */
    public String getSuperName() {
        return null;
    }

    /**
     * 从常量池获取所有实现的接口名称
     *
     * @return
     */
    public String[] getInterfaceNames() {
        return null;
    }

}
