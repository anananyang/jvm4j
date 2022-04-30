package jNative.java.lang;

import jNative.JNativeMethod;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JClassLoader;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.StringPool;

public class NativeClass {

    /**
     * 获取原始类型
     */
    public static class GetPrimitiveClass implements JNativeMethod {

        @Override
        public void execute(Frame frame) {
            // 获取类名(作为参数传入，被设置到局部变量表)
            JObject jstring = frame.getLocalVaribleTable().getRef(0);
            String className = StringPool.getRealString(jstring);
            // 加载类
            JClassLoader loader = frame.getjMethod().getjClass().getLoader();
            JClass jClass = loader.loadClass(className);
            JObject classObj = jClass.getClassObj();
            frame.getOperandStack().pushRef(classObj);
        }
    }

    public static class GetName0 implements JNativeMethod {

        @Override
        public void execute(Frame frame) {
            JObject thisObj = frame.getLocalVaribleTable().getThis();
            JClass jClass = (JClass) thisObj.getExtra();
            String className = jClass.getJavaName();
            JObject jstring = StringPool.getJString(jClass.getLoader(), className);
            frame.getOperandStack().pushRef(jstring);
        }
    }

    public static class DesiredAssertionStatus0 implements JNativeMethod {

        @Override
        public void execute(Frame frame) {
            frame.getOperandStack().pushInt(0);
        }
    }

}
