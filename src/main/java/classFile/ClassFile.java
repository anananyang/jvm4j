package classFile;

import classFile.attributes.AttributeInfo;

import java.util.Arrays;

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


    public short getMinorVersion() {
        return minorVersion;
    }

    public short getMajroVersion() {
        return majroVersion;
    }

    public short getAccessFlag() {
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
            short interfaceNameIndex = interfaces[i];
            interfaceNames[i] = constantPool.getClassName(interfaceNameIndex);
        }
        return interfaceNames;
    }

    public int getConstantCount() {
        return constantPool.getconstantoCount();
    }


    public void printClassInfo() {
        System.out.println(String.format("version: %d.%d", majroVersion, minorVersion));
        System.out.println(String.format("constants count: %d", constantPool.getconstantoCount()));
        System.out.println(String.format("access flags: %s", Integer.toHexString(accessFlag)));
        System.out.println(String.format("this class: %s", getClassName()));
        System.out.println(String.format("super class: %s", getSuperName()));
        System.out.println(String.format("interfaces: %s", Arrays.toString(getInterfaceNames())));
        System.out.println(String.format("field count: %d", fileds == null ? 0 : fileds.length));
        if(fileds != null) {
            for(int i = 0; i < fileds.length; i++) {
                System.out.println(String.format("   %s", fileds[i].getName()));
            }
        }
        System.out.println(String.format("method count: %d", methods == null ? 0 : methods.length));
        if(methods != null) {
            for(int i = 0; i < methods.length; i++) {
                System.out.println(String.format("   %s", methods[i].getName()));
            }
        }
    }
}
