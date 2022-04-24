package classFile.reader;

import classFile.ConstantPool;
import classFile.MemberInfo;

public class MemberReader {

    public static MemberInfo[] read(ByteReader byteReader, ConstantPool constantPool) {
        short len = byteReader.readUnit16();
        if(len == 0) {
            return null;
        }
        MemberInfo[] memberInfos = new MemberInfo[len];
        for (short i = 0; i < len; i++) {
            memberInfos[i] = new MemberInfo(byteReader, constantPool);
        }
        return memberInfos;
    }
}
