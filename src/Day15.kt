fun main() {

  fun part1(input: List<String>): Int {
    var totalScore = 0
    for (line in input) {
      val commands = line.split(",")
      for (command in commands) {
        var curScore = 0
        for (char in command) {
          curScore += char.code
          curScore = (curScore * 17) % 256
        }
        totalScore += curScore
      }
    }
    return totalScore
  }

  fun part2(input: List<String>): Int {
    return 0
  }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("Day15_test")
  println("Part 1 tests: ${part1(testInput)}")
//  println("Part 2 tests: ${part2(testInput)}")
//  check(part1(testInput) == 1320)
//  check(part2(testInput) == ???)

  val input = readInput("Day15")
  part1(input).println()
//  check(part1(input) == 512950)
//  part2(input).println()
//  check(part2(input) == ???)

  // low 13003
}
