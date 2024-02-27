import kotlin.reflect.KProperty

class Bank(name: Name) : Entity<Bank> by Entity() {

    val uniques: Set<List<KProperty<*>>> = setOf(
        listOf(::name)
    )

    var name by string(name) {
        normalizeWith(String::normalizeSpace)
        rule(1000, nonEmpty()) {
            "Bank name cannot be blank: '$it'"
        }
        val minLength = 4
        val maxLength = 32
        rule(1001, lengthRange(minLength, maxLength)) {
            """
                Invalid bank name length (${it.length}),
                must be between $minLength and $maxLength
            """
        }
    }
        private set // Look ma: clients can't mutate name directly

    // Renames bank and returns old name
    fun renameTo(newName: Name): Name =
        name.also { name = newName }
}