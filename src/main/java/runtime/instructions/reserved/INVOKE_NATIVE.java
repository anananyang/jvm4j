package runtime.instructions.reserved;

import jNative.JNativeMethod;
import jNative.Registry;
import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JMethod;

/**
 * 使用预留的指令调用本地方法
 */
public class INVOKE_NATIVE extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        JMethod method = frame.getjMethod();
        String className = method.getjClass().getThisClassName();
        String methodName = method.getName();
        String methodDescriptor = method.getDescriptor();
        JNativeMethod nativeMethod = Registry.findNativeMethod(className, methodName, methodDescriptor);
        if (nativeMethod == null) {
            throw new UnsatisfiedLinkError(className + "." + methodName + methodDescriptor);
        }
//        System.out.println("native method: " + className + "." + methodName + methodDescriptor);
        // 执行本地方法
        nativeMethod.execute(frame);
    }
}
