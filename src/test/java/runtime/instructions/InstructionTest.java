package runtime.instructions;

import classFile.ClassFile;
import classFile.MemberInfo;
import classFile.reader.ClassReader;
import classpath.ClassPath;
import org.testng.annotations.Test;
import runtime.Interpreter;

import java.io.IOException;
import java.lang.reflect.Member;

public class InstructionTest {

    @Test
    public void testInstruction() throws IOException {
        String xjre = "/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre";
        String classpath = "/Users/t/myProject/jvm4j/src/main/resources";
        String className = "GaussTest";

        ClassPath classPath = new ClassPath(xjre, classpath);
        byte[] bytes = classPath.readClass(className);
        if(bytes == null) {
            throw new RuntimeException("can not found class [" + className + "]");
        }
        ClassReader classReader = new ClassReader(bytes);
        ClassFile classFile = classReader.read();
        MemberInfo mainMethod = getMainMethod(classFile);
        if (mainMethod == null) {
            throw new RuntimeException("main method not found");
        }
        Interpreter.interpret(mainMethod);
    }

    private MemberInfo getMainMethod(ClassFile classFile) {
        MemberInfo[] methods = classFile.getMethods();
        if (methods == null) {
            return null;
        }
        for (MemberInfo method : methods) {
            if("main".equals(method.getName()) && "([Ljava/lang/String;)V".equals(method.getDescriptor())) {
                return method;
            }
        }
        return null;
    }
}
