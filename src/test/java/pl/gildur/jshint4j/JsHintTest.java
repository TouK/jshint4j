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
        Assert.assertEquals("W033", error.getCode());
        Assert.assertEquals(1, error.getLine());
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
        Assert.assertEquals("W117", error.getCode());
        Assert.assertEquals(1, error.getLine());
    }
}
