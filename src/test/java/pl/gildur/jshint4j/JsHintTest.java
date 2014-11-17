package pl.gildur.jshint4j;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertNotNull(errors);
        Assert.assertTrue(errors.isEmpty());
    }
    
    @Test
    public void shouldReturnNoErrorsForSimpleSource() {
        // given
        String source = "function test() {}";

        // when
        List<Error> errors = jsHint.lint(source, null);
        
        // then
        Assert.assertNotNull(errors);
        Assert.assertTrue(errors.isEmpty());
    }
    
    @Test
    public void shouldReturnErrorForMissingSemicolon() {
        // given
        String source = "function test() { return 5 }";

        // when
        List<Error> errors = jsHint.lint(source, null);
        
        // then
        Assert.assertNotNull(errors);
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals("(error)", error.getId());
        Assert.assertEquals("Missing semicolon.", error.getRaw());
        Assert.assertEquals("W033", error.getCode());
        Assert.assertEquals(source, error.getEvidence());
        Assert.assertEquals(1, error.getLine());
        Assert.assertEquals(27, error.getCharacter());
        Assert.assertEquals("(main)", error.getScope());
        Assert.assertEquals("Missing semicolon.", error.getReason());
    }
    
    @Test
    public void shouldReturnErrorForUndefinedVariable() {
        // given
        String source = "function test() { return x; }";
        String options = "{ undef: true }";

        // when
        List<Error> errors = jsHint.lint(source, options);
        
        // then
        Assert.assertNotNull(errors);
        Assert.assertEquals(1, errors.size());
        Error error = errors.get(0);
        Assert.assertEquals("(error)", error.getId());
        Assert.assertEquals("'{a}' is not defined.", error.getRaw());
        Assert.assertEquals("W117", error.getCode());
        Assert.assertEquals(source, error.getEvidence());
        Assert.assertEquals(1, error.getLine());
        Assert.assertEquals(26, error.getCharacter());
        Assert.assertEquals("(main)", error.getScope());
        Assert.assertEquals("'x' is not defined.", error.getReason());

    }
}
