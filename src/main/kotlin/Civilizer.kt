interface Civilizer<PRIMITIVE, RULE : Rule<PRIMITIVE>, OBJECT : Civilizable<PRIMITIVE>> {

    val variations: Collection<Variation<PRIMITIVE, RULE, OBJECT>>

    infix fun of(value: PRIMITIVE): OBJECT {
        val safeValue = requireNotNull(value)
        val selectedConfig = findVariationByValue(safeValue)
        return instantiate(selectedConfig, safeValue)
    }

    fun <PRIMITIVE, RULE : Rule<PRIMITIVE>> allConditions(): Condition<PRIMITIVE, RULE> =
        Condition { primitive, rule -> rule.all { it.validate(primitive) } }

    fun <PRIMITIVE, RULE : Rule<PRIMITIVE>> anyConditions(): Condition<PRIMITIVE, RULE> =
        Condition { primitive, rule -> rule.any { it.validate(primitive) } }


    private fun instantiate(ageVariation: Variation<PRIMITIVE, RULE, OBJECT>, value: PRIMITIVE): OBJECT =
        ageVariation.declaration(value)

    private fun findVariationByValue(value: PRIMITIVE): Variation<PRIMITIVE, RULE, OBJECT> =
        variations.find(isConfigForValue(value)) ?: noVariationFound(value,variations)

    fun noVariationFound(value: PRIMITIVE, variations: Collection<Variation<PRIMITIVE, RULE, OBJECT>>): Nothing {
        throw IllegalArgumentException("No variation found for value $value in ${variations.flatMap { it.rule.map { it::class.simpleName } }.joinToString()}")
    }

    private fun isConfigForValue(value: PRIMITIVE): (Variation<PRIMITIVE, RULE, OBJECT>) -> Boolean =
        { it.condition.run(value, it.rule) }

    fun createVariation(
        declaration: Declaration<PRIMITIVE, OBJECT>,
        condition: Condition<PRIMITIVE, RULE>,
        vararg rule: RULE
    ) = object : Variation<PRIMITIVE, RULE, OBJECT> {
        override val rule: Array<out RULE> = rule
        override val declaration: Declaration<PRIMITIVE, OBJECT> = declaration
        override val condition: Condition<PRIMITIVE, RULE> = condition
    }


}

typealias Declaration<PRIMITIVE, OBJECT> = (PRIMITIVE) -> OBJECT

