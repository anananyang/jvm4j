package classpath;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompositeEntry implements Entry {

    private List<Entry> entries = new ArrayList<Entry>();

    CompositeEntry(String pathList) {
        String[] pathArr = pathList.split(File.pathSeparator);
        for (String path : pathArr) {
            entries.add(EntryFactory.newEntry(path));
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
