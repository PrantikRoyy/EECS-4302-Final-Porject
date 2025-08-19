package app;

import antlr.ExprLexer;
import antlr.ExprParser;
import model.StaticTypeChecker;
import model.MyErrorListener;
import model.SemanticErrorReporter;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.nio.file.*;
import java.nio.file.Paths;
import java.util.*;

public class ExpressionApp {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java ExpressionApp <sourcefile1> [<sourcefile2> ...] [--html] [--console]");
            return;
        }

        // Parse args for flags and source files
        List<String> sourceFiles = new ArrayList<>();
        boolean generateHtml = false;
        boolean printConsole = false;

        for (String arg : args) {
            if (arg.equals("--html")) {
                generateHtml = true;
            } else if (arg.equals("--console")) {
                printConsole = true;
            } else {
                sourceFiles.add(arg);
            }
        }
        
        if (!generateHtml && !printConsole) {
            System.out.println("No output flags provided. Use --console and/or --html to get results from the compiler.");
            System.out.println("Example usage:");
            System.out.println("  java -jar my-compiler.jar src/tests/full_test_suite1.txt --console");
            System.out.println("  java -jar my-compiler.jar src/tests/full_test_suite1.txt --html");
            System.out.println("  java -jar my-compiler.jar src/tests/full_test_suite1.txt src/tests/full_test_suite2.txt --console --html");
        }


        if (sourceFiles.isEmpty()) {
            System.out.println("Error: No source files specified.");
            System.out.println("Usage: java ExpressionApp <sourcefile1> [<sourcefile2> ...] [--html] [--console]");
            return;
        }

        // Read all files and concatenate content
        StringBuilder combinedInput = new StringBuilder();
        for (String file : sourceFiles) {
            String content = new String(Files.readAllBytes(Paths.get(file)));
            combinedInput.append(content + "\n");
        }
        String input = combinedInput.toString();

        // === Setup ANTLR ===
        CharStream cs = CharStreams.fromString(input);
        ExprLexer lexer = new ExprLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new MyErrorListener());

        // === Parse ===
        ParseTree tree = parser.prog();  // entry point in your .g4

        if (MyErrorListener.hasError) {
            System.out.println("Syntax errors detected. Aborting.");
            return;
        }

        // === Type Checking ===
        SemanticErrorReporter semanticErrors = new SemanticErrorReporter();
        semanticErrors.setSourceCode(input);
        StaticTypeChecker visitor = new StaticTypeChecker(semanticErrors);
        visitor.visit(tree);

        // === Output ===
        if (!semanticErrors.hasErrors()) {
            if (printConsole) {
                System.out.println("No semantic errors! Type check passed.");
            }
            if (generateHtml) {
            	semanticErrors.outputHtmlReport("OUTPUT_" + Paths.get(sourceFiles.get(0)).getFileName().toString().replace(".txt", "") + ".html");
            }
        } else {
            if (printConsole) {
                System.out.println("Semantic errors found:");
                semanticErrors.printToConsole();
            }
            if (generateHtml) {
//                semanticErrors.outputHtmlReport("index.html");
            	semanticErrors.outputHtmlReport("OUTPUT_" + Paths.get(sourceFiles.get(0)).getFileName().toString().replace(".txt", "") + ".html");
            }
        }
    }
}
