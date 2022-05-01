package classFile;

import classFile.reader.ClassReader;
import classpath.ClassPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Arrays;

public class ClassFileTest {

    @Test
    public void testClassFile() throws IOException{
        String xjre = "/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre";
        String classpath = "/Users/t/myProject/jvm4j/src/main/resources";
        String className = "java.lang.String";


        ClassPath classPath = new ClassPath(xjre, classpath);
        byte[] bytes = classPath.readClass(className);
        if(bytes == null) {
            throw new RuntimeException("can not found class [" + className + "]");
        }
        ClassReader classReader = new ClassReader(bytes);
        ClassFile classFile = classReader.read();

        printClassInfo(classFile);
    }

    public void printClassInfo(ClassFile classFile) {
        int majroVersion = classFile.getMajroVersion();
        int minorVersion = classFile.getMinorVersion();
        ConstantPool constantPool = classFile.getConstantPool();
        int accessFlag = classFile.getAccessFlag();
        String className = classFile.getClassName();
        String superName = classFile.getSuperName();
        String[] interfaceNames = classFile.getInterfaceNames();
        MemberInfo[] fileds = classFile.getFileds();
        MemberInfo[] methods = classFile.getMethods();

        System.out.println(String.format("version: %d.%d", majroVersion, minorVersion));
        System.out.println(String.format("constants count: %d", constantPool.getconstantoCount()));
        System.out.println(String.format("access flags: %s", Integer.toHexString(accessFlag)));
        System.out.println(String.format("this class: %s", className));
        System.out.println(String.format("super class: %s", superName));
        System.out.println(String.format("interfaces: %s", Arrays.toString(interfaceNames)));
        System.out.println(String.format("field count: %d", fileds == null ? 0 : fileds.length));
        if(fileds != null) {
            for(int i = 0; i < fileds.length; i++) {
                System.out.println(String.format("   %s", fileds[i].getName()));
            }
        }
        System.out.println(String.format("method count: %d", methods == null ? 0 : methods.length));
        if(methods != null) {
            for(int i = 0; i < methods.length; i++) {
                System.out.println(String.format("   %s", methods[i].getName()));
            }
        }
    }
}
