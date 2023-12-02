fun main() {

    val mapOfIntegerNames = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    fun sumOfFirstAndLastDigit(value: String): Int {
        val emptyList = mutableListOf<String>()
        for (digit in value) {
            if (digit.isDigit()) {
                emptyList.add(digit.toString())
            }
        }
        return (emptyList.first() + emptyList.last()).toInt()
    }

    fun sumOfFirstAndLastDigitForLetters(value: String): Int {
        val emptyList = mutableListOf<String>()
        var pointerStart = 0
        val pointerMax = value.length
        while (pointerStart < pointerMax) {
            for (digit in mapOfIntegerNames.keys) {
                if (value[pointerStart].isDigit()) {
                    emptyList.add(value[pointerStart].toString())
                    break
                }
                if (pointerStart + digit.length <= pointerMax) {
                    if (value[pointerStart].isDigit()) {
                        emptyList.add(value[pointerStart].toString())
                    } else if (value.substring(pointerStart, pointerStart + digit.length) == digit) {
                        emptyList.add(mapOfIntegerNames[digit].toString())
                        break
                    }
                }
            }
            pointerStart++
        }
        return (emptyList.first() + emptyList.last()).toInt()
    }

    fun part1(input: List<String>): Int {
        var totalCalibrationValue = 0
        input.forEach {
            totalCalibrationValue += sumOfFirstAndLastDigit(it)
        }
        return totalCalibrationValue
    }

    fun part2(input: List<String>): Int {
        var totalCalibrationValue = 0
        input.forEach {
            totalCalibrationValue += sumOfFirstAndLastDigitForLetters(it)
        }
        return totalCalibrationValue
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    println("Part 1 tests: ${part1(testInput)}")
    println("Part 2 tests: ${part2(testInput2)}")
    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
