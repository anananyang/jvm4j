package runtime.rtda.share.heap;

import classFile.ClassFile;
import classFile.MemberInfo;
import classFile.reader.ClassReader;
import classpath.ClassPath;
import org.testng.annotations.Test;
import runtime.Interpreter;

import java.io.IOException;

public class JClassTest {

    @Test
    public void testInstruction() throws IOException {
        String xjre = "/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre";
        String classpath = "/Users/t/myProject/jvm4j/src/main/resources";
//        String className = "GaussLongTest";
//        String className = "MyObject";
//        String className = "InvokerDemo";
        String className = "FibonacciTest";

        ClassPath classPath = new ClassPath(xjre, classpath);
        JClassLoader loader = new JClassLoader(classPath);
        JClass jClass = loader.loadClass(className);
        JMethod mainMethod = jClass.getMainMethod();

        if (mainMethod == null) {
            throw new RuntimeException("main method not found");
        }
        Interpreter.interpret(mainMethod, false);
    }
}
