package pl.gildur.jshint4j;

/**
 * JSHint error.
 * 
 * @author Piotr Wolny
 */
public class Error {

    private String id;
    private String raw;
    private String code;
    private String evidence;
    private int line;
    private int character;
    private String scope;
    private String reason;
    
    public Error(String id, String raw, String code, String evidence, int line, int character, String scope,
            String reason) {
        this.id = id;
        this.raw = raw;
        this.code = code;
        this.evidence = evidence;
        this.line = line;
        this.character = character;
        this.scope = scope;
        this.reason = reason;
    }
    
    public String getId() {
        return id;
    }
    public String getRaw() {
        return raw;
    }
    public String getCode() {
        return code;
    }
    public String getEvidence() {
        return evidence;
    }
    public int getLine() {
        return line;
    }
    public int getCharacter() {
        return character;
    }
    public String getScope() {
        return scope;
    }
    public String getReason() {
        return reason;
    }
    
    @Override
    public String toString() {
        return "Error [id=" + id + ", raw=" + raw + ", code=" + code + ", evidence=" + evidence + ", line=" + line
                + ", character=" + character + ", scope=" + scope + ", reason=" + reason + "]";
    }
}
