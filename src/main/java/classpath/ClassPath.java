package classpath;

import java.io.File;
import java.io.IOException;

public class ClassPath {
    // 启动类路径
    private Entry bootClassPath;
    // 扩展类路径
    private Entry extClassPath;
    // 用户类路径
    private Entry userClassPath;

    /**
     * @param xjre jre 目录
     * @param cp   用户类目录
     */
    public ClassPath(String xjre, String cp) {
        String jreDir = getJreDir(xjre);
        this.bootClassPath = newbootClasspath(jreDir);
        this.extClassPath = newExtClasspath(jreDir);
        this.userClassPath = newUserClasspath(cp);
    }

    public byte[] readClass(String className) throws IOException {
        if (!className.endsWith(".class")) {
            className = className + ".class";
        }
        byte[] bytes;
        bytes = bootClassPath.readClass(className);
        if (bytes != null) {
            return bytes;
        }
        bytes = extClassPath.readClass(className);
        if (bytes != null) {
            return bytes;
        }
        bytes = userClassPath.readClass(className);
        if (bytes != null) {
            return bytes;
        }
        throw new RuntimeException("can not found class!");
    }

    private String getJreDir(String xjre) {
        // 先判断用户指定的 xjre
        if (isValidDirPath(xjre)) {
            return xjre;
        }

        // 判断当前目录下是否jre
        xjre = "jre";
        File file = new File(xjre);
        if (file.exists()) {
            return file.toString();
        }

        // java home
        String javaHome = System.getenv("JAVA_HOME");
        if (javaHome == null) {
            throw new RuntimeException("can not find jre");
        }
        return javaHome + File.separator + "jre";
    }

    private boolean isValidDirPath(String jreDir) {
        if (jreDir == null) {
            return false;
        }
        File file = new File(jreDir);
        return file.exists();
    }

    private Entry newbootClasspath(String jreDir) {
        String bootPath = jreDir + File.separator + "lib" + File.separator + "*";
        return EntryFactory.newEntry(bootPath);
    }

    private Entry newExtClasspath(String jreDir) {
        String bootPath = jreDir + File.separator +  "lib" + File.separator + "ext" + File.separator + "*";
        return EntryFactory.newEntry(bootPath);
    }

    private Entry newUserClasspath(String cp) {
        if (cp == null) {
            cp = "";  // 当前类路径
        }

        return EntryFactory.newEntry(cp);
    }
}
