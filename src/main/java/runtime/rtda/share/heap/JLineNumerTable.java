package runtime.rtda.share.heap;

import classFile.attributes.LineNumberTableAttributeInfo;
import classFile.attributes.LineNumberTableEntry;
import classFile.reader.ByteReader;

public class JLineNumerTable {

    private LineNumberTableEntry[] lineNumberTableEntries;


    public JLineNumerTable(LineNumberTableAttributeInfo attributeInfo) {
        this.lineNumberTableEntries = attributeInfo.getLineNumberTable();
    }


    public int getLineNumber(int PC) {
        if (lineNumberTableEntries == null) {
            return -1;
        }
        for (LineNumberTableEntry entry : lineNumberTableEntries) {
            if (PC >= entry.getStartPc()) {
                return entry.getLineNum();
            }
        }
        return -1;
    }

}
