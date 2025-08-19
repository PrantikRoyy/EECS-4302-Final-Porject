package model;

public class SemanticError {
    private final String message;
    private final int line;
    private final int charPosition;
    private final String context;
    private final String className;

    public SemanticError(String message, int line, int charPosition, String context, String className) {
        this.message = message;
        this.line = line;
        this.charPosition = charPosition;
        this.context = context;
        this.className = className;
    }

    public String getMessage() {
        return message;
    }

    public int getLine() {
        return line;
    }

    public int getCharPosition() {
        return charPosition;
    }
    
    public String getContext() {
    	return this.context;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
    	return String.format("Error [%s] in class '%s' at line %d, char %d: %s",
    		    context, className, line, charPosition, message);
    }
}
