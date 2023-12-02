fun main() {

  val gameCubesMap = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
  )

  fun getMaxCubeCountPerRun(input: String): Map<String, Int> {
    val runCubeCountMap = mutableMapOf<String, Int>()
    for (record in input.split(";")) {
      for (cubeCount in record.split(",")) {
        val (count, color) = cubeCount.trim().split(" ")
        val curMaxCount = runCubeCountMap.getOrDefault(color, 0)
        if (count.toInt() > curMaxCount) {
          runCubeCountMap[color] = count.toInt()
        }
      }
    }
    return runCubeCountMap
  }

  fun part1(input: List<String>): Int {
    var gameScore = 0
    for ((i, value) in input.withIndex()) {
      gameScore += i + 1
      val moves = value.split(":")[1]
      val currentRunMaxCubes = getMaxCubeCountPerRun(moves)
      for (color in gameCubesMap.keys) {
        if (gameCubesMap[color]!! < currentRunMaxCubes[color]!!) {
          gameScore -= i + 1
          break
        }
      }
    }
    return gameScore
  }

  fun part2(input: List<String>): Int {
    var gameScore = 0
    for (value in input) {
      val moves = value.split(":")[1]
      val currentRunMaxCubes = getMaxCubeCountPerRun(moves)
      val curRunTotalScore = currentRunMaxCubes.values.reduce { acc, i -> acc * i }
      gameScore += curRunTotalScore
    }
    return gameScore
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day02_test")
  println("Part 1 tests: ${part1(testInput)}")
  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 8)
  check(part2(testInput) == 2286)

  val input = readInput("Day02")
  part1(input).println()
  part2(input).println()
}
