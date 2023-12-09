fun main() {

  fun createDatasetForReport(report: String): MutableList<List<Int>> {
    val datasets = mutableListOf<List<Int>>()
    var curDataset = report.split(" ").map { it.toInt() }
    datasets.add(curDataset)
    while (!curDataset.all { it == 0 }) {
      val nextDataset = mutableListOf<Int>()
      for ((i, num) in curDataset.drop(1).withIndex()) {
        nextDataset.add(num - curDataset[i])
      }
      datasets.add(nextDataset)
      curDataset = nextDataset
    }
    return datasets
  }

  fun calcReportScore(report: String): Int {
    val datasets = createDatasetForReport(report)

    var score = 0
    var previousNum = 0
    for (dataset in datasets.reversed().drop(1)) {
      val lastNum = dataset.last()
      score = lastNum + previousNum
      previousNum = score
    }

    return score
  }

  fun calcReportScoreForBackwardMoving(report: String): Int {
    val datasets = createDatasetForReport(report)
    val datasetsReversed = datasets.map { it.reversed() }

    var score = 0
    var previousNum = 0
    for (dataset in datasetsReversed.reversed().drop(1)) {
      val lastNum = dataset.last()
      score = lastNum - previousNum
      previousNum = score
    }

    return score
  }

  fun part1(input: List<String>): Int {
    var score = 0
    for (report in input) {
      score += calcReportScore(report)
    }
   return score
  }

  fun part2(input: List<String>): Int {
    var score = 0
    for (report in input) {
      score += calcReportScoreForBackwardMoving(report)
    }
    return score
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day09_test")
  println("Part 1 tests: ${part1(testInput)}")
  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 114)
  check(part2(testInput) == 2)

  val input = readInput("Day09")
  part1(input).println()
  part2(input).println()
}
