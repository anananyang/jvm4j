package runtime.instructions.store.fstore;

import runtime.instructions.base.Index8Instruction;
import runtime.instructions.store.Store;
import runtime.rtda.priv.Frame;

public class FSTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Store.fstore(frame, index);
    }
}
