# Tikrana Bank : Entities, delegates, memory image, oh my!

_Tikrana Bank_ is an exploration of the
[memory image](https://martinfowler.com/bliki/MemoryImage.html) pattern
using Kotlin (on the JVM to start with, but aiming at portability).

This exploration takes advantage of some of Kotlin's unique, powerful 
constructs (such as [delegates](https://kotlinlang.org/docs/delegation.html))
with the following design goals:

- Minimize verbosity
- Maximize readability
- Maximize type safety
- Replace boilerplate code with declarative, high-level constructs

Exploration notes are:

1. [The `Entity` Interface](notes/01-entity-interface.md)
2. [Entity Classes](notes/02-entity-classes.md)