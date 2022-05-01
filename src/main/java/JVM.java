import classpath.ClassPath;
import org.apache.commons.cli.CommandLine;
import runtime.Interpreter;
import runtime.rtda.priv.JThread;
import runtime.rtda.share.heap.JClass;
import runtime.rtda.share.heap.JClassLoader;
import runtime.rtda.share.heap.JMethod;

public class JVM {
    private CommandLine cmd;
    private JThread mainThread;
    private JClassLoader loader;

    public JVM(CommandLine cmd) {
        ClassPath classPath = new ClassPath(cmd.getOptionValue("xjre"),
                cmd.getOptionValue("cp"));
        this.loader = new JClassLoader(classPath);
        this.mainThread = new JThread();
        this.cmd = cmd;
    }

    public void start() {
//        initVM();
        execMain();
    }

    private void initVM() {
        JClass vmClass = loader.loadClass("sun.misc.VM");
        // 执行类的初始化方法
        vmClass.initClass(mainThread);
        // 执行初始化方法
        Interpreter.loop(mainThread, true);
    }

    public void execMain() {
        String className = getClassName();
        if (className == null) {
            throw new RuntimeException("can not found classname");
        }
        String[] args = getClassArgs();
        JClass jClass = loader.loadClass(className);
        JMethod mainMethod = jClass.getMainMethod();
        if (mainMethod == null) {
            throw new RuntimeException("main method not found");
        }
        Interpreter.interpret(mainMethod, args, false);
    }

    private String getClassName() {
        String[] commandArgs = cmd.getArgs();
        if (commandArgs == null || commandArgs.length == 0) {
            return null;
        }
        return commandArgs[0];
    }

    private String[] getClassArgs() {
        String[] commandArgs = cmd.getArgs();
        if (commandArgs.length == 1) {
            return null;
        }
        String[] args = new String[commandArgs.length - 1];
        for (int i = 1; i < commandArgs.length; i++) {
            args[i - 1] = commandArgs[i];
        }
        return args;
    }
}
