package runtime.rtda;

/**
 * 局部变量表是按索引访问的，所以我们可以认为它内部是一个数组
 */
public class LocalVariableTable {

    private Slot[] slots;

    public LocalVariableTable(int maxLocal) {
        assert maxLocal > 0;
        this.slots = new Slot[maxLocal];
    }

    public void setBoolean(int idx, boolean value) {
        Slot<Boolean> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public boolean getBoolean(int idx) {
        return (boolean) slots[idx].get();
    }

    public void setByte(int idx, byte value) {
        Slot<Byte> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public int getByte(int idx) {
        return (byte) slots[idx].get();
    }

    public void setShort(int idx, short value) {
        Slot<Short> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public short getShort(int idx) {
        return (short) slots[idx].get();
    }

    public void setChar(int idx, char value) {
        Slot<Character> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public char getChar(int idx) {
        return (Character) slots[idx].get();
    }

    public void setInt(int idx, int value) {
        Slot<Integer> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public int getInt(int idx) {
        return (int) slots[idx].get();
    }

    public void setLong(int idx, long value) {
        Slot<Long> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public long getLong(int idx) {
        return (long) slots[idx].get();
    }

    public void setFloat(int idx, float value) {
        Slot<Float> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public float getFloat(int idx) {
        return (float) slots[idx].get();
    }

    public void setDouble(int idx, double value) {
        Slot<Double> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public double getDouble(int idx) {
        return (double) slots[idx].get();
    }

    public void setRef(int idx, Object value) {
        Slot<Object> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public Object getRef(int idx) {
        return (Object) slots[idx].get();
    }

}
