/**
 * TicTacToe class implements the interface
 * @author relkharboutly
 * @date 2/12/2022
 */
class FourInARow
/**
 * clear board and set current player
 */
    : IGame {
    // game board in 2D array initialized to zeros
    private val board = Array(GameConstants.ROWS) { IntArray(GameConstants.COLS){0} }

    override fun clearBoard() {
        for (row in 0..< GameConstants.ROWS) {
            for (col in 0..< GameConstants.COLS) {
                board[row][col] = GameConstants.EMPTY
            }
        }
    }

    override fun  setMove(player: Int, location: Int) {
        val row: Int = location/GameConstants.COLS
        val col: Int = location % GameConstants.COLS
        board[row][col] = player
    }

    override val computerMove: Int
        get() {
            // Checks if an adjacent tile is red. Returns true if there is
            fun checkAdjacentCells(x: Int, y: Int): Boolean {
                fun checkCell(x: Int, y: Int): Boolean {
                    if ((x < 0) or (y < 0) or (x >= GameConstants.COLS) or (y >= GameConstants.ROWS)) {
                        return false
                    }
                    return board[x][y] == GameConstants.RED
                }

                val cells = arrayOf(arrayOf(-1, -1), arrayOf(0, -1), arrayOf(1, -1), arrayOf(1, 0), arrayOf(1, 1), arrayOf(0, 1), arrayOf(-1, 1), arrayOf(-1, 0))
                for (index in 0..< 8) {
                    val cell = cells[index]
                    if (checkCell(x + cell[0], y + cell[1])) {
                        return true
                    }
                }
                return false
            }

            var firstValidCell: Int = -1
            for (row in 0..< GameConstants.ROWS) {
                for (col in 0..< GameConstants.COLS) {
                    if (board[row][col] == GameConstants.EMPTY) {
                        val currentCell = row * GameConstants.ROWS + col
                        if (firstValidCell == -1) {
                            firstValidCell = currentCell
                        }
                        val adjacentCell = checkAdjacentCells(row, col)
                        if (adjacentCell) {
                            return currentCell
                        }
                    }
                }
            }
            return firstValidCell
        }

    override fun checkForWinner(): Int {
        fun playerCheck(cell: Int): Int {
            return when (cell) {
                GameConstants.BLUE -> GameConstants.BLUE_WON
                GameConstants.RED -> GameConstants.RED_WON
                else -> GameConstants.PLAYING
            }
        }
        fun offsetCheck(x: Int, y: Int, hor: Boolean, vert: Boolean, invert: Boolean = false): Boolean {
            val horMulti = when (hor) {
                true -> when(invert) {
                    true -> -1
                    false -> 1
                }
                false -> 0
            }
            val vertMulti = when (vert) {
                true -> 1
                false -> 0
            }
            val xOffset = when (invert) {
                true -> 3
                false -> 0
            }
            if ((hor and (x >= GameConstants.ROWS - 3)) or (vert and (y >= GameConstants.COLS - 3))) {
                return false
            }
            val cells = Array(4) {index: Int -> board[x + xOffset + index * horMulti][y + index * vertMulti]}
            return ((cells[0] == cells[1]) and (cells[1] == cells[2]) and (cells[2] == cells[3])) and (cells[0] != 0)
        }
        var isPlaying = false;
        for (row in 0..< GameConstants.ROWS) {
            for (col in 0..< GameConstants.COLS) {
                if (board[row][col] == GameConstants.EMPTY) {
                    isPlaying = true;
                }
                if (offsetCheck(row, col, hor = false, vert = true)) {
                    return playerCheck(board[row][col])
                }
                if (offsetCheck(row, col, hor = true, vert = true)) {
                    return playerCheck(board[row][col])
                }
                if (offsetCheck(row, col, hor = true, vert = true, true)) {
                    return playerCheck(board[row + 3][col])
                }
                if (offsetCheck(row, col, hor = true, vert = false)) {
                    return playerCheck(board[row][col])
                }
            }
        }
        println(isPlaying)
        return when (isPlaying) {
            true -> GameConstants.PLAYING
            false -> GameConstants.TIE
        }
    }

    /**
     * Print the game board
     */
    fun printBoard() {
        for (row in 0..< GameConstants.ROWS) {
            for (col in 0..< GameConstants.COLS) {
                printCell(board[row][col]) // print each of the cells
                if (col != GameConstants.COLS - 1) {
                    print("|") // print vertical partition
                }
            }
            println()
            if (row != GameConstants.ROWS - 1) {
                println("-----------") // print horizontal partition
            }
        }
        println()
    }

    /**
     * Print a cell with the specified "content"
     * @param content either BLUE, RED or EMPTY
     */
    private fun printCell(content: Int) {
        when (content) {
            GameConstants.EMPTY -> print("   ")
            GameConstants.BLUE -> print(" B ")
            GameConstants.RED -> print(" R ")
        }
    }
}

