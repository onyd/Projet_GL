package fr.ensimag.deca.decac;

import fr.ensimag.deca.CLIException;
import fr.ensimag.deca.CompilerOptions;

import java.io.File;
import java.util.UnknownFormatFlagsException;

public class TestFlagsDecac {
    public static void main(String[] args) {
        System.out.println("test flags -v");
        args = new String[]{"-v"};
        CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
            System.out.println(options);
        } catch(CLIException ignored) {
        }

        System.out.println("test flags -r 6");
        args = new String[]{"-r", "6"};
        CompilerOptions options3 = new CompilerOptions();
        try {
            options3.parseArgs(args);
            System.out.println(options3);
        } catch(CLIException ignored) {
        }

        System.out.println("test flags -p -n -r 6 -d -d -P");
        args = new String[]{"-p", "-n", "-r", "6", "-d", "-d", "-P"};
        CompilerOptions options4 = new CompilerOptions();
        try {
            options4.parseArgs(args);
            System.out.println(options4);
        } catch(CLIException ignored) {
        }

        File file = new File("./");
        System.out.println("test flags -p -n -r 6 -d -d -P src/test/deca/codegen/perf/provided/syracuse42.deca");
        args = new String[]{"-p", "-n", "-r", "6", "-d", "-d", "-P", "src/test/deca/codegen/perf/provided/syracuse42.deca"};
        CompilerOptions options5 = new CompilerOptions();
        try {
            options5.parseArgs(args);
            System.out.println(options5);
        } catch(CLIException ignored) {
        }

        System.out.println("test flags -P ");
        args = new String[]{"-d", "-d", "-d", "-d", "-P"};
        CompilerOptions options6 = new CompilerOptions();
        try {
            options6.parseArgs(args);
            System.out.println(options6);
        } catch(CLIException ignored) {
        }

        System.out.println("test flags -v et -p");
        args = new String[]{"-v", "-p"};
        CompilerOptions options2 = new CompilerOptions();
        try {
            options2.parseArgs(args);
        } catch(UnsupportedOperationException | CLIException e) {
            System.out.println("It doesn't work ...OK :" + e);
        }
    }
}
