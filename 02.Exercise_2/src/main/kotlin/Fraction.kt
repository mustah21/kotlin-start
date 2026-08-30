class Fraction(
    numerator: Int,
    denominator: Int,
    private val sign: Int = 1
) : Comparable<Fraction> {

    val numerator: Int
    val denominator: Int
    init {
        require(denominator != 0)
        val divisor = gcd(numerator, denominator)
        this.numerator = numerator / divisor
        this.denominator = denominator / divisor
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Fraction) return false
        return numerator == other.numerator &&
                denominator == other.denominator &&
                sign == other.sign
    }

    override fun compareTo(other: Fraction): Int {
        val currentFraction = (this.numerator.toDouble() / this.denominator.toDouble()) * this.sign
        val otherFraction = (other.numerator.toDouble() / other.denominator.toDouble()) * other.sign
        return currentFraction.compareTo(otherFraction)
    }

    fun gcd(x: Int, y: Int): Int {
        var x = x
        var y = y

        while(y != 0) {
            val temp = x % y
            x = y
            y = temp
        }
        return kotlin.math.abs(x)
    }

    override fun toString(): String {
        return if (sign == 1) "$numerator/$denominator" else "-$numerator/$denominator"
    }


    fun add(other: Fraction): Fraction {
        val signedNumThis = this.numerator * this.sign
        val signedNumOther = other.numerator * other.sign

        val newNumerator = (signedNumThis * other.denominator) + (signedNumOther * this.denominator)
        val newDenominator = this.denominator * other.denominator
        return Fraction(newNumerator, newDenominator)

    }

    fun mult(other: Fraction): Fraction {
        val signedNumThis = this.numerator * this.sign
        val signedNumOther = other.numerator * other.sign

        val newNumerator = signedNumThis * signedNumOther
        val newDenominator = this.denominator * other.denominator
        val resultSign = if ((newNumerator < 0) != (newDenominator < 0)) -1 else 1

        return Fraction(kotlin.math.abs(newNumerator), kotlin.math.abs(newDenominator), resultSign)
    }

    fun div(other: Fraction): Fraction {
        val signedNumThis = this.numerator * this.sign
        val signedNumOther = other.numerator * other.sign

        val newNumerator = signedNumThis * other.denominator
        val newDenominator = this.denominator * signedNumOther

        val resultSign = if ((this.sign * other.sign) == -1) -1 else 1
        return Fraction(kotlin.math.abs(newNumerator), kotlin.math.abs(newDenominator), resultSign)
    }

    fun negate(): Fraction {
        return Fraction(numerator, denominator, -sign)
    }

    operator fun unaryMinus(): Fraction {
        return negate()
    }
    operator fun plus(other: Fraction): Fraction {
        return this.add(other)
    }
    operator fun times(other: Fraction): Fraction {
        return this.mult(other)
    }

}

fun main() {
    val a = Fraction(1,2,-1)
    println(a)
    println(a.add(Fraction(1,3)))

    println(a.mult(Fraction(5,2, -1)))
    println(a.div(Fraction(2,1)))
    println(-Fraction(1,6) + Fraction(1,2))
    println(Fraction(2,3) * Fraction(3,2))
    println(Fraction(1,2) > Fraction(2,3)) // Comparable
}

