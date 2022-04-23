package classFile.reader;

import java.nio.ByteBuffer;

public class ByteReader {
    private int curIdx = 0;  // 当前读取到哪一位
    private int size;
    private ByteBuffer byteBuffer;

    ByteReader(byte[] bytes) {
        assert bytes != null;
        this.size = bytes.length;
        this.byteBuffer = ByteBuffer.wrap(bytes).asReadOnlyBuffer();
    }

    public byte readUnit8() {
        if (curIdx + 1 > size) {
            throw new RuntimeException("no byte can read");
        }
        byte b = byteBuffer.get();
        curIdx++;
//        System.out.println("readUnit8：curIdx: " + curIdx + "，realIndex: " + byteBuffer.position());
        return b;
    }

    public short readUnit16() {
        if (curIdx + 2 > size) {
            throw new RuntimeException("no byte can read");
        }
        short value = byteBuffer.getShort();
        curIdx = curIdx + 2;
//        System.out.println("readUnit16：curIdx: " + curIdx + "，realIndex: " + byteBuffer.position());
        return value;
    }

    public int readUint32() {
        if (curIdx + 4 > size) {
            throw new RuntimeException("no byte can read");
        }
        int value = byteBuffer.getInt();
        curIdx = curIdx + 4;
//        System.out.println("readUint32：curIdx: " + curIdx + "，realIndex: " + byteBuffer.position());
        return value;
    }


    public long readUint64() {
        if (curIdx + 8 > size) {
            throw new RuntimeException("no byte can read");
        }
        long value = byteBuffer.getLong(curIdx);
        curIdx = curIdx + 8;
//        System.out.println("readUint64：curIdx: " + curIdx + "，realIndex: " + byteBuffer.position());
        return value;
    }

    /**
     * 读取多个 16 位数字，读取几个由第一个决定
     *
     * @return
     */
    public short[] readUint16s() {
        short len = this.readUnit16();
        short[] arr = new short[len];
        for (short i = 0; i < len; i++) {
            arr[i] = readUnit16();
        }
        return arr;
    }

    public byte[] readBytes(int len) {
        if (curIdx + len > size) {
            throw new RuntimeException("no byte can read");
        }
        byte[] bytes = new byte[len];
        byteBuffer.get(bytes);
        curIdx = curIdx + len;
//        System.out.println("readBytes：curIdx: " + curIdx + "，realIndex: " + byteBuffer.position());
        return bytes;
    }

}
