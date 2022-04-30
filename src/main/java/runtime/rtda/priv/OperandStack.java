package runtime.rtda.priv;

import runtime.rtda.Slot;
import runtime.rtda.share.heap.JMethod;
import runtime.rtda.share.heap.JObject;

public class OperandStack {
    private Slot[] slots;
    private int size;
    private JMethod jMethod;

    public OperandStack(int maxStack, JMethod jMethod) {
        this.slots = new Slot[maxStack];
        this.jMethod = jMethod;
    }

    public void pushBoolean(boolean value) {
        Slot<Boolean> slot = new Slot<>(value);
        pushSlot(slot);
    }

    public boolean popBoolean() {
        return (boolean) popSlot().get();
    }

    public void pushByte(byte value) {
        Slot<Byte> slot = new Slot<>(value);
        pushSlot(slot);
    }

    public byte popByte() {
        return (byte) popSlot().get();
    }

    public void pushShort(short value) {
        Slot<Short> slot = new Slot<>(value);
        pushSlot(slot);
    }

    public short popShort() {
        return (short) popSlot().get();
    }

    public void pushChar(char value) {
        Integer val = (int) value;
        Slot<Integer> slot = new Slot<>(val);
        pushSlot(slot);
    }

    public char popChar() {
        int val = (int) popSlot().get();
        return (char) val;
    }

    public void pushInt(int value) {
        Slot<Integer> slot = new Slot<>(value);
        pushSlot(slot);
    }

    public int popInt() {
        return (int) popSlot().get();
    }

    public void pushLong(long value) {
        Slot<Long> slot = new Slot<>(value);
        pushSlot(slot);
    }

    public long popLong() {
        return (long) popSlot().get();
    }

    public void pushFloat(float value) {
        Slot<Float> slot = new Slot<>(value);
        pushSlot(slot);
    }

    public float popFloat() {
        return (float) popSlot().get();
    }

    public void pushDouble(double value) {
        Slot<Double> slot = new Slot<>(value);
        pushSlot(slot);
    }

    public double popDouble() {
        return (double) popSlot().get();
    }

    public void pushRef(JObject value) {
        Slot<JObject> slot = new Slot<>(value);
        pushSlot(slot);
    }

    public JObject popRef() {
        return (JObject) popSlot().get();
    }

    public void pushSlot(Slot slot) {
        slots[size] = slot;
        size++;
//        if(jMethod != null){
//            System.out.println(jMethod.getjClass().getThisClassName() + "." + jMethod.getName() + " push : " + slot.get());
//        }

    }

    public Slot popSlot() {
        size--;
        Slot slot = slots[size];
        slots[size] = null;
//        if(jMethod != null){
//            System.out.println(jMethod.getjClass().getThisClassName() + "." + jMethod.getName() + " pop : " + slot.get());
//        }
        return slot;
    }

    public JObject getRefFromTop(int n) {
        int index = size - 1 - n;
        if (index < 0) {
            return null;
        }
        return (JObject) slots[index].get();
    }

}
