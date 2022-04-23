package classFile.constants;

import classFile.reader.ByteReader;
import util.MUTF8;

import java.io.IOException;
import java.util.Arrays;

public class ConstantUtf8Info extends ConstantInfo {

    private String value;

    public ConstantUtf8Info(byte tag, ByteReader reader) {
        super(tag);
        short len = reader.readUnit16();
        byte[] bytes = reader.readBytes(len);
        try {
            this.value = MUTF8.toUtf8String(bytes);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
