fun main() {

    fun getNumbers(text: String) = Regex("[0-9]+").findAll(text).map { it.value.toInt() }.toList()

    fun getWinningCount(myNumsDirty: String, winningNumsDirty: String): Int {
        val myNums = getNumbers(myNumsDirty)
        val winningNums = getNumbers(winningNumsDirty)
        var matchedNums = 0;
        for (num in myNums) {
            if (winningNums.contains(num)) {
                matchedNums++
            }
        }
        return matchedNums
    }

    fun part1(input: List<String>): Int {
        var score = 0
        for (game in input) {
            val (myNumsDirty, winningNumsDirty) = game.split(":")[1].split("|")
            val matchedNums = getWinningCount(myNumsDirty, winningNumsDirty)
            score += Math.pow(2.0, matchedNums.toDouble() - 1.0).toInt()
        }
        return score
    }

    fun createScrathcardsMap(input: List<String>): MutableMap<Int, Int> {
        val scratchcards = mutableMapOf<Int, Int>()
        for (i in input.indices) {
            scratchcards[i] = 1
        }
        return scratchcards
    }

    fun part2(input: List<String>): Int {
        val scratchcards = createScrathcardsMap(input)
        for ((gameNumber, game) in input.withIndex()) {
            val (myNumsDirty, winningNumsDirty) = game.split(":")[1].split("|")
            val matchedNums = getWinningCount(myNumsDirty, winningNumsDirty)

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
    part2(input).println()
}
