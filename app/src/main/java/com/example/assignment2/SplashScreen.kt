package com.example.assignment2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.navigation.findNavController

/**
 * @author Calvin Pancavage
 */

class SplashScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        val button = view.findViewById<Button>(R.id.startButton)
        val nameSelection = view.findViewById<EditText>(R.id.nameSelection)

        button.setOnClickListener {
            // Has the button switch to the splash screen while sending over the player name
            val name = nameSelection.text.toString()
            val action = SplashScreenDirections.actionSplashScreenToPlayboard(name)

            view.findNavController().navigate(action)
        }
        return view
    }
}