package jNative.java.lang;

public class JStackTraceElement {
    private String fileName;
    private String className;
    private String methodNmae;
    private int lineNum;

    public JStackTraceElement(String fileName, String className, String methodNmae, int lineNum) {
        this.fileName = fileName;
        this.className = className;
        this.methodNmae = methodNmae;
        this.lineNum = lineNum;
    }

    public String getFileName() {
        return fileName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodNmae() {
        return methodNmae;
    }

    public int getLineNum() {
        return lineNum;
    }

    public String toString() {
        return className + "." + methodNmae + "(" + fileName + ":" + lineNum + ")";
    }
}
