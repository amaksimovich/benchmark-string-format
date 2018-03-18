Java String Pattern Fill Benchmarks
-----------------------------------

JMH benchmark project testing performance of

* `StringBuilder`
* `StringBuffer`
* `String.+`
* `String.concat`
* `String.format`
* static `MessageFormat.format`
* `MessageFormat.format` (reused instance)

approaches to filling the following set of simple patterns

* `{string}-{string}`
* `{string}-{number}` (plain integer without any separators)
