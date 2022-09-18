interface Variations<PRIMITIVE, RULE : Rule<PRIMITIVE>, OBJECT : WrappedPrimitive<PRIMITIVE>> {
    val variation: List<Variation<PRIMITIVE, RULE, OBJECT>>
}