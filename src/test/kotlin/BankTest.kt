import org.junit.jupiter.api.Assertions.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class BankTest {
    @Test
    fun `Creates bank properly`() {
        val bankName = "Monopoly Bank"
        val bank = Bank(bankName)
        assertEquals(bankName, bank.name)
    }

    @Test
    fun `Normalizes bank name`() {
        for (name in listOf("ACME Bank", " ACME Bank ", "\tACME Bank\t")) {
            assertEquals("ACME Bank", Bank(name).name)
        }
    }

    @Test
    fun `Rejects empty bank name`() {
        for (name in listOf("", " ", "\t \t")) {
            assertThrows(IllegalArgumentException::class.java) {
                Bank(name)
            }
        }
    }

    @Test
    fun `Updates bank name`() {
        val bank = Bank("Monopoly Bank")
        assertEquals("Monopoly Bank", bank.name)
        bank.name = "\tACME Bank "
        assertEquals("ACME Bank", bank.name)
    }
}
