fun main() {

  fun calculateDestination(
    seeds: List<Long>,
    nextSteps: MutableMap<Long, Pair<Long, Long>>
  ) = seeds.map {
    for ((sourceRangeStart, value) in nextSteps.entries) {
      val (destinationRangeStart, rangeLength) = value
      if (it >= sourceRangeStart) {
        if (it < sourceRangeStart + rangeLength) {
          return@map it - sourceRangeStart + destinationRangeStart
        }
      }
    }
    it
  }

  fun calculateDestination(
    seedRanges: List<List<Long>>,
    nextSteps: MutableMap<Long, Pair<Long, Long>>
  ): List<List<Long>> {
    val newSeedRanges = mutableListOf<List<Long>>()
    for (seed in seedRanges) {
      var curSeed = seed
      val nextSeed = mutableListOf<List<Long>>()
      for (step in nextSteps) {
        val (sourceRangeStart, value) = step
        val (destinationRangeStart, rangeLength) = value
        if (curSeed[0] >= sourceRangeStart) {
          if (curSeed[0] + curSeed[1] <= sourceRangeStart + rangeLength) {
            val newSeed = listOf(
              destinationRangeStart - sourceRangeStart + curSeed[0],
              curSeed[1]
            )
            nextSeed.add(newSeed)
            curSeed = emptyList()
            break
          } else if (curSeed[0] > sourceRangeStart + rangeLength) {
            continue
          } else if (curSeed[0] + curSeed[1] > sourceRangeStart + rangeLength) {
            val newSeed = listOf(
              destinationRangeStart - sourceRangeStart + curSeed[0],
              curSeed[1] - (curSeed[0] + curSeed[1] - (sourceRangeStart + rangeLength))
            )
            nextSeed.add(newSeed)
            curSeed = listOf(
              sourceRangeStart + rangeLength,
              curSeed[1] - (curSeed[1] - (curSeed[0] + curSeed[1] - (sourceRangeStart + rangeLength)))
            )
          }
        } else {
          if (curSeed[0] + curSeed[1] <= sourceRangeStart) {
            continue
          } else if (curSeed[0] + curSeed[1] <= sourceRangeStart + rangeLength) {
            val newSeed = listOf(
              destinationRangeStart,
              curSeed[1] - (sourceRangeStart - curSeed[0])
            )
            nextSeed.add(newSeed)
            curSeed = listOf(
              curSeed[0],
              curSeed[1] - (curSeed[1] - (sourceRangeStart - curSeed[0]))
            )
          } else {
            val newSeed = listOf(
              destinationRangeStart,
              curSeed[1] - rangeLength
            )
            nextSeed.add(newSeed)
            curSeed = listOf(
              curSeed[0],
              curSeed[1] - rangeLength,
              curSeed[0] + rangeLength + 1,
              curSeed[1] - rangeLength
            )
          }
        }
      }

      newSeedRanges.addAll(nextSeed)
      if (curSeed.isNotEmpty()) {
        newSeedRanges.add(curSeed)
      }
    }

    return newSeedRanges
  }

  fun part1(input: List<String>): Long {
    var seeds = input.first().split(": ")[1].split(" ").map { it.toLong() }
    var nextSteps = mutableMapOf<Long, Pair<Long, Long>>()
    for (line in input.drop(2)) {
      if (line.isEmpty()) {
        seeds = calculateDestination(seeds, nextSteps)
        nextSteps = mutableMapOf()
        continue
      }
      if (line[0].isDigit()) {
        val (destinationRangeStart, sourceRangeStart, rangeLength) = line.split(" ").map { it.trim().toLong() }
        nextSteps[sourceRangeStart] = Pair(destinationRangeStart, rangeLength)
      }
    }

    return calculateDestination(seeds, nextSteps).min()
  }

  fun part2(input: List<String>): Long {
    var seedRanges = input.first().split(": ")[1].split(" ").map { it.toLong() }.chunked(2)
    var nextSteps = mutableMapOf<Long, Pair<Long, Long>>()
    for (line in input.drop(2)) {
      if (line.isEmpty()) {
        seedRanges = calculateDestination(seedRanges, nextSteps).flatten().chunked(2)
        nextSteps = mutableMapOf()
        continue
      }
      if (line[0].isDigit()) {
        val (destinationRangeStart, sourceRangeStart, rangeLength) = line.split(" ").map { it.trim().toLong() }
        nextSteps[sourceRangeStart] = Pair(destinationRangeStart, rangeLength)
      }
    }
    seedRanges = calculateDestination(seedRanges, nextSteps).flatten().chunked(2)
    return seedRanges.map { it.first() }.min()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day05_test")
  println("Part 1 tests: ${part1(testInput)}")
  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 35L)
  check(part2(testInput) == 46L)

  val input = readInput("Day05")
  part1(input).println()
  check(part1(input) == 157211394L)
  part2(input).println()
  check(part2(input) == 50855035L)
}
