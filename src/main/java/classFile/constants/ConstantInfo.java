package classFile.constants;

public abstract class ConstantInfo {
    protected byte tag;

    ConstantInfo(byte tag) {
        this.tag = tag;
    }

    public byte getTag() {
        return tag;
    }
}