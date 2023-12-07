fun main() {

  val camelPokerRanking = mapOf(
    '2' to 2,
    '3' to 3,
    '4' to 4,
    '5' to 5,
    '6' to 6,
    '7' to 7,
    '8' to 8,
    '9' to 9,
    'T' to 10,
    'J' to 11,
    'Q' to 12,
    'K' to 13,
    'A' to 14
  )

  val camelPokerWithJokerRanking = mapOf(
    'J' to 1,
    '2' to 2,
    '3' to 3,
    '4' to 4,
    '5' to 5,
    '6' to 6,
    '7' to 7,
    '8' to 8,
    '9' to 9,
    'T' to 10,
    'Q' to 11,
    'K' to 12,
    'A' to 13
  )

  val handStrength = mapOf(
    "Five of a Kind" to 7,
    "Four of a Kind" to 6,
    "Full House" to 5,
    "Three of a Kind" to 4,
    "Two Pairs" to 3,
    "One Pair" to 2,
    "High Card" to 1
  )

  fun getBoardStrength(duplicateCards: MutableMap<Char, Int>): Int {
    if (duplicateCards.size == 4) {
      return handStrength["One Pair"]!!
    }
    if (duplicateCards.size == 5) {
      return handStrength["High Card"]!!
    }
    if (duplicateCards.keys.size == 2) {
      if (duplicateCards.values.contains(4)) {
        return handStrength["Four of a Kind"]!!
      }
      if (duplicateCards.values.contains(3)) {
        return handStrength["Full House"]!!
      }
    }
    if (duplicateCards.size == 3) {
      if (duplicateCards.values.contains(2)) {
        return handStrength["Two Pairs"]!!
      }
      if (duplicateCards.values.contains(3)) {
        return handStrength["Three of a Kind"]!!
      }
    }
    return handStrength["Five of a Kind"]!!
  }

  fun calculateBoardStrength(board: String): Int {
    val duplicateCards = mutableMapOf<Char, Int>()
    for (card in board) {
      if (duplicateCards.containsKey(card)) {
        duplicateCards[card] = duplicateCards[card]!! + 1
      } else {
        duplicateCards[card] = 1
      }
    }

    return getBoardStrength(duplicateCards)
  }

  fun calculateBoardStrengthWithJoker(board: String): Int {
    val duplicateCards = mutableMapOf<Char, Int>()
    for (card in board) {
      if (duplicateCards.containsKey(card)) {
        duplicateCards[card] = duplicateCards[card]!! + 1
      } else {
        duplicateCards[card] = 1
      }
    }
    if (duplicateCards.containsKey('J')) {
      val jokerFrequency = duplicateCards.remove('J')!!
      if (jokerFrequency == 5) {
        return handStrength["Five of a Kind"]!!
      }
      val highestFrequencyCard = duplicateCards.toList().maxBy { it.second }
      duplicateCards[highestFrequencyCard.first] = highestFrequencyCard.second + jokerFrequency
    }

    return getBoardStrength(duplicateCards)

  }

  fun calculatePoints(
    orderedBoards: List<Pair<String, Int>>,
    boardBetsMap: MutableMap<String, Int>
  ): Int {
    var points = 0
    for ((i, board) in orderedBoards.withIndex()) {
      points += (i + 1) * boardBetsMap[board.first]!!
    }
    return points
  }

  fun sortBoards(boardStrengths: MutableMap<String, Int>, ranking: Map<Char, Int>) = boardStrengths
    .toList()
    .sortedWith(
      compareBy(
        { it.second },
        { ranking[it.first[0]] },
        { ranking[it.first[1]] },
        { ranking[it.first[2]] },
        { ranking[it.first[3]] },
        { ranking[it.first[4]] })
    )
    .toList()

  fun part1(input: List<String>): Int {
    val boardStrengths = mutableMapOf<String, Int>()
    val boardBetsMap = mutableMapOf<String, Int>()
    for (game in input) {
      val (board, betSize) = game.split(" ")
      val boardStrength = calculateBoardStrength(board)
      boardStrengths[board] = boardStrength
      boardBetsMap[board] = betSize.toInt()
    }
    val orderedBoards = sortBoards(boardStrengths, camelPokerRanking)

    return calculatePoints(orderedBoards, boardBetsMap)
  }

  fun part2(input: List<String>): Int {
    val boardStrengths = mutableMapOf<String, Int>()
    val boardBetsMap = mutableMapOf<String, Int>()
    for (game in input) {
      val (board, betSize) = game.split(" ")
      val boardStrength = calculateBoardStrengthWithJoker(board)
      boardStrengths[board] = boardStrength
      boardBetsMap[board] = betSize.toInt()
    }
    val orderedBoards = sortBoards(boardStrengths, camelPokerWithJokerRanking)

    return calculatePoints(orderedBoards, boardBetsMap)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day07_test")
  println("Part 1 tests: ${part1(testInput)}")
  println("Part 2 tests: ${part2(testInput)}")
  check(part1(testInput) == 6440)
  check(part2(testInput) == 5905)

  val input = readInput("Day07")
  part1(input).println()
    check(part1(input) == 251216224)
    part2(input).println()
    check(part2(input) == 250825971)
}
