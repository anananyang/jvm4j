package runtime.rtda;

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
            throw new RuntimeException("java.lang.StackOverflowError");
        }
        list.add(frame);
    }

    public Frame pop(Frame frame) {
        if (list.isEmpty()) {
            throw new RuntimeException("jvm stack is empty!");
        }
        int maxIndex = list.size() - 1;
        return list.remove(maxIndex);
    }

    public Frame top(Frame frame) {
        if (list.isEmpty()) {
            throw new RuntimeException("jvm stack is empty!");
        }
        int maxIndex = list.size() - 1;
        return list.get(maxIndex);
    }
}
