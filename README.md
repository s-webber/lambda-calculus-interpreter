# Lambda (&#955;) calculus interpreter

[![Build Status](https://travis-ci.org/s-webber/lambda-calculus-interpreter.png?branch=master)](https://travis-ci.org/s-webber/lambda-calculus-interpreter)

## About

This project provides a Java 7 implementation of a read-evaluate-print loop for the evaluation of lambda calculus expressions.

## Building

The project can be built using Maven and Java 7 by executing the following from the command line: `mvn package`.

## Running

The console application can be launched using the following command: 

`java -jar target/lambda-calculus-interpreter-1.0-SNAPSHOT.jar`

The application optionally takes one of three values as a command line argument: `NORMAL`, `APPLICATIVE` or `LAZY`.

The application defaults to `NORMAL` if no command line arguments are specified.

(Use a `\` character to represent a &#955;.)

### Example 1 (defaulting to `NORMAL` evaluation order):

```
$ java -jar target/lambda-calculus-interpreter-1.0-SNAPSHOT.jar

> (\s.(s s) (\x.x \y.y))
Input: (\s.(s s) (\x.x \y.y))
(\s.(s s) (\x.x \y.y))
((\x.x \y.y) (\x.x \y.y))
(\y.y (\x.x \y.y))
(\x.x \y.y)
\y.y
Result: \y.y
```

### Example 2 (using `APPLICATIVE` evaluation order):

```
$ java -jar target/lambda-calculus-interpreter-1.0-SNAPSHOT.jar APPLICATIVE

> (\s.(s s) (\x.x \y.y))
Input: (\s.(s s) (\x.x \y.y))
(\s.(s s) (\x.x \y.y))
(\s.(s s) \y.y)
(\y.y \y.y)
\y.y
Result: \y.y
```

### Example 3 (using `LAZY` evaluation order):

```
$ java -jar target/lambda-calculus-interpreter-1.0-SNAPSHOT.jar LAZY

> (\s.(s s) (\x.x \y.y))
Input: (\s.(s s) (\x.x \y.y))
(\s.(s s) (\x.x \y.y))
((\x.x \y.y) (\x.x \y.y))
(\y.y \y.y)
\y.y
Result: \y.y
```
