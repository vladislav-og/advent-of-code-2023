fun main() {

  fun makeStepRight(curStep: Pair<Int, Int>) = Pair(curStep.first + 1, curStep.second)

  fun makeStepLeft(curStep: Pair<Int, Int>) = Pair(curStep.first - 1, curStep.second)

  fun makeStepDown(curStep: Pair<Int, Int>) = Pair(curStep.first, curStep.second + 1)

  fun makeStepUp(curStep: Pair<Int, Int>) = Pair(curStep.first, curStep.second - 1)

  fun makeStartStep(
    map: List<String>, curStep: Pair<Int, Int>
  ): Pair<Int, Int> {
    if (map[curStep.second + 1][curStep.first] == '|') {
      return makeStepDown(curStep)
    }
    if (map[curStep.second][curStep.first + 1] == '-' || map[curStep.second][curStep.first + 1] == '7') {
      return makeStepRight(curStep)
    }
    if (map[curStep.second - 1][curStep.first] == '|' || map[curStep.second][curStep.first + 1] == 'F') {
      return makeStepUp(curStep)
    }
    return makeStepLeft(curStep)
  }

  fun makeStep(
    curStepValue: Char, isMovingFromLeftToRight: Boolean, curStep: Pair<Int, Int>, isMovingFromRightToLeft: Boolean
  ): Pair<Int, Int> {
    if (curStepValue == '-') {
      if (isMovingFromLeftToRight) {
        return makeStepRight(curStep)
      }
      return makeStepLeft(curStep)
    }
    if (curStepValue == 'J') {
      if (isMovingFromLeftToRight) {
        return makeStepUp(curStep)
      }
      return makeStepLeft(curStep)
    }
    if (curStepValue == 'L') {
      if (isMovingFromRightToLeft) {
        return makeStepUp(curStep)
      }
      return makeStepRight(curStep)
    }
    if (curStepValue == '7') {
      if (isMovingFromLeftToRight) {
        return makeStepDown(curStep)
      }
      return makeStepLeft(curStep)
    }

    if (isMovingFromRightToLeft) {
      return makeStepDown(curStep)
    }
    return makeStepRight(curStep)
  }

  fun makeStep(previousStep: Pair<Int, Int>, curStep: Pair<Int, Int>, map: List<String>): Pair<Int, Int> {
    val isMovingFromLeftToRight = previousStep.first < curStep.first
    val isMovingFromRightToLeft = previousStep.first > curStep.first

    if (map[curStep.second][curStep.first] == 'S') {
      return makeStartStep(map, curStep)
    }

    val curStepValue = map[curStep.second][curStep.first]
    if (curStepValue == '|') {
      if (previousStep.second < curStep.second) {
        return makeStepDown(curStep)
      }
      return makeStepUp(curStep)
    }

    return makeStep(curStepValue, isMovingFromLeftToRight, curStep, isMovingFromRightToLeft)
  }

  fun findStartPosition(input: List<String>): Pair<Int, Int> {
    for ((y, line) in input.withIndex()) {
      for ((x, char) in line.withIndex()) {
        if (char == 'S') {
          return Pair(x, y)
        }
      }
    }
    throw Exception("Start position not found")
  }

  fun part1(input: List<String>): Int {
    val startPos = findStartPosition(input)
    var stepCounter = 0
    var previousStep = startPos
    var curStep = startPos
    while (true) {
      val nextStep = makeStep(previousStep, curStep, input)
      previousStep = curStep
      curStep = nextStep
      if (curStep == startPos) {
        break
      }
      stepCounter++
    }
    return stepCounter / 2 + 1
  }

  fun createMapOfVisitedPositions(visitedPositions: MutableSet<Pair<Int, Int>>, gameMapX: Int, gameMapY: Int): MutableList<MutableList<Int>> {
    val mapOfZerosAndOnes = MutableList(gameMapY) { MutableList(gameMapX) { 0 } }
    visitedPositions.forEach { coordinates -> mapOfZerosAndOnes[coordinates.second][coordinates.first] = 1 }
    return mapOfZerosAndOnes
  }

  fun isInsidePolygon(gameMap: List<String>, polygonSides: MutableSet<Pair<Int, Int>>, x: Int, y: Int): Boolean {
    var intersectionCount = 0
    if (polygonSides.contains(Pair(x, y))) {
      return false
    }
    for (offSet in 1..Int.MAX_VALUE) {
      val newCoordinates = Pair(x + offSet, y)
      val isInsideMap = newCoordinates.first < gameMap[0].length
      if (!isInsideMap) {
        break
      }
      if (newCoordinates in polygonSides) {

        val value = gameMap[newCoordinates.second][newCoordinates.first]
        if (value !in setOf('-', 'L', 'J', 'S')) {
          intersectionCount++
        }
      }
    }
    return intersectionCount % 2 == 1
  }

  fun countElementsInsideMap(gameMap: List<String>, visitedPositions: MutableSet<Pair<Int, Int>>): Int {
    val insideOutSideMap = createMapOfVisitedPositions(visitedPositions, gameMap[0].length, gameMap.size)
    var insidePolygonCount = 0
    for ((y, row) in gameMap.withIndex()) {
      for ((x, char) in row.withIndex()) {
        if (isInsidePolygon(gameMap, visitedPositions, x, y)) {
          insidePolygonCount++
          insideOutSideMap[y][x] = 9
        }
      }
    }
    insideOutSideMap.forEachIndexed { index, it ->
      print("${index + 1}: ")
      it.forEach { value ->
        when (value) {
          9 -> print("\u001B[31m$value\u001B[0m")
          0 -> print("\u001B[32m$value\u001B[0m")
          else -> print(value)
        }
      }
      println()
    }
    return insidePolygonCount
  }

  fun part2(input: List<String>): Int {
    val startPos = findStartPosition(input)
    var previousStep = startPos
    var curStep = startPos
    val visitedPositions = mutableSetOf<Pair<Int, Int>>()

    while (true) {
      visitedPositions.add(curStep)
      val nextStep = makeStep(previousStep, curStep, input)
      previousStep = curStep
      curStep = nextStep
      if (curStep == startPos) {
        break
      }
    }

    return countElementsInsideMap(input, visitedPositions)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day10_test")
  println("Part 1 tests: ${part1(testInput)}")
  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 4)
  check(part2(testInput) == 10)

  val input = readInput("Day10")
  part1(input).println()
  part2(input).println()
}
