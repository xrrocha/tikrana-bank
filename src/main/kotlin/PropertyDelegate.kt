import kotlin.reflect.KProperty

class Rule<P>(
    val code: Int,
    val validation: (P) -> Boolean,
    val errorMessage: (P) -> String
) {
    fun applyTo(value: P) {
        if (!validation(value))
            throw DomainException(code, errorMessage(value))
    }
}

abstract class ScalarDelegate<E, P, D : ScalarDelegate<E, P, D>>(
    initialValue: P,
    config: D.() -> Unit
) {
    private var value: P

    private var normalize: (P) -> P = { it }
    private val rules: MutableList<Rule<P>> = mutableListOf()

    init {
        @Suppress("unchecked_cast") // leaking this in constructor
        val target = this as D
        config(target)

        this.value = normalize(initialValue).also(::validate)
    }

    operator fun getValue(thisRef: E, property: KProperty<*>): P = value

    operator fun setValue(thisRef: E, property: KProperty<*>, value: P) {
        this.value = normalize(value).also(::validate)
    }

    private fun validate(value: P) {
        rules.forEach { rule -> rule.applyTo(value) }
    }

    fun rule(
        code: Int,
        validation: (P) -> Boolean,
        errorMessage: (P) -> String
    ) {
        rules += Rule(code, validation, errorMessage)
    }

    fun normalizeWith(normalize: (P) -> P) {
        this.normalize = normalize
    }
}

class StringScalar<E>(
    initialValue: String,
    config: StringScalar<E>.() -> Unit
) : ScalarDelegate<E, String, StringScalar<E>>(initialValue, config) {

    fun nonEmpty() = String::isNotEmpty

    fun lengthRange(min: Int, max: Int) = { str: String ->
        str.length in min..max
    }
}

fun <E : Entity<E>> E.string(
    initialValue: String,
    config: StringScalar<E>.() -> Unit
): StringScalar<E> =
    StringScalar<E>(initialValue, config)
