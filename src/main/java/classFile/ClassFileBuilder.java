package classFile.reader;

import classFile.ClassFile;
import classFile.ConstantPool;
import classFile.MemberInfo;
import classFile.attributes.AttributeInfo;

public class ClassFileBuilder {

    private ClassFile classFile;

    ClassFileBuilder() {
        this.classFile = new ClassFile();
    }

    public ClassFileBuilder minorVersion(short minorVersion) {
        classFile.setMinorVersion(minorVersion);
        return this;
    }


    public ClassFileBuilder majroVersion(short majroVersion) {
        classFile.setMajroVersion(majroVersion);
        return this;
    }

    public ClassFileBuilder constantPool(ConstantPool constantPool) {
        classFile.setConstantPool(constantPool);
        return this;
    }

    public ClassFileBuilder accessFlag(short accessFlag) {
        classFile.setAccessFlag(accessFlag);
        return this;
    }

    public ClassFileBuilder thisClass(short thisClass) {
        classFile.setThisClass(thisClass);
        return this;
    }

    public ClassFileBuilder superClass(short superClass) {
        classFile.setSuperClass(superClass);
        return this;
    }

    public ClassFileBuilder interfaces(short[] interfaces) {
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
