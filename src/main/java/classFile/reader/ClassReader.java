package classFile;

public class ClassReader {
    private byte[] bytes;

    ClassReader(byte[] bytes) {
        this.bytes = bytes;
    }

    private void read() {
        readAndCheckMagic();
        readAndCheckVersion();
        readAccessFlag();
        readThisClass();
        readSuperClass();
        readInterfaces();
        readFields();
        readMethods();
        readAttributes();
    }

    private void readAndCheckMagic(ClassReader reader) {

    }

    private void readAndCheckVersion(ClassReader reader) {

    }

    private void readAccessFlag(ClassReader reader) {

    }

    private void readThisClass(ClassReader reader) {

    }

    private void readSuperClass(ClassReader reader) {

    }

    private void readInterfaces(ClassReader reader) {

    }

    private void readFields(ClassReader reader) {

    }

    private void readMethods(ClassReader reader) {

    }

    private void readAttributes(ClassReader reader) {

    }

}
