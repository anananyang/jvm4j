package jNative.sun.misc;

import classFile.ClassFile;
import classFile.reader.ClassReader;
import classpath.ClassPath;
import jNative.JNativeMethod;
import org.apache.commons.cli.*;
import runtime.instructions.base.InvokeMethodLogic;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;
import runtime.rtda.share.heap.StringPool;

public class VM {


    public static class Initialize implements JNativeMethod {

        @Override
        public void execute(Frame frame) {
            JClass vmClass = frame.getjMethod().getjClass();
            // 获取静态成员
            JObject savedProps = vmClass.getRefVar("savedProps", "Ljava/util/Properties;");
            JObject keyJstring = StringPool.getJString(vmClass.getLoader(), "foo");
            JObject valueJstring = StringPool.getJString(vmClass.getLoader(), "bar");
            frame.getOperandStack().pushRef(savedProps);
            frame.getOperandStack().pushRef(keyJstring);
            frame.getOperandStack().pushRef(valueJstring);

            JClass propClass = vmClass.getLoader().loadClass("java/util/Properties");
            JMethod setPropMethod = propClass.getMethod("setProperty",
                    "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;",
                    false);
            InvokeMethodLogic.invokeMethod(frame, setPropMethod);

        }
    }


}
