package runtime.rtda.share.heap;


import runtime.rtda.Slot;

public class Slots {

    private Slot[] slots;
    private JObject ref;   // 属于哪个对象

    public Slots(int slotCount) {
        this.ref = ref;    // 当 slots 存放类的静态变量时，ref 为null
        this.slots = new Slot[slotCount];
    }

    public void setRef(JObject ref) {
        this.ref = ref;
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
        Slot slot = slots[idx];
        if(slot == null) {
            return 0;
        }
        return (int) slot.get();
    }

    public void setLong(int idx, long value) {
        Slot<Long> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public long getLong(int idx) {
        Slot slot = slots[idx];
        if(slot == null) {
            return 0;
        }
        return (long) slot.get();
    }

    public void setFloat(int idx, float value) {
        Slot<Float> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public float getFloat(int idx) {
        Slot slot = slots[idx];
        if(slot == null) {
            return 0;
        }
        return (float) slot.get();
    }

    public void setDouble(int idx, double value) {
        Slot<Double> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public double getDouble(int idx) {
        Slot slot = slots[idx];
        if(slot == null) {
            return 0;
        }
        return (double) slot.get();
    }

    public void setRef(int idx, JObject value) {
        Slot<JObject> slot = new Slot<>(value);
        slots[idx] = slot;
    }

    public JObject getRef(int idx) {
        Slot slot = slots[idx];
        if(slot == null) {
            return null;
        }
        return (JObject) slot.get();
    }

}
