package classFile.attributes;

import classFile.ConstantPool;
import classFile.reader.AttributeReader;
import classFile.reader.ByteReader;
import eum.AttributeType;
import util.AttributeUtil;

public class CodeAttributeInfo extends AttributeInfo {

    // 操作数栈的最大深度
    private int maxStack;
    // 本地变量表的最大长度
    private int maxLocal;
    private byte[] code;
    private ExcetionTableEntry[] excetionTable;
    private AttributeInfo[] attributes;

    private ConstantPool constantPool;

    public CodeAttributeInfo(String attrName,
                             int attrLen,
                             ByteReader byteReader,
                             ConstantPool constantPool) {
        super(attrName, attrLen);
        this.constantPool = constantPool;
        this.maxStack = byteReader.readUnit16();
        this.maxLocal = byteReader.readUnit16();
        this.code = readCode(byteReader);
        this.excetionTable = readExcetionTable(byteReader);
        this.attributes = readAttributes(byteReader, constantPool);

    }

    private byte[] readCode(ByteReader byteReader) {
        int codeLen = byteReader.readUint32();
        return byteReader.readBytes(codeLen);
    }

    private ExcetionTableEntry[] readExcetionTable(ByteReader byteReader) {
        int len = byteReader.readUnit16();
        ExcetionTableEntry[] excetionTable = new ExcetionTableEntry[len];
        for (int i = 0; i < len; i++) {
            excetionTable[i] = new ExcetionTableEntry(byteReader);
        }
        return excetionTable;
    }

    private AttributeInfo[] readAttributes(ByteReader byteReader, ConstantPool constantPool) {
        return AttributeReader.read(byteReader, constantPool);
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocal() {
        return maxLocal;
    }

    public byte[] getCode() {
        return code;
    }

    public ExcetionTableEntry[] getExcetionTable() {
        return excetionTable;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }


    public class ExcetionTableEntry {
        private int startPC;
        private int endPC;
        private int handlerPC;
        private int catchType;

        ExcetionTableEntry(ByteReader byteReader) {
            this.startPC = byteReader.readUnit16();
            this.endPC = byteReader.readUnit16();
            this.handlerPC = byteReader.readUnit16();
            this.catchType = byteReader.readUnit16();
        }


        public int getStartPC() {
            return startPC;
        }

        public int getEndPC() {
            return endPC;
        }

        public int getHandlerPC() {
            return handlerPC;
        }

        public int getCatchType() {
            return catchType;
        }
    }

    public LineNumberTableAttributeInfo getLineNumberAttr() {
        return (LineNumberTableAttributeInfo) AttributeUtil.getFirstAttrByType(AttributeType.LineNumberTable, attributes);
    }
}
