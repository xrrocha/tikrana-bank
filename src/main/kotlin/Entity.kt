import kotlin.reflect.KClass

interface Entity<E : Entity<E>> {
    val id: Id

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
