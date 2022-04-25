package runtime.instructions.store.lstore;

import runtime.instructions.base.Index8Instruction;
import runtime.instructions.store.Store;
import runtime.rtda.priv.Frame;

public class LSTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Store.lstore(frame, index);
    }
}
