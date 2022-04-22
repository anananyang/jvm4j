package classpath;

import java.io.IOException;

public interface Entry {

    /**
     * 读取 class 文件
     *
     * @param className
     * @return
     */
    byte[] readClass(String className) throws IOException;
}
