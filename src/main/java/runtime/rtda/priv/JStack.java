package runtime.rtda.priv;

import java.util.ArrayList;
import java.util.List;

/**
 * 虚拟机栈
 */
public class JStack {
    // 栈的最大长度
    private int maxSize = 1024;
    private List<Frame> list;

    public JStack() {
        this.list = new ArrayList<>();
    }

    public JStack(int maxSize) {
        assert maxSize > 0;
        this.maxSize = maxSize;
        this.list = new ArrayList<>();
    }

    public void push(Frame frame) {
        if (list.size() > maxSize) {
            throw new StackOverflowError();
        }
        list.add(frame);
    }

    public Frame pop() {
        if (list.isEmpty()) {
            throw new RuntimeException("jthread stack is empty!");
        }
        int maxIndex = list.size() - 1;
        return list.remove(maxIndex);
    }

    public Frame top() {
        if (isEmpty()) {
            return null;
        }
        int maxIndex = list.size() - 1;
        return list.get(maxIndex);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void clear() {
        while (isEmpty()) {
            pop();
        }
    }

    public Frame[] getFrames(int skip) {
        int len = list.size() - skip;
        Frame[] frames = new Frame[len];
        for (int i = 0; i < len; i++) {
            frames[i] = list.get(len - 1 - i);
        }
        return frames;
    }
}
