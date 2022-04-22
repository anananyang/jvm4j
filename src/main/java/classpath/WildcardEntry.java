package classpath;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WildcardEntry implements Entry {

    private List<Entry> entries = new ArrayList<Entry>();

    WildcardEntry(String path) {
        // 去掉末尾的星号
        String baseDirPath = path.substring(0, path.length() - 1);
        File baseDir = new File(baseDirPath);
        if (!baseDir.exists()) {
            throw new RuntimeException("baseDir [" + baseDir + "] does not exist");
        }
        File[] files = baseDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            String fileName = file.getAbsolutePath();
            if (fileName.endsWith(".jar") || fileName.endsWith(".JAR")) {
                entries.add(EntryFactory.newEntry(fileName));
            }
        }
    }

    public byte[] readClass(String className) throws IOException {
        for (Entry entry : entries) {
            byte[] bytes = entry.readClass(className);
            if (bytes != null) {
                return bytes;
            }
        }
        return null;
    }
}
