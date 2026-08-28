import kotlin.math.sign

class Fraction (val numerator: Int,
                val denominator: Int,
                private val sign: Int = 1): Comparable<Fraction> {

    override fun compareTo(other: Fraction) : Int {
        return -1
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

    fun multiply(other: Fraction): Fraction {
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

        val resultSign = if ((newNumerator < 0) != (newDenominator < 0)) 1 else -1
        println(newNumerator > 0)
        println(newDenominator < 0)
        return Fraction(kotlin.math.abs(newNumerator), kotlin.math.abs(newDenominator), resultSign)
    }






}

fun main() {
    val a = Fraction(1,2,-1)
    println(a)
    println("printing: " + a.add(Fraction(1,3)))
    println(a.multiply(Fraction(5,2, -1)))
    println(a.div(Fraction(2,1)))
//    println(-Fraction(1,6) + Fraction(1,2))
//    println(Fraction(2,3) * Fraction(3,2))
//    println(Fraction(1,2) > Fraction(2,3)) // Comparable

}

