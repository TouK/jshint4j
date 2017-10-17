package pl.touk.jshint4j;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Java wrapper for JSHint (http://www.jshint.com/).
 *
 * @author Piotr Wolny
 */
public class JsHint {

    private static final String JSHINT_JS = "jshint.js";

    /**
     * Runs JSHint on given JavaScript source code.
     *
     * @param source JavaScript source code
     * @param options JSHint options
     * @return JSHint errors
     */
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
            if (jsError != null) {
                errors.add(toError(jsError));
            }
        }
        return errors;
    }

    private Error toError(Map<String, ?> jsError) {
        return new Error(
                Context.toString(jsError.get("id")),
                Context.toString(jsError.get("raw")),
                Context.toString(jsError.get("code")),
                Context.toString(jsError.get("evidence")),
                (int) Context.toNumber(jsError.get("line")),
                (int) Context.toNumber(jsError.get("character")),
                Context.toString(jsError.get("scope")),
                Context.toString(jsError.get("reason")));
    }

    private void evaluateJSHint(Context cx, Scriptable scope) {
        String jsHintName = JSHINT_JS;
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
