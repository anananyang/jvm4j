package runtime.rtda.share.heap;

public class JObject {
    private JClass jClass;
    // 兼容数组、字符串以及普通对象的字段
    private Object data;
    // 额外信息
    private Object extra;

    /**
     * 用于构造普通对象的构造器
     *
     * @param jClass
     */
    public JObject(JClass jClass) {
        this.jClass = jClass;
        this.data = new Slots(jClass.getInstanceSlotCount());
    }

    /**
     * 用于构造数组对象的构造器
     *
     * @param jClass
     * @param data
     */
    public JObject(JClass jClass, Object[] data) {
        this.jClass = jClass;
        this.data = data;
    }

    public JClass getjClass() {
        return jClass;
    }

    public Slots getFields() {
        return (Slots) data;
    }

    public boolean isInstanceOf(JClass other) {
        return other.isAssignableFrom(this.jClass);
    }

    public Byte[] getByteArray() {
        return (Byte[]) data;
    }

    public Boolean[] getBooleanArray() {
        return (Boolean[]) data;
    }

    public Character[] getCharArray() {
        return (Character[]) data;
    }

    public Short[] getShortArray() {
        return (Short[]) data;
    }

    public Integer[] getIntArray() {
        return (Integer[]) data;
    }

    public Long[] getLongArray() {
        return (Long[]) data;
    }

    public Float[] getFloatArray() {
        return (Float[]) data;
    }

    public Double[] getDoubleArray() {
        return (Double[]) data;
    }

    public JObject[] getRefArray() {
        return (JObject[]) data;
    }

    public Object[] getArray() {
        return (Object[]) data;
    }

    public int getArrayLength() {
        return ((Object[]) data).length;
    }

    public void setRefVar(String name, String descriptor, JObject val) {
        JField jField = jClass.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        slots.setRef(jField.getSlotId(), val);
    }

    public JObject getRefVar(String name, String descriptor) {
        JField jField = jClass.lookupField(name, descriptor);
        Slots slots = (Slots) data;
        return slots.getRef(jField.getSlotId());
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

}
