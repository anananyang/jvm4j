package runtime.rtda.share.heap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 字符串池
 */
public class StringPool {

    private static final Map<String, JObject> internedStrings = new HashMap<>();
    private static final Map<JObject, String> jStringToStringMap = new HashMap<>();

    /**
     * 因为是用 java 实现的，这里就很简单了，因为 str 一般都是从常量池获取的，获取时已经转成了 String 对象了
     * 这里就是把 String 对象再转换成 JString 对象
     *
     * @param loader
     * @param str
     * @return
     */
    public static JObject getJString(JClassLoader loader, String str) {
        if (internedStrings.containsKey(str)) {
            return internedStrings.get(str);
        }
        JClass charArrClass = loader.loadClass("[C");
        Character[] chars = toCharacterArr(str);
        JObject charArr = new JObject(charArrClass, chars);

        // 加载字符串类
        JClass stringClass = loader.loadClass("java/lang/String");
        JObject jstring = stringClass.newObject();
        jstring.setRefVar("value", "[C", charArr);
        internedStrings.put(str, jstring);
        jStringToStringMap.put(jstring, str);
        return jstring;
    }

    public static String getRealString(JObject jstring) {
        if (jstring == null) {
            return null;
        }
        String str = jStringToStringMap.get(jstring);
        if (str != null) {
            return str;
        }
        // 获取到数组对象
        JObject charArrRef = jstring.getRefVar("value", "[C");
        str = toRealString(charArrRef.getCharArray());

        return str;
    }

    private static Character[] toCharacterArr(String str) {
        char[] chars = str.toCharArray();
        Character[] characters = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            characters[i] = chars[i];
        }
        return characters;
    }

    private static String toRealString(Character[] charArr) {
        char[] chars = new char[charArr.length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = charArr[i];
        }
        return new String(chars);
    }

}
