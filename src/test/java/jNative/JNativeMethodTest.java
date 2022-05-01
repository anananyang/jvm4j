package jNative;

import classpath.ClassPath;
import org.testng.annotations.Test;
import runtime.Interpreter;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JClassLoader;
import runtime.rtda.share.heap.JMethod;

import java.io.IOException;

public class JNativeMethodTest {

    @Test
    public void testReflet() throws IOException {
        String xjre = "/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre";
        String classpath = "/Users/t/myProject/jvm4j/src/main/resources";
        ClassPath classPath = new ClassPath(xjre, classpath);
        JClassLoader loader = new JClassLoader(classPath);
//        String[] classNames = {"GetClassTest", "StringTest", "ObjectTest"};
        // 拆装箱未完成
//        String[] classNames = {"BoxTest"};
        String[] classNames = {"ParseIntTest"};

        for(String className : classNames) {
            System.out.println("--------- " + className + "-------------");
            JClass jClass = loader.loadClass(className);
            JMethod mainMethod = jClass.getMainMethod();
            if (mainMethod == null) {
                throw new RuntimeException("main method not found");
            }
            Interpreter.interpret(mainMethod, null, false);
        }
    }
}
