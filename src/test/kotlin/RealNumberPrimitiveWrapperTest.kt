import kotlin.test.Test
import kotlin.test.assertEquals

class RealNumberPrimitiveWrapperTest {

    @Test
    fun testRealNumberPrimitiveWrapper() {
        val realNumberPrimitiveWrapper = RealNumberPrimitiveWrapper()
        val realNumber = realNumberPrimitiveWrapper.of("1.0")

        assertEquals("1.0", realNumber.value)
    }
}