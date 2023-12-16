import kotlin.math.pow

fun main() {

  fun zeroAndOneGeneratorVariations(arraysSize: Int): List<List<Int>> {
    val variations = mutableListOf<List<Int>>()
    for (i in 0 until 2.0.pow(arraysSize.toDouble()).toInt()) {
      val elements = Integer.toBinaryString(i).map { it.toString().toInt() }
      if (elements.size < arraysSize) {
        val zeros = MutableList(arraysSize) { 0 }
        elements.forEachIndexed { index, element -> zeros[arraysSize - elements.size + index] = element }
        variations.add(zeros)
      } else {
        variations.add(elements)
      }
    }
    return variations
  }

  fun isSignalValid(signals: MutableList<Char>, targetedSignalResult: List<Int>): Boolean {
    val curSignalResult = mutableListOf<Int>()
    var activeSignalCount = 0
    for ((i, signal) in signals.withIndex()) {
      if (signal == '#') {
        activeSignalCount++
        if (i == signals.size - 1) curSignalResult.add(activeSignalCount)
      } else if (activeSignalCount != 0 && signal == '.') {
        curSignalResult.add(activeSignalCount)
        activeSignalCount = 0
      }
    }
    return curSignalResult.size == targetedSignalResult.size && curSignalResult == targetedSignalResult
  }

  fun getValidRecordCount(signals: String, targetedSignalResult: List<Int>): Int {
    val unknownSignalsCoordinates = mutableListOf<Int>()
    signals.forEachIndexed { i, signal ->
      if (signal == '?') {
        unknownSignalsCoordinates.add(i)
      }
    }

    var optionsCount = 0
    val zeroAndOneGeneratorCombination = zeroAndOneGeneratorVariations(unknownSignalsCoordinates.size)
    zeroAndOneGeneratorCombination.forEach { combination ->
      val signalsCopy = signals.toMutableList()
      combination.forEachIndexed { i, value ->
        signalsCopy[unknownSignalsCoordinates[i]] = if (value == 0) '.' else '#'
      }
      if (isSignalValid(signalsCopy, targetedSignalResult)) optionsCount++
    }

    return optionsCount
  }

  fun part1(input: List<String>): Int {
    var score = 0
    for (record in input) {
      val (signals, values) = record.split(" ")
      val validRecordCount = getValidRecordCount(signals, values.split(",").map { it.toInt() }.toList())
      score += validRecordCount
    }
    return score
  }

  fun part2(input: List<String>): Long {
    return 0
  }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("Day12_test")
  println("Part 1 tests: ${part1(testInput)}")
//  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 21)
//  check(part2(testInput) == 525152)

  val input = readInput("Day12")
  part1(input).println()
//  check(part1(input) == 7694)
//  part2(input).println()
//  check(part2(input) == ???)

}
