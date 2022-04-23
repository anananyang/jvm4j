package classFile.reader;

import classFile.ConstantPool;
import classFile.constants.*;
import emu.ConstantType;

import java.lang.reflect.Constructor;
import java.util.EnumMap;

import static emu.ConstantType.*;

public class ConstantPoolReader {

    private ByteReader byteReader;

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

    ConstantPoolReader(ByteReader byteReader) {
        this.byteReader = byteReader;
    }

    public ConstantPool read() {
        ConstantInfo[] constants = readConstants();
        return new ConstantPool(constants);
    }

    private ConstantInfo[] readConstants() {
        // 获取常量池的长度，这个长度是在编译时计算好的。
        short count = readConstantPoolCount();
        ConstantInfo[] constants = new ConstantInfo[count];
        // 有效的常量池索引是 1 - （n - 1）。0 是无效索引
        for (int i = 1; i < count; i++) {
            constants[i] = readConstant();
            // CONSTANT_Long_info 和 CONSTANT_Double_info 各占两个位置
            if (isLongConstant(constants[i]) || isDoubleConstant(constants[i])) {
                i++;
            }
        }
        return constants;
    }

    short readConstantPoolCount() {
        return byteReader.readUnit16();
    }

    private ConstantInfo readConstant() {
        byte tag = byteReader.readUnit8();
        ConstantInfo constantInfo = newConstantByTag(tag);
        return constantInfo;
    }

    private boolean isLongConstant(ConstantInfo constant) {
        return constant instanceof ConstantLongInfo;
    }

    private boolean isDoubleConstant(ConstantInfo constant) {
        return constant instanceof ConstantDoubleInfo;
    }

    ConstantInfo newConstantByTag(byte tag) {
        Class<? extends ConstantInfo> clazz = getConstantInfoClass(tag);
        if (clazz == null) {
            throw new RuntimeException("can not recognize the constantTag [" + tag + "]");
        }

        try {
            Constructor constructor = clazz.getDeclaredConstructor(byte.class, ByteReader.class);
            ConstantInfo constantInfo = (ConstantInfo) constructor.newInstance(tag, byteReader);
            return constantInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);   // 继续向外抛出异常
        }
    }

    public static Class<? extends ConstantInfo> getConstantInfoClass(byte tag) {
        ConstantType constantType = getByTypeValue(tag);
        if (constantType == null) {
            return null;
        }
        return classMap.get(constantType);
    }
}
