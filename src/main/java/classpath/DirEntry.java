package classpath;

import java.io.*;

public class DirEntry implements Entry {

    private String path;

    DirEntry(String path) {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("path is invalid");
        }
        this.path = dir.toString();
    }

    public byte[] readClass(String className) throws IOException {
        File classFile = new File(path, className);
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = new FileInputStream(classFile);
            bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int size = 0;
            while( (size = is.read(buf)) != -1) {
                bos.write(buf, 0, size);
            }
            return bos.toByteArray();

        }finally {

            if (is != null) {
                is.close();
            }
            if(bos != null) {
                bos.close();
            }
        }
    }
}
