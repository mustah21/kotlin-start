import kotlin.random.Random
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.thread

class Lotto {

    private val lottoArray = mutableListOf<Int>()
    var stats: IntArray = IntArray(8)
    val lock = ReentrantReadWriteLock()
    val r_lock = lock.readLock()
    val w_lock = lock.writeLock()

    init {
        generateLotto()
    }

    fun generateLotto(): List<Int> {
        while (lottoArray.size <= 6) {
            val randomNumberGenerator = Random.nextInt(1, 41)
            if (!lottoArray.contains(randomNumberGenerator)) {
                lottoArray.add(randomNumberGenerator)
            }
        }
        return lottoArray
    }

    fun methodCheck(numbers: List<Int>) {
        var correct: Int = 0
        for (i in 0..6) {
            if (lottoArray.contains(numbers[i])) {
                correct++
            }
        }
        w_lock.lock()
        stats[correct]++
        w_lock.unlock()
    }


}

fun generate13Million(numberOfThreads: Int) {

    var totalCount = 0
    val lotto = Lotto()
    val totalGuesses = 13500000
    val numberOfThreads = numberOfThreads
    val guessesPerThread: Int = totalGuesses / numberOfThreads
    val threads = mutableListOf<Thread>()

    for (threadNumber in 0 until numberOfThreads) {
        val start = threadNumber * guessesPerThread
        val end = start + guessesPerThread


        val t = thread {
            println("Thread $threadNumber/$numberOfThreads starting for $guessesPerThread guesses")

            for (i in start until end) {
                val randomArray = mutableListOf<Int>()
                while (randomArray.size <= 6) {
                    val randomNumberGenerator = ThreadLocalRandom.current().nextInt(1, 41)
                    if (!randomArray.contains(randomNumberGenerator)) {
                        randomArray.add(randomNumberGenerator)
                    }
                }
                lotto.methodCheck(randomArray)

            }
        }
        threads.add(t)
    }
    threads.forEach { it.join() }

    for (i in 0..7) {
        totalCount += lotto.stats[i]
        println("$i:  ${lotto.stats[i]}")
    }


    println("Total count: $totalCount")
}


fun main(args: Array<String>) {
    val start: Long = System.currentTimeMillis()

    generate13Million(5)
    // when entering the number of threads make sure that threads is divisible by 13.5 mil
    // otherwise some guesses will be left off. crazy


    val end: Long = System.currentTimeMillis()
    val duration = (end - start)
    val durationInSeconds = (end - start) / 1000
    println("Duration in seconds: $durationInSeconds , Duration: $duration")

}