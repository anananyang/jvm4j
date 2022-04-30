import classFile.ClassFile;
import classFile.reader.ClassReader;
import classpath.ClassPath;
import org.apache.commons.cli.*;

public class Main {

    private static Options options = new Options();

    static {
        options.addOption("help", false, "print help message");
        options.addOption("?", false, "print help message");
        options.addOption("version", false, "print version");
        options.addOption("cp", true, "classpath");
        options.addOption("xjre", true, "path to jre");
    }

    public static void main(String[] args) {
        Main main = new Main();
        CommandLine cmd = main.parseCommandLine(args);
        main.execute(cmd);
    }

    public CommandLine parseCommandLine(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cmd;
    }


    public void execute(CommandLine cmd) {
        if (cmd.hasOption("version")) {
            printVersion();
        } else if (cmd.hasOption("help") || cmd.hasOption("?") || !cmd.hasOption("cp")) {
            printUsage();
        } else {
            startJvm(cmd);
        }
    }

    private void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("jvm4j", options);
    }

    private void printVersion() {
        System.out.println("jvm4j version 0.1");
    }

    private void startJvm(CommandLine cmd) {
        ClassFile classFile = loadClass(cmd);
    }

    public ClassFile loadClass(CommandLine cmd) {
        String xjre = cmd.getOptionValue("xjre");
        String classpath = cmd.getOptionValue("cp");
        String[] commandArgs = cmd.getArgs();
        if (commandArgs == null || commandArgs.length == 0) {
            throw new RuntimeException("can not found classname and args");
        }
        String className = commandArgs[0];
        try {
            ClassPath classPath = new ClassPath(xjre, classpath);
            byte[] bytes = classPath.readClass(className);
            ClassReader classReader = new ClassReader(bytes);
            ClassFile classFile = classReader.read();
            return classFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getClassArgs(String[] commandArgs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < commandArgs.length; i++) {
            sb.append(commandArgs[i]);
            if (i != commandArgs.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}