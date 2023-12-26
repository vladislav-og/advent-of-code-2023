import kotlin.math.abs

fun main() {

  val moveMap = mapOf(
    'R' to Pair(0, 1),
    'L' to Pair(0, -1),
    'D' to Pair(1, 0),
    'U' to Pair(-1, 0)
  )

  fun part1(input: List<String>): Int {
    val visitedMap = mutableListOf<Pair<Int, Int>>()
    var curPosition = Pair(0, 0) // Pair(y, x)
    var outsidePoints = 0
    for (line in input) {
      val move = line[0]
      val stepsCount = line.split(" ")[1].toInt()
      visitedMap.add(curPosition)
      val nextMove = moveMap[move]!!
      curPosition = Pair(curPosition.first + nextMove.first * stepsCount, curPosition.second + nextMove.second * stepsCount)
      outsidePoints += stepsCount
    }

    val area = visitedMap.mapIndexed { i, it ->
      val j = (i + 1) % visitedMap.size
      it.first * (visitedMap[j].second - it.second)
    }.sum()

    val pointsInside = abs(area) - outsidePoints / 2 + 1

    return pointsInside + outsidePoints
  }

  fun part2(input: List<String>): Int {
    return 0
  }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("Day18_test")
  println("Part 1 tests: ${part1(testInput)}")
//  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 62)
//  check(part2(testInput) == ???)

  val input = readInput("Day18")
  part1(input).println()
  check(part1(input) == 62573)
//  part2(input).println()
//  check(part2(input) == ???)
}
