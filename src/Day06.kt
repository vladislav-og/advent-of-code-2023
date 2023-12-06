fun main() {

  fun readRaces(input: List<String>): List<Pair<Long, Long>> {
    val times = Regex("[0-9]+").findAll(input[0]).toList().map { it.value.toLong() }
    val distances = Regex("[0-9]+").findAll(input[1]).toList().map { it.value.toLong() }
    return times.zip(distances)
  }

  fun readRacesMergedTogether(input: List<String>): Pair<Long, Long> {
    val time = input[0].split(":")[1].replace(" ", "").toLong()
    val distance = input[1].split(":")[1].replace(" ", "").toLong()
    return Pair(time, distance)
  }

  fun calculateWinningRaces(race: Pair<Long, Long>): Long {
    var wins = 0
    val (time, bestDistance) = race
    for (i in 0 until time) {
      val curDistance = i * (time - i)
      if (curDistance > bestDistance) {
        wins++
      }
    }
    return wins.toLong()
  }

  fun part1(input: List<String>): Long {
    val races = readRaces(input)
    var score = 0L
    for (race in races) {
      val winningRacesCount = calculateWinningRaces(race)
      if (score == 0L) {
        score = winningRacesCount
      } else {
        score *= winningRacesCount
      }
    }
    return score
  }

  fun part2(input: List<String>): Long {
    val race = readRacesMergedTogether(input)
    val winningRacesCount = calculateWinningRaces(race)
    return winningRacesCount
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day06_test")
  println("Part 1 tests: ${part1(testInput)}")
  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 288L)
  check(part2(testInput) == 71503L)

  val input = readInput("Day06")
  part1(input).println()
    check(part1(input) == 1413720L)
    part2(input).println()
    check(part2(input) == 30565288L)
}
