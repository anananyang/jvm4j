package runtime.instructions.store.astore;

import runtime.instructions.base.Index8Instruction;
import runtime.instructions.store.Store;
import runtime.rtda.priv.Frame;

public class ASTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Store.astore(frame, index);
    }
}
