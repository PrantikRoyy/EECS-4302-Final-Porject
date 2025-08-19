package model;

import java.util.*;

import model.StaticTypeChecker.FunctionInfo;

public class ScopeStack {
    public static class ScopeFrame {
        public final Map<String, String> variables = new HashMap<>();
        public final Map<String, FunctionInfo> functions = new HashMap<>();

        public void merge(ScopeFrame other) {
            this.variables.putAll(other.variables);
            this.functions.putAll(other.functions);
        }
        
        // Get all declared variable names
        public Set<String> getVariableNames() {
            return new HashSet<>(variables.keySet());
        }

        // Get all declared function names
        public Set<String> getFunctionNames() {
            return new HashSet<>(functions.keySet());
        }

        // Remove a specific variable by name
        public void removeVariable(String name) {
            variables.remove(name);
        }

        // Remove a specific function by name
        public void removeFunction(String name) {
            functions.remove(name);
        }
        
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Variables:\n");
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }

            sb.append("Functions:\n");
            for (Map.Entry<String, FunctionInfo> entry : functions.entrySet()) {
                FunctionInfo info = entry.getValue();
                sb.append("  ").append(entry.getKey()).append("(");
                for (int i = 0; i < info.paramTypes.size(); i++) {
                    sb.append(info.paramTypes.get(i));
                    if (i < info.paramTypes.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("): ").append(info.returnType).append("\n");
            }

            return sb.toString();
        }
    }

    
    
    private final Deque<ScopeFrame> scopes = new ArrayDeque<>();

    public ScopeStack() {
        pushScope(); // global scope
    }

    public void pushScope() {
        scopes.push(new ScopeFrame());
    }

    public void popScope() {
        scopes.pop();
    }

    // === Original Variable Logic ===

    public void declare(String name, String type) {
        scopes.peek().variables.put(name, type);
    }

    public boolean isDeclaredInCurrentScope(String name) {
        return scopes.peek().variables.containsKey(name);
    }

    public String lookup(String name) {
        for (ScopeFrame frame : scopes) {
            if (frame.variables.containsKey(name)) {
                return frame.variables.get(name);
            }
        }
        return null;
    }

    public Map<String, String> getCurrent() {
        return scopes.peek().variables;
    }

    public void pushPrevoiusScope(Map<String, String> variables) {
        scopes.peek().variables.putAll(variables);
    }

    // === Added Function Support ===
    
    public void mergeIntoCurrent(ScopeFrame inherited) {
        scopes.peek().merge(inherited);
    }


    public void declareFunction(String name, FunctionInfo funcInfo) {
        scopes.peek().functions.put(name, funcInfo);
    }

    public boolean isFunctionDeclaredInCurrentScope(String name) {
        return scopes.peek().functions.containsKey(name);
    }

    public FunctionInfo lookupFunction(String name) {
        for (ScopeFrame frame : scopes) {
            if (frame.functions.containsKey(name)) {
                return frame.functions.get(name);
            }
        }
        return null;
    }

    public Map<String, FunctionInfo> getCurrentFunctions() {
        return scopes.peek().functions;
    }

    public void pushPreviousFunctionScope(Map<String, FunctionInfo> functions) {
        scopes.peek().functions.putAll(functions);
    }

    public void mergeScopeFrame(ScopeFrame inherited) {
        scopes.peek().merge(inherited);
    }

    public static ScopeFrame cloneFrame(ScopeFrame frame) {
        ScopeFrame clone = new ScopeFrame();
        clone.variables.putAll(frame.variables);
        clone.functions.putAll(frame.functions);
        return clone;
    }
    
    public ScopeFrame getCurrentFrame() {
        return scopes.peek();
    }
    
    
    

}
