class Bank(
    name: Name
) : Entity<Bank> by Entity() {

    var name by scalar(name) { value ->
        value.trim().also {
            require(it.validateName()) { "Invalid bank name: '$value'" }
        }
    }
        private set // Look ma: clients can't mutate name directly
    // Renames bank and returns old name
    fun renameTo(newName: Name): Name =
        name.also { name = newName }
}