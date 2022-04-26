package runtime.rtda.share.heap;

public class JObject {
    private JClass jClass;
    // 字段
    private Slots fields;

    public JObject(JClass jClass) {
        this.jClass = jClass;
        this.fields = new Slots(jClass.getInstanceSlotCount());
    }

    public JClass getjClass() {
        return jClass;
    }

    public Slots getFields() {
        return fields;
    }

    public boolean isInstanceOf(JClass other) {
        return other.isAssignableFrom(this.jClass);
    }
}
