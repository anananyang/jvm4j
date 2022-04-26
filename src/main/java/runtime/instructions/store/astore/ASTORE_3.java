package runtime.instructions.store.astore;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.instructions.store.Store;
import runtime.rtda.priv.Frame;

public class ASTORE_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Store.astore(frame, 3);
    }
}
