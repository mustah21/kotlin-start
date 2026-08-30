import kotlin.math.roundToInt

// write a function for grades
// < 40 - fail , > 40 fail
// create a map for grades

fun grader(points: Double): Int {
    val mapGrades = mapOf(
        39.9 to 0,
        40.0 to 1,
        52.1 to 2,
        64.1 to 3,
        76.1 to 4,
        88.1 to 5
    )

    val matchedKey = mapGrades.keys.filter { points >= it }
    return mapGrades[matchedKey.maxOrNull()] ?: 0;
}

fun gradesFunction(points: Double): Int? {
    if (points !in 0.0..100.0)
        return null
    return if (points < 40.0) 0
            else (0.5 + (points - 40)*(5.49-0.5) / (100 - 40)).roundToInt()
}


fun main(args: Array<String>) {
    println("My grade is: " + gradesFunction(111.0))
}