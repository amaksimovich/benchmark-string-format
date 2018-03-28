Java String Pattern Fill Benchmarks
-----------------------------------

JMH benchmark project testing performance of

* JRE `StringBuilder`
* JRE `StringBuffer`
* JRE `String.+`
* JRE `String.concat`
* JRE static `String.format`
* JRE `Formatter.format`
* JRE static `MessageFormat.format`
* JRE `MessageFormat.format` (reused instance)
* Apache Lang3 `StrSubstitutor.replace`
* String Template v4 `ST.renter`
* SLF4j static `MessageFormatter.format`

approaches to filling the following set of simple patterns

* `{string}-{string}`
* `{string}-{number}` (plain integer without any separators)
