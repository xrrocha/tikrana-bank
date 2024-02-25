class Bank(
    name: Name
) : Entity<Bank> by Entity() {
    var name by scalar(name) { value ->
        value.trim().also {
            require(it.isNotEmpty()) { "Invalid empty bank name: '$value'" }
        }
    }
        private set

    // Renames bank and returns old name
    fun renameTo(newName: Name): Name =
        name.also { name = newName }
}