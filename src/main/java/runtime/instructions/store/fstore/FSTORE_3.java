package runtime.instructions.store.fstore;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.instructions.store.Store;
import runtime.rtda.priv.Frame;

public class FSTORE_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Store.fstore(frame, 3);
    }
}
