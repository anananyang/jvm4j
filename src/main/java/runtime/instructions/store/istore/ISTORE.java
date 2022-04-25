package runtime.instructions.store.istore;

import runtime.instructions.base.Index8Instruction;
import runtime.instructions.store.Store;
import runtime.rtda.priv.Frame;

public class ISTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Store.istore(frame, index);
    }
}
