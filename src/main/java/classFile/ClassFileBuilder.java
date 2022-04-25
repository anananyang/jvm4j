package classFile;

import classFile.attributes.AttributeInfo;

public class ClassFileBuilder {

    private ClassFile classFile;

    public ClassFileBuilder() {
        this.classFile = new ClassFile();
    }

    public ClassFileBuilder minorVersion(int minorVersion) {
        classFile.setMinorVersion(minorVersion);
        return this;
    }


    public ClassFileBuilder majroVersion(int majroVersion) {
        classFile.setMajroVersion(majroVersion);
        return this;
    }

    public ClassFileBuilder constantPool(ConstantPool constantPool) {
        classFile.setConstantPool(constantPool);
        return this;
    }

    public ClassFileBuilder accessFlag(int accessFlag) {
        classFile.setAccessFlag(accessFlag);
        return this;
    }

    public ClassFileBuilder thisClass(int thisClass) {
        classFile.setThisClass(thisClass);
        return this;
    }

    public ClassFileBuilder superClass(int superClass) {
        classFile.setSuperClass(superClass);
        return this;
    }

    public ClassFileBuilder interfaces(int[] interfaces) {
        classFile.setInterfaces(interfaces);
        return this;
    }

    public ClassFileBuilder fileds(MemberInfo[] filed) {
        classFile.setFileds(filed);
        return this;
    }

    public ClassFileBuilder methods(MemberInfo[] methods) {
        classFile.setMethods(methods);
        return this;
    }

    public ClassFileBuilder attributes(AttributeInfo[] attributes) {
        classFile.setAttributes(attributes);
        return this;
    }

    public ClassFile build() {
        return classFile;
    }

}
