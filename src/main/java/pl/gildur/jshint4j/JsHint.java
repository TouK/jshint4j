package pl.gildur.jshint4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

/**
 * Java wrapper for JSHint (http://www.jshint.com/).
 * 
 * @author Piotr Wolny
 */
public class JsHint {
    
    public List<Error> lint(String source, String options) {
        if (source == null) {
            throw new NullPointerException("Source must not be null.");
        }
        
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();
            evaluateJSHint(cx, scope);
            return lint(cx, scope, source, options);
        } finally {
            Context.exit();
        }
    }

    private List<Error> lint(Context cx, Scriptable scope, String source, String options) {
        List<Error> errors = new ArrayList<Error>();
        Function jsHintFunction = (Function) scope.get("JSHINT", scope);
        Object jsHintOptions = options == null ? null : cx.evaluateString(scope, "options = " + options, null, 1, null);
        jsHintFunction.call(cx, scope, scope, new Object[] { source, jsHintOptions });
        @SuppressWarnings("unchecked")
        List<Map<String, ?>> jsErrors = (List<Map<String, ?>>) jsHintFunction.get("errors", jsHintFunction);
        for (Map<String, ?> jsError : jsErrors) {
            errors.add(toError(jsError));
        }
        return errors;
    }

    private Error toError(Map<String, ?> jsError) {
        Error error = new Error();
        error.setId(Context.toString(jsError.get("id")));
        error.setRaw(Context.toString(jsError.get("raw")));
        error.setCode(Context.toString(jsError.get("code")));
        error.setEvidence(Context.toString(jsError.get("evidence")));
        error.setLine((int) Context.toNumber(jsError.get("line")));
        error.setCharacter((int) Context.toNumber(jsError.get("character")));
        error.setScope(Context.toString(jsError.get("scope")));
        error.setReason(Context.toString(jsError.get("reason")));
        return error;
    }

    private void evaluateJSHint(Context cx, Scriptable scope) {
        String jsHintName = "jshint.js";
        InputStreamReader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(jsHintName));
        try {
            cx.evaluateReader(scope, reader, jsHintName, 1, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
