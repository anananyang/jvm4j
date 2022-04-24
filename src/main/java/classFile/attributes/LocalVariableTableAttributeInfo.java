package classFile.attributes;

import classFile.ConstantPool;
import classFile.reader.ByteReader;

/**
 * 调试信息，可以使用 javac -g:none 关闭
 */
public class LocalVariableTableAttributeInfo extends AttributeInfo {

    private LocalVariableTableEntry[] localVariableTable;

    public LocalVariableTableAttributeInfo(String attrName,
                                           int attrLen,
                                           ByteReader byteReader,
                                           ConstantPool constantPool) {
        super(attrName, attrLen);
        this.localVariableTable = readlocalVariableTable(byteReader);
    }

    private LocalVariableTableEntry[] readlocalVariableTable(ByteReader byteReader) {
        short len = byteReader.readUnit16();
        LocalVariableTableEntry[] localVariableTable = new LocalVariableTableEntry[len];
        for (short i = 0; i < len; i++) {
            localVariableTable[i] = new LocalVariableTableEntry(byteReader);
        }
        return localVariableTable;
    }

    public LocalVariableTableEntry[] getLocalVariableTable() {
        return localVariableTable;
    }

    class LocalVariableTableEntry {
        // 局部变量的生命周期开始的字节码偏移量
        private short startPc;
        // 作用范围所覆盖的长度
        private short length;
        // 局部变量名称在常量池中的索引
        private short nameIndex;
        // 变量描述符在常量池中的索引
        private short descriptorIndex;
        // 局部变量在栈帧中的 slot 位置
        private short index;

        LocalVariableTableEntry(ByteReader byteReader) {
            this.startPc = byteReader.readUnit16();
            this.length = byteReader.readUnit16();
            this.nameIndex = byteReader.readUnit16();
            this.descriptorIndex = byteReader.readUnit16();
            this.index = byteReader.readUnit16();
        }


        public short getStartPc() {
            return startPc;
        }

        public short getLength() {
            return length;
        }

        public short getNameIndex() {
            return nameIndex;
        }

        public short getDescriptorIndex() {
            return descriptorIndex;
        }

        public short getIndex() {
            return index;
        }
    }
}
