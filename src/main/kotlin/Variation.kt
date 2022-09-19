interface Variation<PRIMITIVE, RULE : Rule<PRIMITIVE>, OBJECT : Civilizable<PRIMITIVE>> {
    val rule: Array<out RULE>
    val condition: Condition<PRIMITIVE, RULE>
    val declaration: Declaration<PRIMITIVE, OBJECT>
}