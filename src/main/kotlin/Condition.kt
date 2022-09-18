fun interface Condition<PRIMITIVE, RULE : Rule<PRIMITIVE>> {
    fun run(primitive: PRIMITIVE, rule: Array<out RULE>): Boolean
}