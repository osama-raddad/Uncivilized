package demo

import Civilizable
import Civilizer
import Rule
import java.math.BigInteger

sealed class RealNumber : Civilizable<String>, Number() {
    class Zero(override val value: String) : RealNumber()
    sealed class Integer : RealNumber() {
        sealed class Positive : Integer() {
            sealed class Even : Positive() {
                class Prime(override val value: String) : Even()
                class Composite(override val value: String) : Even()
            }

            sealed class Odd : Positive() {
                class Prime(override val value: String) : Odd()
                class Composite(override val value: String) : Odd()
            }
        }

        sealed class Negative : Integer() {
            class Even(override val value: String) : Negative()
            class Odd(override val value: String) : Negative()
        }
    }

    sealed class Decimal : RealNumber() {
        sealed class Positive : Decimal() {
            class Even(override val value: String) : Positive()
            class Odd(override val value: String) : Positive()
        }

        sealed class Negative : Decimal() {
            class Even(override val value: String) : Negative()
            class Odd(override val value: String) : Negative()
        }
    }

    override fun toByte(): Byte = value.toByte()

    override fun toChar(): Char = value.chars().findFirst().asInt.toChar()

    override fun toDouble(): Double = value.toDouble()

    override fun toFloat(): Float = value.toFloat()

    override fun toInt(): Int = value.toInt()

    override fun toLong(): Long = value.toLong()

    override fun toShort(): Short = value.toShort()
}

class IntegerRule : Rule<String> {
    override fun validate(primitive: String): Boolean = primitive.toIntOrNull() != null
}

class DecimalRule : Rule<String> {
    override fun validate(primitive: String): Boolean = primitive.toDoubleOrNull() != null
}

class NegativeRule : Rule<String> {
    override fun validate(primitive: String): Boolean {
        return when (primitive.toIntOrNull() ?: primitive.toDoubleOrNull()) {
            is Int -> primitive.toInt() < 0
            is Double -> primitive.toDouble() < 0.0
            else -> false
        }
    }
}

class PositiveRule : Rule<String> {
    override fun validate(primitive: String): Boolean {
        return when (primitive.toIntOrNull() ?: primitive.toDoubleOrNull()) {
            is Int -> primitive.toInt() > 0
            is Double -> primitive.toDouble() > 0.0
            else -> false
        }
    }
}

class EvenRule : Rule<String> {
    override fun validate(primitive: String): Boolean {
        return when (primitive.toIntOrNull() ?: primitive.toDoubleOrNull()) {
            is Int -> primitive.toInt() % 2 == 0
            is Double -> primitive.toDouble() % 2 == 0.0
            else -> false
        }
    }
}

class OddRule : Rule<String> {
    override fun validate(primitive: String): Boolean {
        return when (primitive.toIntOrNull() ?: primitive.toDoubleOrNull()) {
            is Int -> primitive.toInt() % 2 != 0
            is Double -> primitive.toDouble() % 2 != 0.0
            else -> false
        }
    }
}

class PrimeRule : Rule<String> {
    override fun validate(primitive: String): Boolean {
        return (primitive.toBigIntegerOrNull() ?: return false).isProbablePrime(100)
    }
}

class CompositeRule : Rule<String> {
    override fun validate(primitive: String): Boolean {
        return (primitive.toBigIntegerOrNull() ?: return false).isProbablePrime(100).not()
    }
}

class ZeroRule : Rule<String> {
    override fun validate(primitive: String): Boolean {
        return primitive.toBigIntegerOrNull() == BigInteger.ZERO
    }
}


class RealNumberCivilizer : Civilizer<String, Rule<String>, RealNumber> {
    override val variations = listOf(
        createVariation(RealNumber::Zero, allConditions(), ZeroRule()),
        createVariation(
            RealNumber.Integer.Positive.Even::Prime,
            allConditions(),
            IntegerRule(),
            PrimeRule(),
            EvenRule(),
            PositiveRule()
        ),
        createVariation(
            RealNumber.Integer.Positive.Odd::Prime,
            allConditions(),
            IntegerRule(),
            PrimeRule(),
            OddRule(),
            PositiveRule()
        ),
        createVariation(
            RealNumber.Integer.Positive.Even::Composite,
            allConditions(),
            IntegerRule(),
            CompositeRule(),
            EvenRule(),
            PositiveRule()
        ),
        createVariation(
            RealNumber.Integer.Positive.Odd::Composite,
            allConditions(),
            IntegerRule(),
            CompositeRule(),
            OddRule(),
            PositiveRule()
        ),
        createVariation(RealNumber.Integer.Negative::Even, allConditions(), IntegerRule(), EvenRule(), NegativeRule()),
        createVariation(RealNumber.Integer.Negative::Odd, allConditions(), IntegerRule(), OddRule(), NegativeRule()),
        createVariation(RealNumber.Decimal.Positive::Even, allConditions(), DecimalRule(), EvenRule(), PositiveRule()),
        createVariation(RealNumber.Decimal.Positive::Odd, allConditions(), DecimalRule(), OddRule(), PositiveRule()),
        createVariation(RealNumber.Decimal.Negative::Even, allConditions(), DecimalRule(), EvenRule(), NegativeRule()),
        createVariation(RealNumber.Decimal.Negative::Odd, allConditions(), DecimalRule(), OddRule(), NegativeRule())
    )
}


val wrapper = RealNumberCivilizer()
val number: RealNumber = wrapper.of(readln())
println("${number.value} is ${number::class.qualifiedName}")

//
//fun testInteger(wrapper: RealNumberCivilizer) {
//    var integer: Int = -1000
//    while (integer <= 1000) {
//        val number: RealNumber = wrapper.of(integer.toString())
//        println("${number.toInt()} is ${number::class.qualifiedName}")
//        integer += 1
//    }
//}
//
//fun testDecimal(wrapper: RealNumberCivilizer) {
//    var decimal: Double = -100.0
//    while (decimal <= 100.0) {
//        val number: RealNumber = wrapper.of(decimal.toString())
//        println("${number.toDouble()} is ${number::class.qualifiedName}")
//        decimal += 0.1
//    }
//}
