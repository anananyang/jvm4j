package jNative.java.lang;

import jNative.JNativeMethod;
import runtime.rtda.priv.Frame;

public class NativeMath {

    public static class FloatToRawIntBits implements JNativeMethod {
        @Override
        public void execute(Frame frame) {
            float val = frame.getLocalVaribleTable().getFloat(0);
            frame.getOperandStack().pushInt(Float.floatToRawIntBits(val));
        }
    }

    public static class DoubleToRawIntBits implements JNativeMethod {
        @Override
        public void execute(Frame frame) {
            double val = frame.getLocalVaribleTable().getDouble(0);
            frame.getOperandStack().pushLong(Double.doubleToRawLongBits(val));
        }
    }

    public static class LongBitsToDouble implements JNativeMethod {
        @Override
        public void execute(Frame frame) {
            long val = frame.getLocalVaribleTable().getLong(0);
            frame.getOperandStack().pushDouble(Double.longBitsToDouble(val));
        }
    }
}
