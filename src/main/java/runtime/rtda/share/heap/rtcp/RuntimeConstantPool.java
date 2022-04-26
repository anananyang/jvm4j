package runtime.rtda.share.heap.rtcp;

import classFile.ConstantPool;
import classFile.constants.*;
import eum.ConstantType;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.rtcp.ref.ClassRef;
import runtime.rtda.share.heap.rtcp.ref.FieldRef;
import runtime.rtda.share.heap.rtcp.ref.InterfaceMethodRef;
import runtime.rtda.share.heap.rtcp.ref.MethodRef;

import static eum.ConstantType.*;

/**
 * 运行时常量池, 存放字面量和符号引用
 * 1、字面量包括整数、浮点数和字符串字面量
 * 2、符号引用包括类符号引用、字段符号引用、方法符号引用和接口符号引号
 */
public class RuntimeConstantPool {
    private JClass jClass;
    private JConstant[] constants;

    public RuntimeConstantPool(JClass jClass, ConstantPool constantPool) {
        this.jClass = jClass;
        this.constants = this.initConstants(constantPool);
    }

    public JClass getjClass() {
        return jClass;
    }

    private JConstant[] initConstants(ConstantPool constantPool) {
        int count = constantPool.getconstantoCount();
        if (count == 0) {
            return null;
        }
        // 常量池从 1 开始
        JConstant[] constants = new JConstant[count];
        for (int i = 1; i < count; i++) {
            ConstantInfo constantInfo = constantPool.getByIndex(i);
            switch (constantInfo.getType()) {
                case CONSTANT_Integer:
                    ConstantIntegerInfo integerInfo = (ConstantIntegerInfo) constantInfo;
                    constants[i] = new JConstant<Integer>(integerInfo.getValue(), CONSTANT_Integer);
                    break;

                case CONSTANT_Float:
                    ConstantFloatInfo floatInfo = (ConstantFloatInfo) constantInfo;
                    constants[i] = new JConstant<Float>(floatInfo.getValue(), CONSTANT_Float);
                    break;

                case CONSTANT_Long:
                    ConstantLongInfo longInfo = (ConstantLongInfo) constantInfo;
                    constants[i] = new JConstant<Long>(longInfo.getValue(), CONSTANT_Long);
                    i++;   // long 占两个
                    break;

                case CONSTANT_Double:
                    ConstantDoubleInfo doubleInfo = (ConstantDoubleInfo) constantInfo;
                    constants[i] = new JConstant<Double>(doubleInfo.getValue(), CONSTANT_Double);
                    i++; // double 占两个
                    break;

                case CONSTANT_String:
                    ConstantStringInfo strInfo = (ConstantStringInfo) constantInfo;
                    constants[i] = new JConstant<String>(strInfo.getStr(), CONSTANT_String);
                    break;

                case CONSTANT_Class:
                    ConstantClassInfo classInfo = (ConstantClassInfo) constantInfo;
                    constants[i] = new JConstant<ClassRef>(new ClassRef(this, classInfo), CONSTANT_Class);
                    break;

                case CONSTANT_Fieldref:
                    ConstantFieldrefInfo fieldrefInfo = (ConstantFieldrefInfo) constantInfo;
                    constants[i] = new JConstant<FieldRef>(new FieldRef(this, fieldrefInfo), CONSTANT_Class);
                    break;

                case CONSTANT_Methodref:
                    ConstantMethodrefInfo methodrefInfo = (ConstantMethodrefInfo) constantInfo;
                    constants[i] = new JConstant<MethodRef>(new MethodRef(this, methodrefInfo), CONSTANT_Class);
                    break;

                case CONSTANT_InterfaceMethodref:
                    ConstantInterfaceMethodrefInfo imethodrefInfo = (ConstantInterfaceMethodrefInfo) constantInfo;
                    constants[i] = new JConstant<InterfaceMethodRef>(new InterfaceMethodRef(this, imethodrefInfo), CONSTANT_Class);
                    break;
                default:
                    //还有一些jdk1.7才开始支持的动态属性,不在本虚拟机的实现范围内
                    break;
            }
        }
        return constants;
    }

    public Object getValueByIndex(int index) {
        if (index < 0 || index >= constants.length) {
            throw new RuntimeException("No constants at index [" + index + "]");
        }
        return constants[index].getValue();
    }

    public ConstantType getTypeByIndex(int index) {
        if (index < 0 || index >= constants.length) {
            throw new RuntimeException("No constants at index [" + index + "]");
        }
        return constants[index].getType();
    }

}
