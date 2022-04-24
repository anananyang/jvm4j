package runtime.rtda;

public class OperandStack {
    private Slot[] slots;
    private int size;

    public OperandStack(int maxStack) {
        assert maxStack > 0;
        this.slots = new Slot[maxStack];
    }

    public void pushBoolean(boolean value) {
        Slot<Boolean> slot = new Slot<>(value);
        slots[size] = slot;
        size++;
    }

    public boolean popBoolean() {
        size--;
        return (boolean) slots[size].get();
    }

    public void pushByte(byte value) {
        Slot<Byte> slot = new Slot<>(value);
        slots[size] = slot;
        size++;
    }

    public int popByte() {
        size--;
        return (byte) slots[size].get();
    }

    public void pushShort(short value) {
        Slot<Short> slot = new Slot<>(value);
        slots[size] = slot;
        size++;
    }

    public short popShort() {
        size--;
        return (short) slots[size].get();
    }

    public void pushChar(char value) {
        Slot<Character> slot = new Slot<>(value);
        slots[size] = slot;
        size++;
    }

    public char popChar() {
        size--;
        return (Character) slots[size].get();
    }

    public void pushInt(int value) {
        Slot<Integer> slot = new Slot<>(value);
        slots[size] = slot;
        size++;
    }

    public int popInt() {
        size--;
        return (int) slots[size].get();
    }

    public void pushLong(long value) {
        Slot<Long> slot = new Slot<>(value);
        slots[size] = slot;
        size++;
    }

    public long popLong() {
        size--;
        return (long) slots[size].get();
    }

    public void pushFloat(float value) {
        Slot<Float> slot = new Slot<>(value);
        slots[size] = slot;
        size++;
    }

    public float popFloat() {
        size--;
        return (float) slots[size].get();
    }

    public void pushDouble(double value) {
        Slot<Double> slot = new Slot<>(value);
        slots[size] = slot;
        size++;
    }

    public double popDouble() {
        size--;
        return (double) slots[size].get();
    }

    public void pushRef(Object value) {
        Slot<Object> slot = new Slot<>(value);
        slots[size] = slot;
        size++;
    }

    public Object popRef(int idx) {
        size--;
        return (Object) slots[size].get();
    }


}
