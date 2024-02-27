import arrow.core.Either
import arrow.core.left
import arrow.core.right

class DomainException(
    val code: Int,
    message: String,
    cause: Throwable? = null
) : RuntimeException("%05d: %s".format(code, message), cause)

fun <R> Either.Companion.domainCatch(block: () -> R): Either<DomainException, R> =
    try {
        block().right()
    } catch (de: DomainException) {
        de.left()
    }
