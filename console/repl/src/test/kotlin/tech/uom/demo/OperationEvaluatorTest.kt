package tech.uom.demo

import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf
import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.junit.Ignore
import org.junit.Test
import tech.units.indriya.unit.Units
import java.lang.IllegalArgumentException
import javax.measure.Quantity
import javax.measure.UnconvertibleException

class OperationEvaluatorTest {


    @Test
    fun `cannot add (yet)`() {

        val inputTree = Operation(
                value = "+",
                left = QuantityElement(value = "3", unit = "m"),
                right = QuantityElement(value = "4", unit = "m")
        )

        assertThat(
                OperationEvaluator().evaluate(inputTree)
                , nullValue())

    }

    @Test
    fun `can multiply`() {

        val inputTree = Operation(
                value = "*",
                left = QuantityElement(value = "3", unit = "m"),
                right = QuantityElement(value = "4", unit = "m")
        )

        val expectedResult = q(12, Units.SQUARE_METRE)

        val result = OperationEvaluator().evaluate(inputTree)
        assertThat(
                result
                , `is`(expectedResult as Quantity<*>))

    }

    @Test
    fun `can divide`() {
        val inputTree = Operation(
                value = "/",
                left = QuantityElement(value = "12", unit = "m^2"),
                right = QuantityElement(value = "3", unit = "m")
        )

        val expectedResult = q(4, Units.METRE)

        val result = OperationEvaluator().evaluate(inputTree)
        assertThat(
                result
                , `is`(expectedResult as Quantity<*>))

    }

    @Test
    fun `can add`() {
        val inputTree = Operation(
                value = "+",
                left = QuantityElement(value = "12", unit = "m"),
                right = QuantityElement(value = "3", unit = "m")
        )

        val expectedResult = q(15, Units.METRE)

        val result = OperationEvaluator().evaluate(inputTree)
        assertThat(
                result
                , `is`(expectedResult as Quantity<*>))

    }

    @Test
    fun `cannot add different quantities`() {
        val inputTree = Operation(
                value = "+",
                left = QuantityElement(value = "12", unit = "l"),
                right = QuantityElement(value = "3", unit = "m")
        )

        kotlin.runCatching {
            OperationEvaluator().evaluate(inputTree)
        }
                .onSuccess { fail("Expected exception to be thrown") }
                .onFailure { assertThat(it, IsInstanceOf(UnconvertibleException::class.java)) }

    }

    @Test
    fun `can subtract quantities`() {
        val inputTree = Operation(
                value = "-",
                left = QuantityElement(value = "12", unit = "m"),
                right = QuantityElement(value = "3", unit = "m")
        )

        val expectedResult = q(9, Units.METRE)

        val result = OperationEvaluator().evaluate(inputTree)
        assertThat(result, `is`(expectedResult as Quantity<*>))
    }

    @Test
    fun `cannot subtract different quantities`() {
        val inputTree = Operation(
                value = "-",
                left = QuantityElement(value = "12", unit = "l"),
                right = QuantityElement(value = "3", unit = "m")
        )

        kotlin.runCatching {
            OperationEvaluator().evaluate(inputTree)
        }
                .onSuccess { fail("Expected exception to be thrown") }
                .onFailure { assertThat(it, IsInstanceOf(UnconvertibleException::class.java)) }

    }

    @Test
    fun `can evaluate operation tree`() {
        // 1 m - 4 m + 7 m  =  (1 m - 4 m) + 7 m = 4 m
        val inputTree =
                Operation(
                        value = "+",
                        left = Operation(
                                value = "-",
                                left = QuantityElement(value = "1", unit = "m"),
                                right = QuantityElement(value = "4", unit = "m")
                        ),
                        right = QuantityElement(value = "7", unit = "m")
                )

        val expectedResult = q(4, Units.METRE)

        val result = OperationEvaluator().evaluate(inputTree)
        assertThat(result, `is`(expectedResult as Quantity<*>))
    }
}
