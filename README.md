jshint4j
========

Java wrapper for JSHint (http://www.jshint.com/).

[![Build Status](https://travis-ci.org/TouK/jshint4j.svg?branch=master)](https://travis-ci.org/TouK/jshint4j)

Usage
-----

Add dependency to your project:
```xml
<dependency>
    <groupId>pl.touk</groupId>
    <artifactId>jshint4j</artifactId>
    <version>2.9.5</version>
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

Credits
-------

Original version of JsHint4J was developed by Piotr Wolny (https://github.com/gildur/jshint4j).

License
-------

<a href="https://raw.githubusercontent.com/TouK/jshint4j/master/LICENSE">MIT</a>
