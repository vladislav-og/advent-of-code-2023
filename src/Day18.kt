import kotlin.math.abs

fun main() {

  val moveMaps = mapOf(
    'R' to Pair(0, 1),
    'L' to Pair(0, -1),
    'D' to Pair(1, 0),
    'U' to Pair(-1, 0)
  )

  val moveDirections = mapOf(
    '0' to 'R',
    '1' to 'D',
    '2' to 'L',
    '3' to 'U'
  )

  fun getArea(visitedMap: MutableList<Pair<Long, Long>>) = visitedMap.mapIndexed { i, it ->
    val j = (i + 1) % visitedMap.size
    it.first * (visitedMap[j].second - it.second)
  }.sum()

  fun part1(input: List<String>): Int {
    val visitedMap = mutableListOf<Pair<Long, Long>>()
    var curPosition = Pair(0L, 0L) // Pair(y, x)
    var outsidePoints = 0L
    for (line in input) {
      val move = line[0]
      val stepsCount = line.split(" ")[1].toInt()
      visitedMap.add(curPosition)
      val nextMove = moveMaps[move]!!
      curPosition = Pair(curPosition.first + nextMove.first * stepsCount, curPosition.second + nextMove.second * stepsCount)
      outsidePoints += stepsCount
    }

    val area = getArea(visitedMap)

    val pointsInside = abs(area) - outsidePoints / 2 + 1

    return pointsInside.toInt() + outsidePoints.toInt()
  }

  fun part2(input: List<String>): Long {
    val visitedMap = mutableListOf<Pair<Long, Long>>()
    var curPosition = Pair(0L, 0L) // Pair(y, x)
    var outsidePoints = 0L
    for (line in input) {
      visitedMap.add(curPosition)
      val move = line.split(" ")[2]
      val stepsCountHex = move.substring(2, 7)
      val stepsCount = stepsCountHex.toLong(16)
      val digDirection = moveDirections[move[move.length - 2]]
      val nextMove = moveMaps[digDirection]!!

      curPosition = Pair(curPosition.first + nextMove.first * stepsCount, curPosition.second + nextMove.second * stepsCount)
      outsidePoints += stepsCount
    }

    val area = getArea(visitedMap)

    val pointsInside = abs(area) - outsidePoints / 2 + 1

    return pointsInside + outsidePoints
  }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("Day18_test")
//  println("Part 1 tests: ${part1(testInput)}")
  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 62)
  check(part2(testInput) == 952408144115)

  val input = readInput("Day18")
  part1(input).println()
  check(part1(input) == 62573)
  part2(input).println()
  check(part2(input) == 54662804037719)
}
