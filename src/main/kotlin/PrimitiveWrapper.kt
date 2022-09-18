interface PrimitiveWrapper<PRIMITIVE, RULE : Rule<PRIMITIVE>, OBJECT : WrappedPrimitive<PRIMITIVE>> {

    val variations: List<Variation<PRIMITIVE, RULE, OBJECT>>


    fun of(value: PRIMITIVE): OBJECT {
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
        requireNotNull(variations.find(isConfigForValue(value))) { noVariationFound(value) }

    fun noVariationFound(value: PRIMITIVE): () -> Any =
        { "$value is not a valid value for ${this::class.simpleName}" }

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

