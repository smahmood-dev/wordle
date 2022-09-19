package com.example.wordle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.wordle.FourLetterWordList
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var counter = 1
        var wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        // Import dynamic UI
        val guessButton = findViewById<Button>(R.id.guessButton)
        val guessEntry = findViewById<EditText>(R.id.guessEntry)
        val guess1Input = findViewById<TextView>(R.id.guess1Input)
        val guess1Check = findViewById<TextView>(R.id.guess1Check)
        val guess2Input = findViewById<TextView>(R.id.guess2Input)
        val guess2Check = findViewById<TextView>(R.id.guess2Check)
        val guess3Input = findViewById<TextView>(R.id.guess3Input)
        val guess3Check = findViewById<TextView>(R.id.guess3Check)
        val revealAnswer = findViewById<TextView>(R.id.revealAnswer)

        // Hide UI elements
        guess1Input.visibility = View.GONE
        guess1Check.visibility = View.GONE
        guess2Input.visibility = View.GONE
        guess2Check.visibility = View.GONE
        guess3Input.visibility = View.GONE
        guess3Check.visibility = View.GONE
        revealAnswer.visibility = View.GONE

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
        fun checkGuess(guess: String) : String {
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

        guessButton.setOnClickListener {
            // Hides keyboard after user submits guess.
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }

            // Core game loop
            when (counter) {
                1 -> {
                    var userInput = guessEntry.text.toString().uppercase()
                    guessEntry.setText("") // Clears user input after user submits guess.
                    guess1Input.text = userInput
                    guess1Input.visibility = View.VISIBLE
                    guess1Check.text = checkGuess(userInput)
                    guess1Check.visibility = View.VISIBLE
                    counter++
                }
                2 -> {
                    var userInput = guessEntry.text.toString().uppercase()
                    guessEntry.setText("")
                    guess2Input.text = userInput
                    guess2Input.visibility = View.VISIBLE
                    guess2Check.text = checkGuess(userInput)
                    guess2Check.visibility = View.VISIBLE
                    counter++
                }
                else -> {
                    var userInput = guessEntry.text.toString().uppercase()
                    guessEntry.setText("")
                    guess3Input.text = userInput
                    guess3Input.visibility = View.VISIBLE
                    guess3Check.text = checkGuess(userInput)
                    guess3Check.visibility = View.VISIBLE
                    counter++

                    // Must convey that the game is over.
                    guessButton.isEnabled = false
                    revealAnswer.text = wordToGuess
                    revealAnswer.visibility = View.VISIBLE

                }
            }
        }
    }
}