package classFile.attributes;

public class AttributeInfo {
    protected String attrName;
    protected int attrLen;

    public AttributeInfo(String attrName, int attrLen) {
        this.attrName = attrName;
        this.attrLen = attrLen;
    }

    public String getAttrName() {
        return attrName;
    }
}
