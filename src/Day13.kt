fun main() {

  fun getReflections(rows: List<List<Char>>): Int {
    val reflections = mutableListOf<List<Char>>()
    for ((i, row) in rows.withIndex()) {
      reflections.add(row)
      if (reflections.size > rows.size / 2) {
        val remainingRows = rows.size - i - 1
        val isReflectionMirrored = reflections.size != rows.size && reflections.subList((i + 1 - remainingRows), i + 1) == rows.subList(i + 1, rows.size).reversed()
        if (isReflectionMirrored) {
          return reflections.size
        }
      } else {
        val isReflectionMirrored = reflections.reversed() == rows.subList(i + 1, i + reflections.size + 1)
        if (isReflectionMirrored) {
          return reflections.size
        }
      }
    }
    return 0
  }

  fun getReflectionRow(mapRows: List<List<Char>>): Int {
    return getReflections(mapRows)
  }

  fun getReflectionCol(mapCols: List<List<Char>>): Int {
    return getReflections(mapCols)
  }

  fun getScore(
    mapRows: MutableList<MutableList<Char>>, mapCols: MutableList<MutableList<Char>>, score: Int
  ): Int {
    var score1 = score
    val reflectionRowNr = getReflectionRow(mapRows)
    val reflectionColNr = getReflectionCol(mapCols)
    score1 += reflectionColNr + 100 * reflectionRowNr
    return score1
  }

  fun part1(input: List<String>): Int {
    val mapRows = mutableListOf<MutableList<Char>>()
    val mapCols = mutableListOf<MutableList<Char>>()
    var rowIndex = 0
    var colIndex = 0
    var score = 0
    for ((index, row) in input.withIndex()) {
      if (row.isNotBlank()) {
        mapRows.add(row.toMutableList())
        for (i in row.indices) {
          if (mapCols.size <= i) {
            mapCols.add(mutableListOf())
          }
          mapCols[i].add(row[i])
        }
        colIndex++
        rowIndex++

        if (index == input.size - 1) {
          score = getScore(mapRows, mapCols, score)
          mapRows.clear()
          mapCols.clear()
          rowIndex = 0
          colIndex = 0
        }

      } else {
        score = getScore(mapRows, mapCols, score)
        rowIndex = 0
        colIndex = 0
        mapRows.clear()
        mapCols.clear()
      }
    }
    return score
  }

  fun part2(input: List<String>): Long {
    return 0
  }

// test if implementation meets criteria from the description, like:
  val testInput = readInput("Day13_test")
  println("Part 1 tests: ${part1(testInput)}")
//  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 405)
//  check(part2(testInput) == 400)

  val input = readInput("Day13")
  part1(input).println()
  check(part1(input) == 30487)
//  part2(input).println()
//  check(part2(input) == ???)

}
