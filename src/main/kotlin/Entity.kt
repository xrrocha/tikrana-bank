import java.math.BigDecimal
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

typealias Id = Int
typealias Name = String
typealias Amount = BigDecimal
typealias ErrorMessage = String
typealias Errors = Map<Name, ErrorMessage>

interface Entity<E : Entity<E>> {
    val id: Id

    fun <P> scalar(
        initialValue: P,
        checkValue: (P) -> P = { it }
    ): Scalar<E, P> =
        Scalar<E, P>(initialValue, checkValue)

    companion object {
        private var nextId = 1

        inline operator fun <reified E : Entity<E>> invoke(): Entity<E> =
            newInstance(E::class)

        fun <E : Entity<E>> newInstance(kClass: KClass<E>): Entity<E> =
            object : Entity<E> {
                override val id: Id = nextId++
            }
    }
}

class Scalar<E, P>(
    initialValue: P,
    private val checkValue: (P) -> P = { it }
) {
    private var value: P

    init {
        this.value = checkValue(initialValue)
    }

    operator fun getValue(thisRef: E, property: KProperty<*>): P = value

    operator fun setValue(thisRef: E, property: KProperty<*>, value: P) {
        this.value = checkValue(value)
    }
}
