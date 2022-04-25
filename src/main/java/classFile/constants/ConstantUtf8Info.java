package classFile.constants;

import classFile.reader.ByteReader;
import util.MUTF8;

import java.io.IOException;

public class ConstantUtf8Info extends ConstantInfo {

    private String value;

    public ConstantUtf8Info(int tag, ByteReader reader) {
        super(tag);
        int len = reader.readUnit16();
        byte[] bytes = reader.readBytes(len);
        try {
            this.value = MUTF8.toUtf8String(bytes);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getValue() {
        return value;
    }
}
