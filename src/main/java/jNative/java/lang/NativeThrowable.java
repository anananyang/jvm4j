package jNative.java.lang;

import jNative.JNativeMethod;
import runtime.rtda.priv.Frame;
import runtime.rtda.priv.JThread;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;

public class NativeThrowable {

    public static class FillInStackTrace implements JNativeMethod {
        @Override
        public void execute(Frame frame) {
            // 异常类
            JObject jObject = frame.getLocalVaribleTable().getThis();
            frame.getOperandStack().pushRef(jObject);
            // 获取栈轨迹
            JStackTraceElement[] stackTraceElements = createStackTraceElements(jObject,
                    frame.getjThread());
            jObject.setExtra(stackTraceElements);
        }

        private JStackTraceElement[] createStackTraceElements(JObject jObject, JThread jThread) {
            // 先计算到抛出异常的方法有多少栈帧，栈顶有两帧正在执行 fillInStackTrace 和 fillInStackTrace 需要跳过, 减掉 java/lang/Object
            int skip = disanceToObject(jObject.getjClass()) + 2 - 1;
            // 获取跳过 skip 个栈顶元素之后的剩余栈帧(注意，不能破坏栈帧)
            Frame[] frames = jThread.getFrames(skip);
            JStackTraceElement[] jStackTraceElements = new JStackTraceElement[frames.length];
            for (int i = 0; i < frames.length; i++) {
                jStackTraceElements[i] = createStackTraceElement(frames[i]);
            }
            return jStackTraceElements;
        }

        /**
         * 计算异常类的构造器，异常类会调用父类的构造器，所以只要计算该异常类有多少层的继承关系
         *
         * @param jClass
         * @return
         */
        private int disanceToObject(JClass jClass) {
            int distance = 0;
            for (JClass c = jClass; c != null; c = c.getSuperClass()) {
                distance++;
            }
            return distance;
        }

        private JStackTraceElement createStackTraceElement(Frame frame) {
            JMethod jMethod = frame.getjMethod();
            JClass jClass = jMethod.getjClass();
            return new JStackTraceElement(jClass.getSourceFile(),
                    jClass.getJavaName(),
                    jMethod.getName(),
                    jMethod.getLineNumber(frame.getNextPC() - 1));
        }
    }


}
