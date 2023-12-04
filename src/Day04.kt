import kotlin.math.pow

fun main() {

    fun getNumbers(text: String) = text.chunked(3).map { it.trim().toInt() }

    fun getWinningCount(myNumsDirty: String, winningNumsDirty: String): Int {
        val myNums = getNumbers(myNumsDirty)
        val winningNums = getNumbers(winningNumsDirty)
        var matchedNums = 0
        for (num in myNums) {
            if (winningNums.contains(num)) {
                matchedNums++
            }
        }
        return matchedNums
    }

    fun getMatchedPoints(game: String): Int {
        val (_, myNumsDirty, winningNumsDirty) = game.split(": ", " | ")
        val matchedNums = getWinningCount(myNumsDirty, winningNumsDirty)
        return matchedNums
    }

    fun part1(input: List<String>): Int {
        var score = 0
        for (game in input) {
            val matchedNums = getMatchedPoints(game)
            score += 2.0.pow(matchedNums.toDouble() - 1.0).toInt()
        }
        return score
    }

    fun createScratchCardsMap(input: List<String>): MutableMap<Int, Int> {
        val scratchcards = mutableMapOf<Int, Int>()
        for (i in input.indices) {
            scratchcards[i] = 1
        }
        return scratchcards
    }

    fun part2(input: List<String>): Int {
        val scratchcards = createScratchCardsMap(input)
        for ((gameNumber, game) in input.withIndex()) {
            val matchedNums = getMatchedPoints(game)

            for (i in 1..matchedNums) {
                scratchcards[gameNumber + i] = scratchcards[gameNumber + i]!! + scratchcards[gameNumber]!!
            }
        }
        return scratchcards.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    println("Part 1 tests: ${part1(testInput)}")
    println("Part 2 tests: ${part2(testInput)}")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    check(part1(input) == 23941)
    part2(input).println()
    check(part2(input) == 5571760)
}
