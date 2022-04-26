package classFile.constants;

import classFile.reader.ByteReader;
import eum.ConstantType;
import util.MUTF8;

import java.io.IOException;

public class ConstantUtf8Info extends ConstantInfo {

    private String value;

    public ConstantUtf8Info(ConstantType type, ByteReader reader) {
        super(type);
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
