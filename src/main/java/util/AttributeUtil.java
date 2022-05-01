package util;

import classFile.attributes.AttributeInfo;
import eum.AttributeType;

public abstract class AttributeUtil {

    public static AttributeInfo getFirstAttrByType(AttributeType attributeType,
                                            AttributeInfo[] attributeInfos) {
        if (attributeInfos == null) {
            return null;
        }
        String name = attributeType.name();
        for (AttributeInfo attribute : attributeInfos) {
            if (name.equals(attribute.getAttrName())) {
                return attribute;
            }
        }
        return null;
    }
}
