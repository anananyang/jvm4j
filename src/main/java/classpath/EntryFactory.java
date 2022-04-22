package classpath;

import java.io.File;

public class EntryFactory {

    public static Entry newEntry(String path) {
        if (path.endsWith("*")) {
            return new WildcardEntry(path);
        }
        if (path.endsWith(".jar") || path.endsWith(".JAR") || path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new ZipEntry(path);
        }
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }
        return new DirEntry(path);
    }
}
