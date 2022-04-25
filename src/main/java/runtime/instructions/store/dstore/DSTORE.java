package runtime.instructions.store.dstore;

import runtime.instructions.base.Index8Instruction;
import runtime.instructions.store.Store;
import runtime.rtda.priv.Frame;

public class DSTORE extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        Store.dstore(frame, index);
    }
}
