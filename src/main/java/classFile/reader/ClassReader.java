package classFile.reader;

import classFile.ClassFile;
import classFile.ClassFileBuilder;
import classFile.ConstantPool;
import classFile.MemberInfo;
import classFile.attributes.AttributeInfo;

public class ClassReader {

    private ByteReader byteReader;

    public ClassReader(byte[] bytes) {
        this.byteReader = new ByteReader(bytes);
    }

    public ClassFile read() {
        int magicNum = readAndCheckMagicNum();
        int minorVersion = readAndCheckMinorVersion();
        int majorVersion = readAndCheckMajorVersion();
        ConstantPool constantPool = readConstantPool();

        ClassFileBuilder builder = new ClassFileBuilder();
        return builder.minorVersion(minorVersion)
                .majroVersion(majorVersion)
                .constantPool(constantPool)
                .accessFlag(readAccessFlag())
                .thisClass(readThisClass())
                .superClass(readSuperClass())
                .interfaces(readInterfaces())
                .fileds(readFields(constantPool))
                .methods(readMethods(constantPool))
                .attributes(readAttributes(constantPool))
                .build();
    }

    /**
     * 魔数占4个字节
     *
     * @return
     */
    private int readAndCheckMagicNum() {
        int magicNum = byteReader.readUint32();
        if (magicNum != 0xCAFEBABE) {
            throw new RuntimeException("java.lang.ClassFormatError: magic!");
        }
        return magicNum;
    }

    private int readAndCheckMinorVersion() {
        int minorVersion = byteReader.readUnit16();
        // 此版本号只在 1.2 之前用过，之后全都为 0（我们不管 1.2 之前的版本）
        if (minorVersion != 0) {
            throw new RuntimeException("java.lang.UnsupportedClassVersionError!");
        }
        return minorVersion;
    }

    private int readAndCheckMajorVersion() {
        int majorVersion = byteReader.readUnit16();
        // 此版本号只在 1.2 之前用过，之后全都为 0（我们不管 1.2 之前的版本）
        switch (majorVersion) {
            case 46:  // 1.2 版本
            case 47:  // 1.3
            case 48:  // 1.4
            case 49:  // 1.5
            case 50:  // 1.6
            case 51:  // 1.7
            case 52:  // 1.8
                return majorVersion;
            default:
                throw new RuntimeException("java.lang.UnsupportedClassVersionError!");
        }
    }

    /**
     * 读取常量池信息
     *
     * @return
     */
    private ConstantPool readConstantPool() {
        return ConstantPoolReader.read(byteReader);
    }


    private int readAccessFlag() {
        return byteReader.readUnit16();
    }

    private int readThisClass() {
        return byteReader.readUnit16();
    }

    private int readSuperClass() {
        return byteReader.readUnit16();
    }

    private int[] readInterfaces() {
        return byteReader.readUint16s();
    }

    private MemberInfo[] readFields(ConstantPool constantPool) {
        return MemberReader.read(byteReader, constantPool);
    }

    private MemberInfo[] readMethods(ConstantPool constantPool) {
        return MemberReader.read(byteReader, constantPool);
    }

    private AttributeInfo[] readAttributes(ConstantPool constantPool) {
        return AttributeReader.read(byteReader, constantPool);
    }

}
