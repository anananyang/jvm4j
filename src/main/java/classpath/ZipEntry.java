package classpath;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class ZipEntry implements Entry {

    private String path;

    ZipEntry(String path) {
        this.path = path;
    }

    public byte[] readClass(String className) throws IOException {
        ZipInputStream zis = new ZipInputStream( new FileInputStream(path));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        java.util.zip.ZipEntry zipEntry = null;
        try {
            while((zipEntry = zis.getNextEntry()) != null) {
                if(zipEntry.isDirectory()) {
                    continue;
                }
                String name = zipEntry.getName();
                if(!name.endsWith(className)) {
                    continue;
                }
                byte[] buf = new byte[1024];
                int size = 0;
                while((size = zis.read(buf)) != -1) {
                    bos.write(buf, 0, size);
                }
                return bos.toByteArray();
            }

            return null;

        } finally {
            zis.close();
            bos.close();
        }



    }
}
