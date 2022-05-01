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
        CommandLine cmd = parseCommandLine(args);
        execute(cmd);
    }

    public static CommandLine parseCommandLine(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cmd;
    }


    public static void execute(CommandLine cmd) {
        if (cmd.hasOption("version")) {
            printVersion();
        } else if (cmd.hasOption("help") || cmd.hasOption("?") || !cmd.hasOption("cp")) {
            printUsage();
        } else {
            new JVM(cmd).start();
        }
    }

    private static void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("jvm4j", options);
    }

    private static void printVersion() {
        System.out.println("jvm4j version 0.1");
    }

}
