import java.math.BigDecimal

typealias Id = Int
typealias Name = String
typealias Amount = BigDecimal
typealias ErrorMessage = String

fun String.normalizeSpace(): String =
    trim()
        .split("\\s+".toRegex())
        .joinToString(" ")
