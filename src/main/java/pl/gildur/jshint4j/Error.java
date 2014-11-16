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
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRaw() {
        return raw;
    }
    public void setRaw(String raw) {
        this.raw = raw;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getEvidence() {
        return evidence;
    }
    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
    public int getLine() {
        return line;
    }
    public void setLine(int line) {
        this.line = line;
    }
    public int getCharacter() {
        return character;
    }
    public void setCharacter(int character) {
        this.character = character;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    @Override
    public String toString() {
        return "Error [id=" + id + ", raw=" + raw + ", code=" + code + ", evidence=" + evidence + ", line=" + line
                + ", character=" + character + ", scope=" + scope + ", reason=" + reason + "]";
    }
}
