package runtime.rtda.share.heap.rtcp;

import eum.ConstantType;

public class JConstant<T> {
    private T value;
    private ConstantType type;

    public JConstant(T value, ConstantType type) {
        this.value = value;
        this.type = type;

    }

    public T getValue() {
        return value;
    }

    public ConstantType getType() {
        return type;
    }
}
