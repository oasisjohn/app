package com.example.barangay20

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class RequestFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_request, container, false)

        val brgyidbutton = view.findViewById<TextView>(R.id.textBrgyId)
        val cor = view.findViewById<TextView>(R.id.textBrgycor)
        val certif = view.findViewById<TextView>(R.id.textCertif)

        brgyidbutton.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, Barangayid_ScrollingFragment())
            fragmentTransaction.addToBackStack(null)  // Optional: Add to back stack if you want to navigate back
            fragmentTransaction.commit()

            }
        cor.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, Cor_ScrollingFragment())
            fragmentTransaction.addToBackStack(null)  // Optional: Add to back stack if you want to navigate back
            fragmentTransaction.commit()
        }

        certif.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, Certif_ScrollingFragment())
            fragmentTransaction.addToBackStack(null)  // Optional: Add to back stack if you want to navigate back
            fragmentTransaction.commit()
        }

        return view
    }
}
