import java.math.BigDecimal

typealias Id = Int
typealias Name = String
typealias Amount = BigDecimal

fun String.normalizeSpace(): String =
    trim()
        .split("\\s+".toRegex())
        .joinToString(" ")
