fun main() {

  fun readGameMap(input: List<String>): MutableMap<String, Pair<String, String>> {
    val gameMap = mutableMapOf<String, Pair<String, String>>()
    for (step in input.drop(2)) {
      val (from, toDirty) = step.split(" = ")
      val (left, right) = toDirty.drop(1).dropLast(1).split(", ")
      val to = Pair(left, right)
      gameMap[from] = to
    }
    return gameMap
  }

  fun chooseNextTurn(
    gameMap: MutableMap<String, Pair<String, String>>,
    curStep: String,
    nextTurn: String
  ): String {
    val (left, right) = gameMap[curStep]!!
    return if (nextTurn == "R") {
      right
    } else {
      left
    }
  }

  fun chooseNextSteps(
    gameMap: MutableMap<String, Pair<String, String>>,
    curSteps: List<String>,
    nextTurn: String
  ): List<String> {
    val nextSteps = mutableListOf<String>()
    for (curStep in curSteps) {
      nextSteps.add(chooseNextTurn(gameMap, curStep, nextTurn))
    }
    return nextSteps
  }

  fun moveStepPointer(instructions: List<String>, instructionPointer: Int): Int {
    var instructionPointer1 = instructionPointer
    if (instructions.size == instructionPointer1 + 1) {
      instructionPointer1 = 0
    } else {
      instructionPointer1++
    }
    return instructionPointer1
  }

  fun part1(input: List<String>): Int {
    val instructions = input[0].chunked(1)
    var score = 0
    val gameMap = readGameMap(input)
    var instructionPointer = 0
    var curStep = "AAA"
    val endStep = "ZZZ"
    while (curStep != endStep) {
      val nextTurn = instructions[instructionPointer]
      curStep = chooseNextTurn(gameMap, curStep, nextTurn)
      score++
      instructionPointer = moveStepPointer(instructions, instructionPointer)
    }
    return score
  }

  fun greatestCommonDivisor(a: Long, b: Long): Long {
    return if (b == 0L) a else greatestCommonDivisor(b, a % b)
  }

  fun leastCommonMultiplier(a: Long, b: Long): Long {
    return a * (b / greatestCommonDivisor(a, b))
  }

  fun part2(input: List<String>): Long {
    val instructions = input[0].chunked(1)
    var score = 0
    val gameMap = readGameMap(input)
    var instructionPointer = 0
    var curSteps = gameMap.keys.filter { it.endsWith("A") }
    val zStepFoundStepNumber = IntArray(curSteps.size) { 0 }
    while (curSteps.any { !it.endsWith("Z") }) {
      val nextTurn = instructions[instructionPointer]
      curSteps = chooseNextSteps(gameMap, curSteps, nextTurn)
      score++
      curSteps.forEach { step ->
        if (step.endsWith("Z")) {
          zStepFoundStepNumber[curSteps.indexOf(step)] = score
        }
      }
      if (zStepFoundStepNumber.all { it != 0 }) {
        break
      }
      instructionPointer = moveStepPointer(instructions, instructionPointer)
    }
    return zStepFoundStepNumber.fold(1L) { acc, i -> leastCommonMultiplier(acc, i.toLong()) }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day08_test")
//  println("Part 1 tests: ${part1(testInput)}")
  println("Part 2 tests: ${part2(testInput)}")
//  check(part1(testInput) == 6)
  check(part2(testInput) == 6L)

  val input = readInput("Day08")
  part1(input).println()
    check(part1(input) == 21883)
    part2(input).println()
    check(part2(input) == 12833235391111)

  // low 1774635555
}
