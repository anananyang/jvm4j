package runtime.rtda.share.heap;

import classpath.ClassPath;
import org.testng.annotations.Test;
import runtime.Interpreter;

import java.io.IOException;

public class ArrayTest {

    @Test
    public void testArray() throws IOException {
        String xjre = "/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre";
        String classpath = "/Users/t/myProject/jvm4j/src/main/resources";
        String[] classNames = {"BubbleSortTest"};

        ClassPath classPath = new ClassPath(xjre, classpath);
        JClassLoader loader = new JClassLoader(classPath);
        for(String className : classNames) {

            System.out.println("--------- " + className + "-------------");
            JClass jClass = loader.loadClass(className);
            JMethod mainMethod = jClass.getMainMethod();
            if (mainMethod == null) {
                throw new RuntimeException("main method not found");
            }
            Interpreter.interpret(mainMethod, false);
        }
    }
}
