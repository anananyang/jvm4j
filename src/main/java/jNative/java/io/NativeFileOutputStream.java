package jNative.java.io;

import jNative.JNativeMethod;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.LocalVariableTable;
import runtime.rtda.share.heap.JObject;

public class NativeFileOutputStream {

    public static class WriteBytes implements JNativeMethod {

        @Override
        public void execute(Frame frame) {
            LocalVariableTable table = frame.getLocalVaribleTable();
            JObject thisObj = table.getThis();
            // WriteBytes 的参数
            JObject byteArrRef = table.getRef(1);
            int off = table.getInt(2);
            int len = table.getInt(3);
            boolean append = table.getBoolean(4);
            // char
            Byte[] bytes = (Byte[]) byteArrRef.getArray();
            byte[] primitiveBytes = toPrimitiveBytes(bytes);
            String str = null;
            try {
                str = new String(primitiveBytes, off, len, "UTF-8");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(str);
        }

        private byte[] toPrimitiveBytes(Byte[] bytes) {
            byte[] primitiveBytes = new byte[bytes.length];
            for(int i = 0; i < bytes.length; i++) {
                primitiveBytes[i] = bytes[i];
            }
            return primitiveBytes;
        }

    }
}
