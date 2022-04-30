package runtime.rtda.share.heap;

import classpath.ClassPath;
import org.testng.annotations.Test;
import runtime.Interpreter;

import java.io.IOException;

public class StringTest {

    @Test
    public void testString() throws IOException {
        String xjre = "/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre";
        String classpath = "/Users/t/myProject/jvm4j/src/main/resources";
        ClassPath classPath = new ClassPath(xjre, classpath);
        JClassLoader loader = new JClassLoader(classPath);
        String className = "HelloWorld";
        String[] args = {"foo", "bar"};
        System.out.println("--------- " + className + "-------------");
        JClass jClass = loader.loadClass(className);
        JMethod mainMethod = jClass.getMainMethod();
        if (mainMethod == null) {
            throw new RuntimeException("main method not found");
        }
        Interpreter.interpret(mainMethod, args,true);

    }
}
