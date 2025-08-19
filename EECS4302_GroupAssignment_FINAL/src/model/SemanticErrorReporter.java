package model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SemanticErrorReporter {

    private final List<SemanticError> errors = new ArrayList<>();
    private final Map<String, ScopeStack.ScopeFrame> classScopes = new HashMap<>();
    private final Map<String, String> inheritanceMap = new HashMap<>();
    public final Map<String, ArrayList<String>> classNameToFuncs = new HashMap<>();

    // New field to hold the full source code as a string
    private String sourceCode = null;

    // Setter to assign source code from outside
    public void setSourceCode(String code) {
        this.sourceCode = code;
    }

    public void addClassScope(String className, ScopeStack.ScopeFrame frame) {
        classScopes.put(className, ScopeStack.cloneFrame(frame)); // clone to prevent mutation
    }

    public void setInheritanceMap(Map<String, String> classToParentMap) {
        inheritanceMap.clear();
        inheritanceMap.putAll(classToParentMap);
    }

    public void finalizeScopesRemovingInheritedMembers() {
        List<String> sortedClasses = getSortedClassList();
        Collections.reverse(sortedClasses); // Go from leaf to root

        for (String className : sortedClasses) {
            String parent = inheritanceMap.get(className);
            if (parent == null) continue;

            ScopeStack.ScopeFrame currentFrame = classScopes.get(className);
            ScopeStack.ScopeFrame parentFrame = classScopes.get(parent);
            if (currentFrame == null || parentFrame == null) continue;

            for (String inheritedVar : parentFrame.getVariableNames()) {
                currentFrame.removeVariable(inheritedVar);
            }

            for (String inheritedFunc : parentFrame.getFunctionNames()) {
                currentFrame.removeFunction(inheritedFunc);
            }
        }
    }

    public void add(String message, int line, int charPosition, String context, String className) {
        errors.add(new SemanticError(message, line, charPosition, context, className));
    }

    public List<SemanticError> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private List<String> getSortedClassList() {
        Map<String, List<String>> parentToChildren = new HashMap<>();
        Set<String> allClasses = new HashSet<>(classScopes.keySet());

        for (String child : allClasses) {
            String parent = inheritanceMap.get(child);
            if (parent != null && allClasses.contains(parent)) {
                parentToChildren.computeIfAbsent(parent, k -> new ArrayList<>()).add(child);
            } else {
                parentToChildren.computeIfAbsent(null, k -> new ArrayList<>()).add(child);
            }
        }

        List<String> sorted = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (String root : parentToChildren.getOrDefault(null, Collections.emptyList())) {
            dfsSort(root, parentToChildren, visited, sorted);
        }

        return sorted;
    }

    private void dfsSort(String current, Map<String, List<String>> graph, Set<String> visited, List<String> sorted) {
        if (visited.contains(current)) return;
        visited.add(current);
        sorted.add(current);
        for (String child : graph.getOrDefault(current, Collections.emptyList())) {
            dfsSort(child, graph, visited, sorted);
        }
    }

    public void outputHtmlReport(String filename) {
        String html = buildHtml();
        try {
            Files.write(Paths.get(filename), html.getBytes(StandardCharsets.UTF_8));
            System.out.println("\nError report file location: " + Paths.get(filename));
        } catch (IOException e) {
            System.err.println("Could not write semantic error report: " + e.getMessage());
        }
    }

    /**
     * Internal helper to build the HTML as a string.
     */
    public String buildHtml() {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html lang=\"en\">");
        html.append("<head>");
        html.append("<meta charset=\"UTF-8\">");
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        html.append("<title>Semantic Report</title>");
        html.append("<style>");
        html.append("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background: #f9f9f9; }");
        html.append(".grid-container { display: grid; grid-template-columns: 5fr 4fr; gap: 20px; padding: 20px; box-sizing: border-box; }");

        html.append(".error-container { background: #fff0f0; border: 1px solid #e74c3c; border-radius: 10px; padding: 15px; overflow-y: auto; }");
        html.append(".error { border-left: 5px solid #e74c3c; padding: 10px; margin-bottom: 10px; background: #fff; }");
        html.append(".error strong { color: #c0392b; }");
        html.append(".error em { color: #555; font-size: 0.9em; }");

        html.append(".class-column { display: flex; flex-direction: column; gap: 20px; }");
        html.append(".class-card { background: #ffffff; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.07); padding: 20px; }");
        html.append(".class-header { font-weight: 700; font-size: 1.25rem; margin-bottom: 15px; color: #2c3e50; }");

        html.append(".class-info { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }");
        html.append(".info-block { font-size: 0.9rem; }");
        html.append(".info-block h3 { margin-top: 0; border-bottom: 1px solid #ddd; color: #2980b9; font-size: 1rem; }");
        html.append(".info-block ul { list-style: none; padding: 0; margin: 0; }");
        html.append(".info-block li { padding: 4px 0; border-bottom: 1px solid #eee; }");
        html.append(".info-block li:last-child { border-bottom: none; }");
        html.append(".info-block .signature { color: #34495e; font-family: monospace; }");

        // New CSS for source code display
        html.append(".code-section { margin: 30px; background: #ffffff; border-radius: 12px; padding: 20px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }");
        html.append(".code-block { font-family: monospace; background: #f8f8f8; padding: 10px; overflow-x: auto; }");
        html.append(".code-line { white-space: pre; }");
        html.append(".line-number { display: inline-block; width: 40px; color: #888; }");
        html.append(".error-line { background-color: #ffe6e6; border-left: 4px solid #e74c3c; }");

        html.append("</style>");
        html.append("</head>");
        html.append("<body>");

        html.append("<div class=\"grid-container\">");

        // Errors panel
        html.append("<div class=\"error-container\">");
        html.append("<h2>Semantic Errors</h2>");
        if (errors.isEmpty()) {
            html.append("<p>No semantic errors found.</p>");
        } else {
            for (SemanticError error : errors) {
                html.append("<div class=\"error\">")
                    .append("<strong>").append(error.getContext()).append("</strong>: ")
                    .append(error.getMessage())
                    .append("<br><em>")
                    .append("Class: ").append(error.getClassName())
                    .append(", Line: ").append(error.getLine())
                    .append(", Char: ").append(error.getCharPosition())
                    .append("</em>")
                    .append("</div>");
            }
        }
        html.append("</div>");

        // Class info panel
        html.append("<div class=\"class-column\">");
        for (String className : getSortedClassList()) {
            ScopeStack.ScopeFrame frame = classScopes.get(className);
            if (frame == null) continue;

            String parent = inheritanceMap.get(className);
            String header = "Class: " + className;
            if (parent != null && !parent.isEmpty()) {
                header += " (inherits " + parent + ")";
            }

            html.append("<div class=\"class-card\">");
            html.append("<div class=\"class-header\">").append(header).append("</div>");

            html.append("<div class=\"class-info\">");

            // Variables column
            html.append("<div class=\"info-block\">");
            html.append("<h3>Variables</h3>");
            if (frame.variables.isEmpty()) {
                html.append("<p><em>None</em></p>");
            } else {
                html.append("<ul>");
                for (Map.Entry<String, String> var : frame.variables.entrySet()) {
                    html.append("<li><span class=\"signature\">")
                        .append(var.getValue()).append(" ").append(var.getKey())
                        .append("</span></li>");
                }
                html.append("</ul>");
            }
            html.append("</div>");

            // Functions column
            html.append("<div class=\"info-block\">");
            html.append("<h3>Functions</h3>");
            if (frame.functions.isEmpty()) {
                html.append("<p><em>None</em></p>");
            } else {
                html.append("<ul>");
                for (Map.Entry<String, StaticTypeChecker.FunctionInfo> fn : frame.functions.entrySet()) {
                    StaticTypeChecker.FunctionInfo info = fn.getValue();
                    html.append("<li><span class=\"signature\">")
                        .append(info.returnType).append(" ").append(fn.getKey()).append("(");
                    for (int i = 0; i < info.paramTypes.size(); i++) {
                        html.append(info.paramTypes.get(i));
                        if (i < info.paramTypes.size() - 1) html.append(", ");
                    }
                    html.append(")</span></li>");
                }
                html.append("</ul>");
            }
            html.append("</div>");

            html.append("</div>"); // .class-info
            html.append("</div>"); // .class-card
        }
        html.append("</div>"); // .class-column

        html.append("</div>"); // .grid-container

        // Append source code section if sourceCode set
        if (sourceCode != null) {
            html.append(generateCodeSection());
        }

        html.append("</body></html>");

        return html.toString();
    }

    // Generate the source code display with error line highlighting
    private String generateCodeSection() {
        StringBuilder sb = new StringBuilder();
        Set<Integer> errorLines = new HashSet<>();
        for (SemanticError e : errors) {
            errorLines.add(e.getLine());
        }

        sb.append("<div class=\"code-section\">");
        sb.append("<h2>Source Code</h2>");
        sb.append("<pre class=\"code-block\">");

        String[] lines = sourceCode.split("\n");
        for (int i = 0; i < lines.length; i++) {
            int lineNum = i + 1;
            String escaped = escapeHtml(lines[i]);

            if (errorLines.contains(lineNum)) {
                sb.append("<div class=\"code-line error-line\">");
            } else {
                sb.append("<div class=\"code-line\">");
            }

            sb.append("<span class=\"line-number\">").append(lineNum).append("</span> ");
            sb.append(escaped);
            sb.append("</div>");
        }

        sb.append("</pre>");
        sb.append("</div>");
        return sb.toString();
    }

    // Escape HTML special chars in source code lines
    private String escapeHtml(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    public void printToConsole() {
        for (SemanticError error : errors) {
            System.out.println(error);
        }
    }
}
