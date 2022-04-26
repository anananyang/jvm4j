package runtime.instructions.store.lstore;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.instructions.store.Store;
import runtime.rtda.priv.Frame;

public class LSTORE_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Store.lstore(frame, 3);
    }
}
