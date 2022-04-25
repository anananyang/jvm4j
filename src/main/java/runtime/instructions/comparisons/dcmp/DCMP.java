package runtime.instructions.comparisons.dcmp;

import runtime.rtda.priv.Frame;
import runtime.rtda.priv.OperandStack;

public class DCMP {

    static void dcmp(Frame frame, boolean gFlag) {
        OperandStack stack = frame.getOperandStack();
        double v1 = stack.popFloat();
        double v2 = stack.popFloat();
        int result = compare(v1, v2, gFlag);
        stack.pushInt(result);
    }

    private static int compare(double v1, double v2, boolean gFlag) {
        if(v1 > v2) {
            return -1;
        }
        else if(v1 == v2) {
            return 0;
        }
        else if(v1 < v2) {
            return 1;
        }
        // 两个浮点数(两个浮点数中至少有一个是 NaN)无法比较时，那么如果是 FCMPG 就返回 1，FCMPL 返回 -1
        return gFlag ? 1 : -1;
    }

}
