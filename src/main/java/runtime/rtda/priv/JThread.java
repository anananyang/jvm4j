package runtime.rtda.priv;

import runtime.rtda.share.heap.JMethod;

public class JThread {
    private int pc;
    private JStack stack;

    public JThread() {
        this.stack = new JStack(1024);
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public void pushFrame(Frame frame) {
        stack.push(frame);
    }

    public Frame popFrame() {
        return stack.pop();
    }

    public Frame topFrame() {
        return stack.top();
    }

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    public Frame newFrame(JMethod jMethod) {
        return new Frame(this, jMethod);
    }

    public void clearStack() {
        stack.clear();
    }

    public Frame[] getFrames(int skip) {
        return stack.getFrames(skip);
    }
}
