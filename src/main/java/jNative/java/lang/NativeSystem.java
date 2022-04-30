package jNative.java.lang;

import jNative.JNativeMethod;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.LocalVariableTable;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JObject;

public class NativeSystem {

    public static class ArrayCopy implements JNativeMethod {

        @Override
        public void execute(Frame frame) {
            // 获取参数
            LocalVariableTable table = frame.getLocalVaribleTable();
            JObject src = table.getRef(0);
            int srcPos = table.getInt(1);
            JObject dest = table.getRef(2);
            int destPos = table.getInt(3);
            int length = table.getInt(4);

            if (src == null || dest == null) {
                throw new NullPointerException();
            }

            if (!checkArrayCopy(src, dest)) {
                throw new ArrayStoreException();
            }

            if (srcPos < 0
                    || destPos < 0
                    || length < 0
                    || srcPos + length > src.getArrayLength()
                    || destPos + length > dest.getArrayLength()) {
                throw new IndexOutOfBoundsException();
            }

            doArrayCopy(src, dest, srcPos, destPos, length);
        }

        private boolean checkArrayCopy(JObject src, JObject dest) {
            JClass srcClass = src.getjClass();
            JClass destClass = dest.getjClass();
            if (!srcClass.isArray() || !destClass.isArray()) {
                return false;
            }
            // 如果是原始类型
            if (srcClass.getComponentClass().isPrimitiveType()
                    || destClass.getComponentClass().isPrimitiveType()) {
                return srcClass == destClass;
            }

            return true;
        }

        private void doArrayCopy(JObject src, JObject dest, int srcPos, int destPos, int length) {
            // 直接利用 java 内置的 arraycopy 进行复制
            System.arraycopy(src.getArray(), srcPos, dest.getArray(), destPos, length);
        }


    }
}
