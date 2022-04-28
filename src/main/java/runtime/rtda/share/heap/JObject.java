package runtime.rtda.share.heap;

public class JObject {
    private JClass jClass;
    // 兼容数组和普通对象的字段
    private Object data;

    /**
     * 用于构造普通对象的构造器
     *
     * @param jClass
     */
    public JObject(JClass jClass) {
        this.jClass = jClass;
        this.data = new Slots(jClass.getInstanceSlotCount());
    }

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
}
