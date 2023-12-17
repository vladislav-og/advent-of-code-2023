fun main() {

  fun calculateTotalWeight(newGameMap: List<String>) = newGameMap.mapIndexed { i, it ->
    it.sumOf { char ->
      if (char == 'O') {
        newGameMap.size - i
      } else {
        0
      }
    }
  }.sum()

  fun tiltWithSortingWest(gameMap: MutableList<String>) {
    for ((i, gameMapRow) in gameMap.withIndex()) {
      val splitedRanges = gameMapRow.split('#')
      val sortedSplitedRanges = splitedRanges.map{
        it.toCharArray().sortedBy { char ->
          if (char == 'O') {
            0
          } else {
            1
          }
        }.joinToString("")
      }.joinToString("#")
      gameMap[i] = sortedSplitedRanges
    }
  }

  fun tiltWithSortingEast(gameMap: MutableList<String>) {
    for ((i, gameMapRow) in gameMap.withIndex()) {
      val splitedRanges = gameMapRow.split('#')
      val sortedSplitedRanges = splitedRanges.map{
        it.toCharArray().sortedBy { char ->
          if (char == 'O') {
            1
          } else {
            0
          }
        }.joinToString("")
      }.joinToString("#")
      gameMap[i] = sortedSplitedRanges
    }
  }

  fun rotateGameMapClockwise(gameMap: MutableList<String>): MutableList<String> {
    return gameMap[0].indices.map { col ->
      gameMap.indices.reversed().map { row ->
        gameMap[row][col]
      }.joinToString("")
    }.toMutableList()
  }

  fun rotateGameMapCounterClockwise(gameMap: MutableList<String>): MutableList<String> {
    return gameMap[0].indices.reversed().map { col ->
      gameMap.indices.map { row ->
        gameMap[row][col]
      }.joinToString("")
    }.toMutableList()
  }

  fun part1(input: List<String>): Int {
    var newGameMap = input.map { it }.toMutableList()
    newGameMap = rotateGameMapCounterClockwise(newGameMap)
    tiltWithSortingWest(newGameMap)

    val rotateGameMapCounterClockwise = rotateGameMapClockwise(newGameMap)
    return calculateTotalWeight(rotateGameMapCounterClockwise)
  }

  fun part2(input: List<String>): Int {
    var newGameMap = input.map { it }.toMutableList()
    val memoRySet = mutableListOf<List<String>>()
    for (i in 0..<1000000000) {
      newGameMap = rotateGameMapCounterClockwise(newGameMap)
      tiltWithSortingWest(newGameMap)
      newGameMap = rotateGameMapCounterClockwise(newGameMap)
      tiltWithSortingEast(newGameMap)
      newGameMap = rotateGameMapCounterClockwise(newGameMap)
      tiltWithSortingWest(newGameMap)
      newGameMap = rotateGameMapCounterClockwise(newGameMap)
      tiltWithSortingEast(newGameMap)
      if (memoRySet.contains(newGameMap)) {
        val indexOfRepeatStart = memoRySet.indexOf(newGameMap)
        val i1 = (999999999 - indexOfRepeatStart)  % (i - indexOfRepeatStart)
        return calculateTotalWeight(memoRySet[i1 + indexOfRepeatStart])
      }
      memoRySet.add(newGameMap)
    }

    return calculateTotalWeight(newGameMap)
  }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("Day14_test")
  println("Part 1 tests: ${part1(testInput)}")
  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 136)
  check(part2(testInput) == 64)

  val input = readInput("Day14")
  part1(input).println()
  check(part1(input) == 108826)
  part2(input).println()
  check(part2(input) == 99291)

}
