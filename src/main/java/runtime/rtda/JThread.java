package runtime.rtda;

public class JThread {
    private short pc;
    private JStack stack;

    public JThread() {
        this.stack = new JStack(1024);
    }

    public short getPc() {
        return pc;
    }

    public void setPc(short pc) {
        this.pc = pc;
    }

    public void pushFrame(Frame frame) {
        stack.push(frame);
    }

    public Frame popFrame(Frame frame) {
        return stack.pop(frame);
    }

    public Frame curFrame() {
        return stack.top();
    }
}
