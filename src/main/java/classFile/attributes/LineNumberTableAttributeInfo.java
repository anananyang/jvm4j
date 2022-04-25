package classFile.attributes;

import classFile.ConstantPool;
import classFile.reader.ByteReader;

/**
 * 调试信息，可以使用 javac -g:none 关闭
 */
public class LineNumberTableAttributeInfo extends AttributeInfo {

    private LineNumberTableEntry[] LineNumberTable;

    public LineNumberTableAttributeInfo(String attrName,
                                        int attrLen,
                                        ByteReader byteReader,
                                        ConstantPool constantPool) {
        super(attrName, attrLen);
        this.LineNumberTable = readLineNumberTable(byteReader);
    }

    private LineNumberTableEntry[] readLineNumberTable(ByteReader byteReader) {
        int len = byteReader.readUnit16();
        LineNumberTableEntry[] lineNumberTable = new LineNumberTableEntry[len];
        for (short i = 0; i < len; i++) {
            lineNumberTable[i] = new LineNumberTableEntry(byteReader);
        }
        return lineNumberTable;
    }

    public LineNumberTableEntry[] getLineNumberTable() {
        return LineNumberTable;
    }

    class LineNumberTableEntry {
        private int startPc;
        private int lineNum;

        LineNumberTableEntry(ByteReader byteReader) {
            this.startPc = byteReader.readUnit16();
            this.lineNum = byteReader.readUnit16();
        }

        public int getStartPc() {
            return startPc;
        }

        public int getLineNum() {
            return lineNum;
        }
    }

}
