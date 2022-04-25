package classFile.constants;

public abstract class ConstantInfo {
    protected int tag;

    ConstantInfo(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }
}