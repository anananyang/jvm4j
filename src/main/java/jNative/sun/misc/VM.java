package jNative.sun.misc;

import classFile.ClassFile;
import classFile.reader.ClassReader;
import classpath.ClassPath;
import jNative.JNativeMethod;
import org.apache.commons.cli.*;
import runtime.instructions.base.InvokeMethodLogic;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.*;

public class VM {


    public static class Initialize implements JNativeMethod {

        @Override
        public void execute(Frame frame) {
            JClassLoader loader = frame.getjMethod().getjClass().getLoader();
            JClass jClass = loader.loadClass("java/lang/System");
            JMethod jMethod = jClass.lookupMethod("initializeSystemClass", "()V");
            InvokeMethodLogic.invokeMethod(frame, jMethod);
        }
    }


}
