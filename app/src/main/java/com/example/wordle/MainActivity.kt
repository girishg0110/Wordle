package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    val wordToGuess = FourLetterWordList.getRandomFourLetterWord()

    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var guessesUsed = 0

        val guessField = findViewById<EditText>(R.id.guessField)
        val answerTv = findViewById<TextView>(R.id.answerTv)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val guesses = listOf(findViewById<EditText>(R.id.guess1), findViewById(R.id.guess2), findViewById(R.id.guess3))
        val hints = listOf(findViewById<TextView>(R.id.hint1), findViewById(R.id.hint2), findViewById(R.id.hint3))

        submitButton.setOnClickListener {
            val newGuess = guessField.text
            if (newGuess.length == 4) {
                if (guessesUsed < 3) {
                    guesses[guessesUsed].text = newGuess
                    val result = checkGuess(newGuess.toString())
                    hints[guessesUsed].text = result
                    guessesUsed++
                    if ((result == "0000") or (guessesUsed == 3)) {
                        submitButton.isEnabled = false
                        submitButton.isClickable = false
                        answerTv.text = wordToGuess
                        answerTv.isVisible = true
                    }

                }
            } else Toast.makeText(this, "Four letter guesses!", Toast.LENGTH_SHORT).show()
        }
    }
}