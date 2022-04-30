package jNative.java.lang;

import jNative.JNativeMethod;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JObject;

public class NativeObject {


    public static class GetClass implements JNativeMethod {
        @Override
        public void execute(Frame frame) {
            JObject thisObj = frame.getLocalVaribleTable().getThis();
            JObject classObj = thisObj.getjClass().getClassObj();
            frame.getOperandStack().pushRef(classObj);
        }
    }

    // 直接使用原对象的 hashcode
    public static class HashCode implements JNativeMethod {
        @Override
        public void execute(Frame frame) {
            JObject jObject = frame.getLocalVaribleTable().getThis();
            int hashcode = jObject.hashCode();
            frame.getOperandStack().pushInt(hashcode);
        }
    }

//    // 直接使用原对象的 clone
//    public static class ObjectClone implements JNativeMethod {
//        @Override
//        public void execute(Frame frame) {
//            JObject jObject = frame.getLocalVaribleTable().getThis();
//            JClass jClass = frame.getjMethod().getjClass();
//            JClass cloneable = jClass.getLoader().loadClass("java/lang/Cloneab");
//            if(!jClass.isImplements(cloneable)) {
//                throw new RuntimeException(new CloneNotSupportedException());
//            }
//            frame.getOperandStack().pushRef(jObject.jclone());
//        }
//    }
}
