package jNative;

import jNative.java.lang.*;
import jNative.sun.misc.VM;
import runtime.rtda.priv.Frame;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地方法的注册中心
 */
public class Registry {

    /**
     * 注册本地方法的方法
     */
    private static JNativeMethod REGISTER_NATIVES_METHOD = new JNativeMethod() {
        @Override
        public void execute(Frame frame) {
            // do nothing;
        }
    };

    private static Map<String, JNativeMethod> nativeMethodMap = new HashMap<>();

    static {
        registry("java/lang/Object",
                "getClass",
                "()Ljava/lang/Class;",
                new NativeObject.GetClass());


        registry("java/lang/Object",
                "hashCode",
                "()I",
                new NativeObject.HashCode());

        // 克隆暂未实现
//        registry("java/lang/Object",
//                "clone",
//                "()Ljava/lang/Object;",
//                new NativeObject.ObjectClone());

        registry("java/lang/Class",
                "getPrimitiveClass",
                "(Ljava/lang/String;)Ljava/lang/Class;",
                new NativeClass.GetPrimitiveClass());

        registry("java/lang/Class",
                "getName0",
                "()Ljava/lang/String;",
                new NativeClass.GetName0());

        registry("java/lang/Class",
                "desiredAssertionStatus0",
                "(Ljava/lang/Class;)Z",
                new NativeClass.DesiredAssertionStatus0());

        registry("java/lang/System",
                "arraycopy",
                "(Ljava/lang/Object;ILjava/lang/Object;II)V",
                new NativeSystem.ArrayCopy());

        registry("java/lang/Float",
                "floatToRawIntBits",
                "(F)I",
                new NativeMath.FloatToRawIntBits());

        registry("java/lang/Double",
                "doubleToRawLongBits",
                "(D)J",
                new NativeMath.DoubleToRawIntBits());

        registry("java/lang/Double",
                "longBitsToDouble",
                "(J)D",
                new NativeMath.LongBitsToDouble());

        registry("java/lang/String",
                "intern",
                "()Ljava/lang/String;",
                new NativeString.StringIntern());

        registry("sun/misc/VM",
                "initialize",
                "()V",
                new VM.Initialize());

    }

    public static void registry(String className,
                                String methodName,
                                String descriptor,
                                JNativeMethod nativeMethod) {
        String key = getKey(className, methodName, descriptor);
        nativeMethodMap.put(key, nativeMethod);
    }

    private static String getKey(String className, String methodName, String descriptor) {
        return className + "." + methodName + descriptor;
    }

    public static JNativeMethod findNativeMethod(String className,
                                                 String methodName,
                                                 String descriptor) {
        String key = getKey(className, methodName, descriptor);
        JNativeMethod jNativeMethod = nativeMethodMap.get(key);
        if (jNativeMethod != null) {
            return jNativeMethod;
        }
        // 如果是注册本地方法的方法
        if ("()V".equals(descriptor) && "registerNatives".equals(methodName)) {
            return REGISTER_NATIVES_METHOD;
        }
        return null;
    }
}
