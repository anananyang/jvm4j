package runtime.instructions.reserved;

import jNative.JNativeMethod;
import jNative.Registry;
import runtime.instructions.base.NoOperandsInstruction;
import runtime.rtda.priv.Frame;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.MethodDescriptor;

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
//            handleUnimplementsNativeResult(frame);
            throw new UnsatisfiedLinkError(className + "." + methodName + methodDescriptor);
        } else {
            nativeMethod.execute(frame);
        }

        // 执行本地方法

    }


    private void handleUnimplementsNativeResult(Frame frame) {
        JMethod jMethod = frame.getjMethod();
        String returnType = jMethod.getReturnType();
        switch (returnType.charAt(0)) {
            case 'V':
                break;
            case 'I':
                frame.getOperandStack().pushInt(0);
                break;
            case 'J':
                frame.getOperandStack().pushInt(0);
                break;
            case 'D':
                frame.getOperandStack().pushInt(0);
                break;
            case 'F':
                frame.getOperandStack().pushInt(0);
                break;
            case 'Z':
                frame.getOperandStack().pushBoolean(true);
                break;
            case 'B':
                frame.getOperandStack().pushByte((byte) 0);
                break;
            case 'L':
                frame.getOperandStack().pushRef(null);
                break;

        }
    }
}
