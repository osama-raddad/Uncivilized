fun interface Rule<PRIMITIVE> {
    fun validate(primitive: PRIMITIVE): Boolean
}