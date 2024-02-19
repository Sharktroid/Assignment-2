import java.util.Scanner


val FIR_board = FourInARow()

/** The entry main method (the program starts here)  */
fun main() {
    var currentState: Int
    var userInput: String
    val scanner = Scanner(System.`in`)
    FIR_board.printBoard()
    //game loop
    do {
        userInput = scanner.nextLine()
        FIR_board.setMove(GameConstants.BLUE, userInput.toInt())
        FIR_board.setMove(GameConstants.RED, FIR_board.computerMove)
        currentState = FIR_board.checkForWinner()
        FIR_board.printBoard()
    } while (currentState == GameConstants.PLAYING && userInput != "q")
    // repeat if not game-over

    when (currentState) {
        GameConstants.BLUE_WON -> println("Blue Won!")
        GameConstants.RED_WON -> println("Red Won!")
        GameConstants.TIE -> println("It's a tie!")
    }
}
 