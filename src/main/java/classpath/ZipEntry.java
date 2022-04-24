package classpath;

import java.io.*;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipEntry implements Entry {

    private String path;

    ZipEntry(String path) {
        this.path = path;
    }

    public byte[] readClass(String className) throws IOException {
        File file = new File(path);
        ZipFile zipFile = new ZipFile(file);
        java.util.zip.ZipEntry entry = zipFile.getEntry(className);
        if(entry == null) {
            return null;
        }
        InputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[1024];
            int size = 0;
            while((size = is.read(buf)) != -1) {
                bos.write(buf, 0, size);
            }
            return bos.toByteArray();
        } finally {
            is.close();
            bos.close();
        }
    }
}
