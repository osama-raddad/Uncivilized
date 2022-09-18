data class RealNumber(override val value: String) : WrappedPrimitive<String>

class RealNumberPrimitiveWrapper : PrimitiveWrapper<String, Rule<String>, RealNumber>, Rule<String> {
    override fun validate(primitive: String) = primitive.toDoubleOrNull() != null
    override val variations = listOf(createVariation(::RealNumber, allConditions(), this))
}

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val wrapper = RealNumberPrimitiveWrapper()
            val number: RealNumber = wrapper.of(args.firstOrNull() ?: readln())
            println(number.value)
        }
    }
}