package jNative.java.lang;

import jNative.JNativeMethod;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.StringPool;

public class NativeString {

    /**
     * 将字符串放入池中
     */
    public static class StringIntern implements JNativeMethod {

        @Override
        public void execute(Frame frame) {
            JObject jstring = frame.getLocalVaribleTable().getThis();
            // getRealString 时，如果发现不在池中，那么就会放入池中
            String realString = StringPool.getRealString(jstring);
            JObject interned = StringPool.getJString(frame.getjMethod().getjClass().getLoader(),
                    realString);
            frame.getOperandStack().pushRef(interned);
        }
    }
}
