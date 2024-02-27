import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BankTest {
    @Test
    fun `Creates bank properly`() {
        val bankName = "Monopoly Bank"
        val bank = Bank(bankName)
        assertEquals(bankName, bank.name)
    }

    @Test
    fun `Normalizes bank name`() {
        for (whitespaceIrregularName in listOf("ACME Bank", " ACME Bank ", "\tACME\t \tBank\t")) {
            assertEquals("ACME Bank", Bank(whitespaceIrregularName).name)
        }
    }

    @Test
    fun `Rejects empty bank name`() {
        for (badEmptyName in listOf("", " ", "\t \t")) {
            val exception = assertFailsWith(DomainException::class) {
                Bank(badEmptyName)
            }
            assertEquals(1000, exception.code)
        }
    }

    @Test
    fun `Accept proper bank name lengths`() {
        val minLengthName = "a".repeat(4)
        assertEquals(minLengthName, Bank(minLengthName).name)
        val maxLengthName = "z".repeat(32)
        assertEquals(maxLengthName, Bank(maxLengthName).name)
    }

    @Test
    fun `Rejects too short, too long bank names`() {
        for (tooShortName in listOf("a", "ab", "abc")) {
            val exception = assertFailsWith(DomainException::class) {
                Bank(tooShortName)
            }
            assertEquals(1001, exception.code)
        }
        val tooLongName = "n".repeat(32 + 1)
        val exception = assertFailsWith(DomainException::class) {
            Bank(tooLongName)
        }
        assertEquals(1001, exception.code)
    }

    @Test
    fun `Updates bank name`() {
        val bank = Bank("Monopoly Bank")
        assertEquals("Monopoly Bank", bank.name)
        bank.renameTo("ACME Bank")
        assertEquals("ACME Bank", bank.name)
    }
}
