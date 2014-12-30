jshint4j
========

Java wrapper for JSHint (http://www.jshint.com/).

[![Build Status](https://travis-ci.org/gildur/jshint4j.svg?branch=master)](https://travis-ci.org/gildur/jshint4j)

Usage
-----

Add dependency to your project:
```xml
<dependency>
    <groupId>pl.gildur</groupId>
    <artifactId>jshint4j</artifactId>
    <version>1.0.1</version>
</dependency>
```

Use it in your code:
```java
JsHint jsHint = new JsHint();
String source = "function test() { return x; }";
String options = "{ undef: true }";
List<Error> errors = jsHint.lint(source, options);
for (Error error : errors) {
    System.out.printf("JSHint error on line %d, character %d: %s",
            error.getLine(),
            error.getCharacter(),
            error.getReason());
}
```

License
-------

<a href="https://raw.githubusercontent.com/gildur/jshint4j/master/LICENSE">MIT</a>
