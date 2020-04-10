package run.qontract.core.pattern

import run.qontract.core.Resolver
import run.qontract.core.Result
import run.qontract.core.mismatchResult
import run.qontract.core.value.NullValue
import run.qontract.core.value.StringValue
import run.qontract.core.value.Value

object NullPattern : Pattern {
    override fun matches(sampleData: Value?, resolver: Resolver): Result =
        when {
            sampleData is NullValue || sampleData is StringValue && sampleData.string.trim() == "" ->
                Result.Success()
            else -> mismatchResult("null", sampleData)
        }

    override fun generate(resolver: Resolver): Value = NullValue
    override fun newBasedOn(row: Row, resolver: Resolver): List<Pattern> = listOf(this)

    override fun parse(value: String, resolver: Resolver): Value =
        when(value.trim()) {
            "(null)" -> NullValue
            else -> throw ContractException("Failed to parse $value: it is not null.")
        }

    override fun matchesPattern(pattern: Pattern, resolver: Resolver): Boolean = pattern == NullPattern
    override val description: String = "null"

    override val pattern: Any = "(null)"
}

internal fun isNullablePattern(patternSpec: String): Boolean = withoutPatternDelimiters(patternSpec.trim()).endsWith("?")
internal fun withoutNullToken(patternSpec: String): String {
    return "(" + withoutPatternDelimiters(patternSpec.trim()).trim().removeSuffix("?") + ")"
}