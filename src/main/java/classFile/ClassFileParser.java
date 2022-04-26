package classFile;

import classFile.reader.ClassReader;

public abstract class ClassFileParser {

    public static ClassFile parse(byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes);
        return classReader.read();
    }
}
