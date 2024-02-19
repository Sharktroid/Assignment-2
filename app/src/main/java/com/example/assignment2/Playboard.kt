package com.example.assignment2

import FourInARow
import GameConstants
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

/**
 * @author Calvin Pancavage
 */
class Playboard : Fragment() {
    private lateinit var squares: Array<ImageButton>
    private var board = FourInARow()
    private var currentTurn = GameConstants.BLUE
    private lateinit var playerName: String
    private lateinit var turnView: TextView
    private lateinit var resetButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_playboard, container, false)
        // Setting variables
        playerName = PlayboardArgs.fromBundle(requireArguments()).name
        turnView = view.findViewById(R.id.turnView)
        updateTurnView()
        val usernameView = view.findViewById<TextView>(R.id.usernameView)
        usernameView.text = playerName
        resetButton = view.findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
             reset()
        }

        // Saves all of the squares in an array
        val ids = arrayOf(
            R.id.tile0,  R.id.tile1,  R.id.tile2,  R.id.tile3,  R.id.tile4,  R.id.tile5,
            R.id.tile6,  R.id.tile7,  R.id.tile8,  R.id.tile9,  R.id.tile10, R.id.tile11,
            R.id.tile12, R.id.tile13, R.id.tile14, R.id.tile15, R.id.tile16, R.id.tile17,
            R.id.tile18, R.id.tile19, R.id.tile20, R.id.tile21, R.id.tile22, R.id.tile23,
            R.id.tile24, R.id.tile25, R.id.tile26, R.id.tile27, R.id.tile28, R.id.tile29,
            R.id.tile30, R.id.tile31, R.id.tile32, R.id.tile33, R.id.tile34, R.id.tile35
        )
        squares = Array(ids.size) {index -> view.findViewById(ids[index])}
        for (index in squares.indices) {
            squares[index].setOnClickListener {
                squareSelected(GameConstants.BLUE, index)
            }
        }

        return view
    }

    private fun squareSelected(player: Int, num: Int) {
        // Called when a square is selected
        val button = squares[num]
        board.setMove(player, num)
        button.setImageResource(when (player) {
            GameConstants.BLUE -> R.drawable.blue
            GameConstants.RED -> R.drawable.red
            else -> R.drawable.empty
        })
        button.isEnabled = false
        checkState()
    }

    private fun checkState() {
        // Checks if either player has won
        if (board.checkForWinner() != GameConstants.PLAYING) {
            for (square in squares) {
                square.isEnabled = false
            }
            turnView.text = when (board.checkForWinner()) {
                GameConstants.BLUE_WON -> String.format("%s wins!", playerName)
                GameConstants.RED_WON -> "The AI won!"
                else -> "It's a draw!"
            }
            resetButton.isEnabled = true
        }
        else {
            changeTurn()
            if (currentTurn == GameConstants.RED) {
                // Automatically makes the move for the AI
                squareSelected(GameConstants.RED, board.computerMove)
            }
        }
    }

    private fun changeTurn() {
        // Changes the current turn
        currentTurn = when (currentTurn) {
            GameConstants.BLUE -> GameConstants.RED
            else -> GameConstants.BLUE
        }
        updateTurnView()
    }

    private fun updateTurnView() {
        // Updates the TurnView
        turnView.text = when (currentTurn) {
            GameConstants.BLUE -> String.format("%s's turn", playerName)
            GameConstants.RED -> "The AI's turn"
            else -> "ERROR"
        }
    }

    private fun reset() {
        // Resets the game
        board.clearBoard()
        for (square in squares) {
            square.setImageResource(R.drawable.empty)
            square.isEnabled = true
        }
        resetButton.isEnabled = false
        currentTurn = GameConstants.BLUE
        updateTurnView()
    }
}