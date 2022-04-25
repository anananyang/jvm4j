package runtime.instructions;

import java.nio.ByteBuffer;

public class ByteCodeReader {
    private ByteBuffer byteBuffer;

    public ByteCodeReader(byte[] bytes) {
        this(bytes,  0);
    }

    public ByteCodeReader(byte[] bytes, int pc) {
        this.byteBuffer = ByteBuffer.wrap(bytes);
        this.byteBuffer.position(pc);
    }

    public void setPC(int pc) {
        this.byteBuffer.position(pc);
    }

    public int readUint8() {
        byte val = byteBuffer.get();
        return val & 0xff;
    }

    public int readInt8() {
        int val = byteBuffer.get();
        return val;
    }

    public int readInt16() {
        int val = byteBuffer.getShort();
        return val;
    }

    public int readUint16() {
        short val = byteBuffer.getShort();
        return val & 0xffff;
    }

    public int readInt32() {
        int val = byteBuffer.getInt();
        return val;
    }

    public int readUint32() {
        int val = byteBuffer.getInt();
        return val & 0xffffffff;
    }

    public int[] readInt32s(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = readInt32();
        }
        return arr;
    }

    /**
     * 4 字节对齐
     */
    public void skipPadding() {
        int pc = getPc();
        while (pc % 4 != 0) {
            readInt8();
        }
    }

    public int getPc() {
        return this.byteBuffer.position();
    }
}
