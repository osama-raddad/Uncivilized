import demo.RealNumberDemo2_ws
import kotlin.test.Test
import kotlin.test.assertEquals

class RealNumberCivilizerTest {

    @Test
    fun testRealNumberPrimitiveWrapper() {
        val realNumberPrimitiveWrapper = RealNumberDemo2_ws.RealNumberCivilizer()
        val realNumber = realNumberPrimitiveWrapper.of("1.0")

        assertEquals("1.0", realNumber.value)
    }
}