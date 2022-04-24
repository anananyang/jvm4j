package classFile.reader;

import classFile.ConstantPool;
import classFile.attributes.*;
import emu.AttributeType;

import java.lang.reflect.Constructor;
import java.util.EnumMap;

import static emu.AttributeType.*;
import static emu.AttributeType.Deprecated;

public class AttributeReader {

    private static EnumMap<AttributeType, Class<? extends AttributeInfo>> classMap = new EnumMap<>(AttributeType.class);

    static {
        classMap.put(Code, CodeAttributeInfo.class);
        classMap.put(ConstantValue, ConstantValueAttributeInfo.class);
        classMap.put(Deprecated, DeprecatedAttributeInfo.class);
        classMap.put(Exceptions, ExceptionsAttributeInfo.class);
        classMap.put(LineNumberTable, LineNumberTableAttributeInfo.class);
        classMap.put(LocalVariableTable, LocalVariableTableAttributeInfo.class);
        classMap.put(SourceFile, SourceFileAttributeInfo.class);
        classMap.put(Synthetic, SyntheticAttributeInfo.class);
        classMap.put(Unparse, UnparsedAttributeInfo.class);
    }


    public static AttributeInfo[] read(ByteReader byteReader, ConstantPool constantPool) {
        return readAttributes(byteReader, constantPool);
    }

    private static AttributeInfo[] readAttributes(ByteReader byteReader, ConstantPool constantPool) {
        short size = byteReader.readUnit16();
        if (size == 0) {
            return null;
        }
        AttributeInfo[] attributeInfos = new AttributeInfo[size];
        for (int i = 0; i < size; i++) {
            attributeInfos[i] = readAttribute(byteReader, constantPool);
        }
        return null;
    }

    private static AttributeInfo readAttribute(ByteReader byteReader, ConstantPool constantPool) {
        // 属性名称在常量池中的索引
        short attrNameIdx = byteReader.readUnit16();
        String attrName = constantPool.getUtf8(attrNameIdx);
        int attrLen = byteReader.readUint32();
        AttributeInfo attributeInfo = newAttributeInfo(attrName, attrLen, byteReader, constantPool);
        return attributeInfo;
    }

    private static AttributeInfo newAttributeInfo(String attrName,
                                                  int attrLen,
                                                  ByteReader byteReader,
                                                  ConstantPool constantPool) {
        AttributeType attributeType = Unparse;   // 默认
        if (AttributeType.contain(attrName)) {
            attributeType = AttributeType.valueOf(attrName);
        }
        Class<? extends AttributeInfo> clazz = classMap.get(attributeType);
        try {
            Constructor constructor = clazz.getDeclaredConstructor(String.class,
                    int.class,
                    ByteReader.class,
                    ConstantPool.class);
            AttributeInfo attributeInfo = (AttributeInfo) constructor.newInstance(attrName,
                    attrLen,
                    byteReader,
                    constantPool);
            return attributeInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);   // 继续向外抛出异常
        }
    }

}
