package runtime.instructions.comparisons.ifcond;

import runtime.instructions.base.BranchInstruction;
import runtime.rtda.priv.Frame;

/**
 * 将操作数栈定的 int 变量弹出，跟 0 进行表，如果 x >= 0 则跳转
 */
public class IFGE extends BranchInstruction {

    @Override
    protected boolean check(Frame frame) {
        int x = frame.getOperandStack().popInt();
        return x >= 0;
    }
}
