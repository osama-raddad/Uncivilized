package demo

import Civilizable
import Civilizer
import Rule

data class RealNumber(override val value: String) : Civilizable<String>
// region: flags
//{
//    fun isInteger(): Boolean = value.contains(".")
//    fun isDecimal(): Boolean = !isInteger()
//    fun isNegative(): Boolean = value.startsWith("-")
//    fun isPositive(): Boolean = !isNegative()
//    fun isZero(): Boolean = value == "0"
//    fun isPrime(): Boolean = value.toBigInteger().isProbablePrime(100)
//    fun isComposite(): Boolean = !isPrime()
//    fun isEven(): Boolean = value.toInt() % 2 == 0
//    fun isOdd(): Boolean = !isEven()
//}
// endregion

class RealNumberCivilizer : Civilizer<String, Rule<String>, RealNumber>, Rule<String> {
    override fun validate(primitive: String) = primitive.toDoubleOrNull() != null
    override val variations = listOf(createVariation(RealNumberDemo_ws::RealNumber, allConditions(), this))
}


val civilizer = RealNumberCivilizer()
val number: RealNumber = civilizer of (args.firstOrNull() ?: readln())
println("${number.value} is a ${number::class.simpleName}")