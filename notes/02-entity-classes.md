# Entity Classes

Entity classes implement the `Entity` interface via simple delegation:

```kotlin
typealias Name = String
class Bank(initialName: Name) : Entity<Bank> by Entity() {
    // ... elided
}
```

## Scalar Property Declaration

Scalar properties are declared using the `scalar` DSL function:

```kotlin
class Bank(initialName: Name) : Entity<Bank> by Entity() {
    var name by scalar(initialName) {
        it.trim().also {
            require(it.isNotEmpty()) { "Invalid empty bank name: '$it'" }
        }
    }
}
```

Given the above definition, client code can create and manipulate `Bank` instances
as in:

```kotlin
val bank = Bank("Monopoly Bank")
assertEquals("Monopoly Bank", bank.name)

bank.name = "\tACME Bank "
// Bank is auto-normalized to trim it
assertEquals("ACME Bank", bank.name)

// Bank name cannot be blank or empty
assertThrows(IllegalArgumentException::class.java) {
    Bank("\t \t")
}
```

If, instead of leveraging the `Entity`-provided data definition DSL, we'd written 
this class by hand, the implementation would have been:

```kotlin
class Bank(initialName: Name) : Entity<Bank> by Entity() {
    var name: Name = initialName
        set(value) {
            val nValue = value.trim()
            require(nValue.isEmpty()) { "Invalid bank empty name: '$value'" }
            field = nValue
        }
}
```

So far, using the `scalar` delegate may not look like such a _big_ boon, but things
get better (and simpler!) the more we leverage the data definition DSL.

[Previous](01-entity-interface.md)
