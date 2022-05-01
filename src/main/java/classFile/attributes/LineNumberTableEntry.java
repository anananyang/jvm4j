package classFile.attributes;

import classFile.reader.ByteReader;

public class LineNumberTableEntry {

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
