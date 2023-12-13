import java.math.BigInteger
import kotlin.math.abs

fun main() {

  fun getGalaxyTotalSteps(input: List<String>, galaxyExpansionRate: Int): Long {
    var countGalaxies = 0
    val galaxies = mutableListOf<Pair<Int, Int>>()
    val emptyRows = input.first().indices.toMutableSet()
    val emptyCols = input.indices.toMutableSet()

    input.forEachIndexed { y, row ->
      row.forEachIndexed { x, char ->
        if (char == '#') {
          emptyCols.remove(x)
          emptyRows.remove(y)
          countGalaxies++
          galaxies.add(Pair(y, x))
        }
      }
    }

    var distanceScore = BigInteger.ZERO
    for ((i, galaxy) in galaxies.withIndex()) {
      for (targetGalaxy in galaxies.subList(i + 1, galaxies.size)) {
        val distance = abs(galaxy.first - targetGalaxy.first) + abs(galaxy.second - targetGalaxy.second)
        val emptyColsBetween = emptyCols.count { (it > galaxy.second && it < targetGalaxy.second) || (it < galaxy.second && it > targetGalaxy.second) }
        val emptyRowsBetween = emptyRows.count { it > galaxy.first && it < targetGalaxy.first }
        distanceScore += (distance + galaxyExpansionRate * (emptyColsBetween + emptyRowsBetween)).toBigInteger()
      }
    }
    return distanceScore.toLong()
  }

  fun part1(input: List<String>): Int {
    val galaxyExpansionRate = 1

    return getGalaxyTotalSteps(input, galaxyExpansionRate).toInt()
  }

  fun part2(input: List<String>): Long {
    val galaxyExpansionRate = 999999

    return getGalaxyTotalSteps(input, galaxyExpansionRate)
  }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("Day11_test")
//  println("Part 1 tests: ${part1(testInput)}")
//  println("Part 2 tests: ${part2(testInput)}")
//  check(part1(testInput) == 374)
//  check(part2(testInput) == 374L)

  val input = readInput("Day11")
  part1(input).println()
  check(part1(input) == 10494813)
  part2(input).println()
  check(part2(input) == 840988812853)

// low 1774635555
}
