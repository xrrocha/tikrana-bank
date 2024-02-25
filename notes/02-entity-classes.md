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

## Ensuring Property and Entity Integrity

As a design rule, we don't want our domain entity classes to wildly expose their
internals so client code can mess with them at their will (and at our peril).

It's good practice to protect writable properties (such as the bank's `name` 
above) from unrestricted mutation. Thus, we revise our above property definition
to make `name` un-writable by client code but, in exchange, gain semantic clarity
by giving the rename operation a meaningful name:

```kotlin
class Bank(name: Name) : Entity<Bank> by Entity() {
    var name by scalar(name) { value ->
        value.trim().also {
            require(it.isNotEmpty()) { "Invalid empty bank name: '$value'" }
        }
    }
        private set

    // Renames bank and returns old name
    fun renameTo(newName: Name): Name =
        name.also {
            // ... amy future synchronization will take place here...
            name = newName
        }
}
```

Here, we mar the `name` property as `private set` so it cannot be freely mutated
by client code.

We also add a meaningful `renameTo` method that sets the new name and courteously
returns the old one.

Yes, this may not seen like a gain to some who could rightfully argue we've
increased complexity without major gains in preserving integrity.

However, as we'll see later on, when the `name` participates of some other
structure (such as a `Map<Name, Bank>` used to guarantee bank name uniqueness, for
instance), the `renameTo` operation will need to take care of verifying the new
name is not a duplicate _and_ replace the old name by the new one in the map.

We wouldn't want our clients to be responsible for keeping things in sync or
(worse yet!) to be able to subvert our cherished uniqueness-ensuring mechanism.

[Previous](01-entity-interface.md)
