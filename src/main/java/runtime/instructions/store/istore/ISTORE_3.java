package runtime.instructions.store.istore;

import runtime.instructions.base.NoOperandsInstruction;
import runtime.instructions.store.Store;
import runtime.rtda.priv.Frame;

public class ISTORE_3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Store.istore(frame, 2);
    }
}
