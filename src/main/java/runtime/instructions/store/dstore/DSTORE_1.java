package runtime.instructions.store.dstore;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.instructions.store.Store;
import runtime.rtda.priv.Frame;

public class DSTORE_1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Store.dstore(frame, 1);
    }
}
