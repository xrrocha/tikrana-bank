class Bank(initialName: Name) : Entity<Bank> by Entity() {

    var name by string(initialName) {
        normalizeWith(String::normalizeSpace)
        rule(1000, nonEmpty()) { "Bank name cannot be blank" }
        rule(1001, lengthRange(MIN_NAME_LENGTH, MAX_NAME_LENGTH)) {
            """
                Invalid bank name length (${it.length}),
                must be between $MIN_NAME_LENGTH and $MAX_NAME_LENGTH
            """
        }
    }
        private set // Clients can't mutate name directly

    // Renames bank and returns old name
    fun renameTo(newName: Name): Name =
        name.also { name = newName }

    companion object {
        // Type-safe, editable constraint metadata
        private const val MIN_NAME_LENGTH = 6
        private const val MAX_NAME_LENGTH = 32
    }
}