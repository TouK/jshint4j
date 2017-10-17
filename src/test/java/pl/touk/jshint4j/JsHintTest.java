package pl.touk.jshint4j;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * {@link JsHint} tests.
 *
 * @author Piotr Wolny
 */
public class JsHintTest {

    private final JsHint jsHint = new JsHint();

    @Test(expected=NullPointerException.class)
    public void shouldThrowNullPointerExceptionForNullSource() {
        // given
        String source = null;

        // when
        jsHint.lint(source, null);
    }

    @Test
    public void shouldReturnNoErrorsForEmptySource() {
        // given
        String source = "";

        // when
        List<Error> errors = jsHint.lint(source, null);

        // then
        assertNotNull(errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void shouldReturnNoErrorsForSimpleSource() {
        // given
        String source = "function test() {}";

        // when
        List<Error> errors = jsHint.lint(source, null);

        // then
        assertNotNull(errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void shouldReturnErrorForMissingSemicolon() {
        // given
        String source = "function test() { return 5 }";

        // when
        List<Error> errors = jsHint.lint(source, null);

        // then
        assertNotNull(errors);
        assertEquals(1, errors.size());
        Error error = errors.get(0);
        assertEquals("(error)", error.getId());
        assertEquals("Missing semicolon.", error.getRaw());
        assertEquals("W033", error.getCode());
        assertEquals(source, error.getEvidence());
        assertEquals(1, error.getLine());
        assertEquals(27, error.getCharacter());
        assertEquals("(main)", error.getScope());
        assertEquals("Missing semicolon.", error.getReason());
    }

    @Test
    public void shouldReturnErrorForUndefinedVariable() {
        // given
        String source = "function test() { return x; }";
        String options = "{ undef: true }";

        // when
        List<Error> errors = jsHint.lint(source, options);

        // then
        assertNotNull(errors);
        assertEquals(1, errors.size());
        Error error = errors.get(0);
        assertEquals("(error)", error.getId());
        assertEquals("'{a}' is not defined.", error.getRaw());
        assertEquals("W117", error.getCode());
        assertEquals(source, error.getEvidence());
        assertEquals(1, error.getLine());
        assertEquals(26, error.getCharacter());
        assertEquals("(main)", error.getScope());
        assertEquals("'x' is not defined.", error.getReason());
    }

    @Test
    public void shouldReturnLotsOfErrorsForInvalidCode() {
        // given
        String source = "function test( { return x;";

        // when
        List<Error> errors = jsHint.lint(source, null);

        // then
        assertNotNull(errors);
        assertEquals(7, errors.size());
    }
}
