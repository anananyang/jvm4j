package classFile.reader;

import classFile.ConstantPool;
import classFile.constants.*;
import eum.ConstantType;

import java.lang.reflect.Constructor;
import java.util.EnumMap;

import static eum.ConstantType.*;

public class ConstantPoolReader {

    private static EnumMap<ConstantType, Class<? extends ConstantInfo>> classMap = new EnumMap<>(ConstantType.class);

    static {
        classMap.put(CONSTANT_Utf8, ConstantUtf8Info.class);
        classMap.put(CONSTANT_Integer, ConstantIntegerInfo.class);
        classMap.put(CONSTANT_Float, ConstantFloatInfo.class);
        classMap.put(CONSTANT_Long, ConstantLongInfo.class);
        classMap.put(CONSTANT_Double, ConstantDoubleInfo.class);
        classMap.put(CONSTANT_Class, ConstantClassInfo.class);
        classMap.put(CONSTANT_String, ConstantStringInfo.class);
        classMap.put(CONSTANT_Fieldref, ConstantFieldrefInfo.class);
        classMap.put(CONSTANT_Methodref, ConstantMethodrefInfo.class);
        classMap.put(CONSTANT_InterfaceMethodref, ConstantInterfaceMethodrefInfo.class);
        classMap.put(CONSTANT_NameAndType, ConstantNameAndTypeInfo.class);
        classMap.put(CONSTANT_MethodHandle, ConstantMethodHandleInfo.class);
        classMap.put(CONSTANT_MethodType, ConstantMethodTypeInfo.class);
        classMap.put(CONSTANT_InvokeDynamic, ConstantInvokeDynamicInfo.class);
    }

    public static ConstantPool read(ByteReader byteReader) {
        ConstantInfo[] constants = readConstants(byteReader);
        return new ConstantPool(constants);
    }

    private static ConstantInfo[] readConstants(ByteReader byteReader) {
        // 获取常量池的长度，这个长度是在编译时计算好的。
        int count = readConstantPoolCount(byteReader);
        ConstantInfo[] constants = new ConstantInfo[count];
        // 有效的常量池索引是 1 - （n - 1）。0 是无效索引
        for (int i = 1; i < count; i++) {
            constants[i] = readConstant(byteReader);
            // CONSTANT_Long_info 和 CONSTANT_Double_info 各占两个位置
            if (isLongConstant(constants[i]) || isDoubleConstant(constants[i])) {
                i++;
            }
        }
        return constants;
    }

    private static int readConstantPoolCount(ByteReader byteReader) {
        return byteReader.readUnit16();
    }

    private static ConstantInfo readConstant(ByteReader byteReader) {
        int tag = byteReader.readUnit8();
        ConstantInfo constantInfo = newConstantByTag(tag, byteReader);
        return constantInfo;
    }

    private static boolean isLongConstant(ConstantInfo constant) {
        return constant instanceof ConstantLongInfo;
    }

    private static boolean isDoubleConstant(ConstantInfo constant) {
        return constant instanceof ConstantDoubleInfo;
    }

    private static ConstantInfo newConstantByTag(int tag, ByteReader byteReader) {
        ConstantType constantType = getByTypeValue(tag);
        Class<? extends ConstantInfo> clazz = getConstantInfoClass(constantType);
        if (clazz == null) {
            throw new RuntimeException("can not recognize the constantTag [" + tag + "]");
        }

        try {
            Constructor constructor = clazz.getDeclaredConstructor(ConstantType.class, ByteReader.class);
            ConstantInfo constantInfo = (ConstantInfo) constructor.newInstance(constantType, byteReader);
            return constantInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);   // 继续向外抛出异常
        }
    }

    public static Class<? extends ConstantInfo> getConstantInfoClass(ConstantType constantType) {
        if (constantType == null) {
            return null;
        }
        return classMap.get(constantType);
    }
}
