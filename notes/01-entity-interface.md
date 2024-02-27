# `Entity` interface

Entities in a memory image model are represented as classes implementing a
common `Entity` interface.

The `Entity` provides a rich DSL for defining _entity metadata_ in a
declarative, easy to write, easy to read fashion.

By leveraging this DSL, entity classes look and behave exactly like vanilla
Kotlin classes but, behind the scenes, provide strong correctness and
transactional guarantees.

The entity metadata declared by the DSL applies to:

- Property, entity an transaction-level validation
- Efficient, transparent participation in transactions
 
The delegation-enabled mechanism used to specify entity metadata is more
expressive, more type-safe and easier to read than "classical" `@` annotations.

## Entity Representation

The `Entity` interface is implemented by all entities in the model and dictates
for them to have an `id` identifier property of type `Id` (`Int`):

```kotlin
typealias Id = Int

interface Entity<E: Entity<E>> {
    val id: Id
}
```

Ids are drawn from a _model-wide_ sequence (that is, generated ids are unique
across _all_ entities and not within each individual entity). This is
implemented by the `Entity` interface companion object:

```kotlin
interface Entity<E: Entity<E>> {
    val id: Id

    companion object {
        private var nextId = 1

        operator fun <E : Entity<E>> invoke(): Entity {
            return object : Entity {
                override val id: Id = nextId++
            }
        }
    }
}
```

> 👉 Note that incrementing `nextId` need not be thread-safe as will be
> discussed later when presenting the memory image pattern

Entity classes implement `Entity` via delegation:

```kotlin
typealias Name = String
class Bank(initialName: Name) : Entity<Bank> by Entity() {
    // ... elided
}
```

[Next: Entity Classes](02-entity-classes.md)