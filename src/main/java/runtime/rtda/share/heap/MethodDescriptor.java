package runtime.rtda.share.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析后的方法描述符
 */
public class MethodDescriptor {
    private String raw;
    // 参数类型
    private List<String> paramTypes;
    // 返回值类型
    private String returnType;

    MethodDescriptor(String descriptor) {
        this.raw = descriptor;
        this.paramTypes = new ArrayList<>();
        parse();
    }

    private void parse() {
        CharReader charReader = new CharReader();
        parseParamTypes(charReader);
        parseReturnType(charReader);
        // 没有解析完
        if (!isFinish(charReader)) {
            throw new RuntimeException("bad method decriptor: " + raw);
        }
    }


    private boolean isFinish(CharReader charReader) {
        return !charReader.hasNext();
    }

    // (Ljava/lang/String;Ljava/lang/String)V
    private void parseParamTypes(CharReader charReader) {
        int start = charReader.getNext();
        if ('(' != start) {
            throw new RuntimeException("bad method decriptor: " + raw);
        }
        while (charReader.hasNext()) {
            char c = charReader.getNext();
            if (')' == c) {
                break;
            } else {
                charReader.back();
            }
            this.paramTypes.add(parseType(charReader));
        }
    }

    private String parseType(CharReader charReader) {
        char c = charReader.getNext();
        switch (c) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z':
            case 'V':
                return c + "";
            case 'L':
            case '[':
                StringBuilder sb = new StringBuilder(c);
                while (charReader.hasNext()) {
                    char c1 = charReader.getNext();
                    // 所有的参数类型都处理完了
                    if (c1 == ')') {
                        charReader.back();
                        break;
                    }
                    // object type 以 ; 结尾
                    else if (c1 == ';') {
                        break;
                    }
                    sb.append(c1);
                }
                return sb.toString();
            default:
                throw new RuntimeException("bad method decriptor: " + raw);
        }
    }

    private void parseReturnType(CharReader charReader) {
        this.returnType = this.parseType(charReader);
    }


    public int getParamCount() {
        return paramTypes.size();
    }

    private class CharReader {
        private int index = 0;

        boolean hasNext() {
            return index < raw.length();
        }

        char getNext() {
            char c = raw.charAt(index);
            index++;
            return c;
        }

        void back() {
            index--;
        }
    }
}
