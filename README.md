jshint4j
========

Java wrapper for JSHint (http://www.jshint.com/).

Usage
-----

```java
JsHint jsHint = new JsHint();
String source = "function test() { return x; }";
String options = "{ undef: true }";
List<Error> errors = jsHint.lint(source, options);
for (Error error : errors) {
    System.out.printf("JSHint error on line %d, character %d: %s", error.getLine(), error.getCharacter(),
            error.getReason());
}
```
