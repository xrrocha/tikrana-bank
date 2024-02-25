import arrow.core.getOrElse

class Bank(name: Name) : Entity<Bank> by Entity() {
    var name by scalar(name) { value ->
        value.prepareName()
            .mapLeft { "Invalid bank name: $it" }
            .getOrElse { throw IllegalArgumentException(it) }
    }
        private set // Look ma: clients can't mutate name directly

    // Renames bank and returns old name
    fun renameTo(newName: Name): Name =
        name.also { name = newName }
}