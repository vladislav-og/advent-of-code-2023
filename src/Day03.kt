fun main() {


  fun isNumberAdjacentToASymbol(
    column: Int,
    curStartPointer: Int,
    curEndPointer: Int,
    input: List<String>
  ): Boolean {
    for (i in column - 1..column + 1) {
      if (i >= 0 && i < input.size) {
        for (j in curStartPointer - 1..curEndPointer + 1) {
          if (i == column && (j in curStartPointer..curEndPointer)) {
            continue
          }
          if (j >= 0 && j < input[i].length) {
            val char = input[i][j]
            if (!char.isDigit() && char != '.') {
              return true
            }
          }
        }
      }
    }
    return false
  }

  fun getScoreToAdd(
    i: Int,
    curStartPointer: Int,
    curEndPointer: Int,
    input: List<String>,
    curNumber: String,
    score: Int
  ): Int {
    var score1 = score
    var scoreToAdd = 0
    if (isNumberAdjacentToASymbol(i, curStartPointer, curEndPointer, input)) {
      scoreToAdd += curNumber.toInt()
    }
    score1 += scoreToAdd
    return score1
  }

  fun part1(input: List<String>): Int {
    var isCreatingNumber = false
    var curNumber = ""
    var curStartPointer = 0
    var curEndPointer = 0
    var score = 0
    for ((i, line) in input.withIndex()) {
      for ((j, char) in line.withIndex()) {
        if (char.isDigit()) {
          if (j == line.length - 1) {
            curEndPointer++
            curNumber += char

            isCreatingNumber = false
            score = getScoreToAdd(i, curStartPointer, curEndPointer, input, curNumber, score)
            curNumber = ""
          } else {
            if (isCreatingNumber) {
              curEndPointer++
            } else {
              isCreatingNumber = true
              curStartPointer = j
              curEndPointer = j
            }
            curNumber += char
          }
        } else {
          if (isCreatingNumber) {
            isCreatingNumber = false
            score = getScoreToAdd(i, curStartPointer, curEndPointer, input, curNumber, score)
            curNumber = ""
          }
        }
      }
    }
    return score
  }

  fun constructNumberMap(input: List<String>): Map<Pair<Int, Int>, Int> {
    val numbersMap = mutableMapOf<Pair<Int, Int>, Int>()
    var isCreatingNumber = false
    var curNumber = ""
    var curStartPointer = 0
    var curEndPointer = 0
    for ((i, line) in input.withIndex()) {
      for ((j, char) in line.withIndex()) {
        if (char.isDigit()) {
          if (j == line.length - 1) {
            curEndPointer++
            curNumber += char
            for (indexOfNumber in curStartPointer..curEndPointer) {
              val pair = Pair(i, indexOfNumber)
              numbersMap[pair] = curNumber.toInt()
            }
            curNumber = ""
            isCreatingNumber = false
          } else {
            if (isCreatingNumber) {
              curEndPointer++
            } else {
              isCreatingNumber = true
              curStartPointer = j
              curEndPointer = j
            }
            curNumber += char
          }
        } else {
          if (isCreatingNumber) {
            isCreatingNumber = false
            for (indexOfNumber in curStartPointer..curEndPointer) {
              val pair = Pair(i, indexOfNumber)
              numbersMap[pair] = curNumber.toInt()
            }
            curNumber = ""
          }
        }
      }
    }
    return numbersMap
  }

  fun getScore(
    startRowIndex: Int,
    starColIndex: Int,
    input: List<String>,
    numbersMap: Map<Pair<Int, Int>, Int>
  ): Int {
    var curStepScore = 0
    val surroundingNumbers = mutableListOf<Int>()
    for (starSurroundingRowIndex in startRowIndex - 1..startRowIndex + 1) {
      var startedAddingNumber = false
      if (starSurroundingRowIndex >= 0 && starSurroundingRowIndex < input.size) {
        for (starSurroundingColIndex in starColIndex - 1..starColIndex + 1) {
          if (starSurroundingColIndex >= 0 && starSurroundingColIndex < input[starSurroundingRowIndex].length) {
            val key = Pair(starSurroundingRowIndex, starSurroundingColIndex)
            if (numbersMap.containsKey(key)) {
              if (!startedAddingNumber) {
                startedAddingNumber = true
                surroundingNumbers.add(numbersMap[key]!!)
              }
            } else {
              startedAddingNumber = false
            }
          }
        }
      }
    }
    if (surroundingNumbers.size == 2) {
      curStepScore += surroundingNumbers.first() * surroundingNumbers.last()
    }
    return curStepScore
  }

  fun part2(input: List<String>): Int {
    var score = 0
    val numbersMap = constructNumberMap(input)
    for ((i, line) in input.withIndex()) {
      for ((j, char) in line.withIndex()) {
        if (char == '*') {
          score += getScore(i, j, input, numbersMap)
        }
      }
    }
    return score
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_test")
  val testInput1 = readInput("Day03_test1")
  val testInput2 = readInput("Day03_test2")
  val testInput3 = readInput("Day03_test3")

  println("Part 2 tests 0: ${part2(testInput)}")
  check(part2(testInput) == 467835)
  println("Part 2 tests 1: ${part2(testInput1)}")
  check(part2(testInput1) == 0)
  println("Part 2 tests 2: ${part2(testInput2)}")
  check(part2(testInput2) == 981085)
  println("Part 2 tests 3: ${part2(testInput3)}")
  check(part2(testInput3) == 45369)

  val input = readInput("Day03")
  check(part1(input) == 522726)
  part1(input).println()

  check(part2(input) == 81721933)
  part2(input).println()

  // no 80813613
  // no 81598609
  // ?? 81721933
}
