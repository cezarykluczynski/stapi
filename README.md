## About
Star Trek API is an attempt to create public Star Trek API, similiar to what
[Star Wars API](https://github.com/phalt/swapi) and [PokeAPI](https://github.com/PokeAPI/pokeapi) accomplished.

**Note:** this is a research project, not suitable for production use.

## Installation
Star Trek API could be run by anyone, using resources found free on the internet. Some technical knowledge is required.

### Prerequisites
PHP, MySQL (or other database of choice) will be required for this part.

Star Trek API uses content from well-known [Memory Alpha](http://memory-alpha.wikia.com/wiki/Portal:Main).
Therefore, MediaWiki, the software Wikipedia runs on, and
[Memory Alpha XML dump](http://memory-alpha.wikia.com/wiki/Special:Statistics) is required. MediaWiki has tutorials
on [installing](https://www.mediawiki.org/wiki/Manual:Installation_guide) and
[importing dumps](https://www.mediawiki.org/wiki/Manual:Importing_XML_dumps).

### Running STAPI server
Java 8, Oracle Database Express Edition 11g (or compatible) will be required for this part.

Gradle is used for building. If you do not plan on development, you can use Gradle Wrapper:

```sh
./gradlew bootRun
```

Otherwise, use gradle:

```sh
gradle bootRun
```
