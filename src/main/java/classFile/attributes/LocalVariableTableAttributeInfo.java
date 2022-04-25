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
        int len = byteReader.readUnit16();
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
        private int startPc;
        // 作用范围所覆盖的长度
        private int length;
        // 局部变量名称在常量池中的索引
        private int nameIndex;
        // 变量描述符在常量池中的索引
        private int descriptorIndex;
        // 局部变量在栈帧中的 slot 位置
        private int index;

        LocalVariableTableEntry(ByteReader byteReader) {
            this.startPc = byteReader.readUnit16();
            this.length = byteReader.readUnit16();
            this.nameIndex = byteReader.readUnit16();
            this.descriptorIndex = byteReader.readUnit16();
            this.index = byteReader.readUnit16();
        }


        public int getStartPc() {
            return startPc;
        }

        public int getLength() {
            return length;
        }

        public int getNameIndex() {
            return nameIndex;
        }

        public int getDescriptorIndex() {
            return descriptorIndex;
        }

        public int getIndex() {
            return index;
        }
    }
}
