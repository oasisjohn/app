package com.example.barangay20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val request = view.findViewById<TextView>(R.id.request)
        // Now you can find your ImageView within the inflated view
        val profile = view.findViewById<ImageView>(R.id.user)
        profile.setOnClickListener{
            // If ProfileFragment is a fragment, use a fragment transaction to navigate to it
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, ProfileFragment())
            fragmentTransaction.addToBackStack(null)  // Optional: Add to back stack if you want to navigate back
            fragmentTransaction.commit()

            // If you want to show a Toast
        // Toast.makeText(requireContext(), "User Profile", Toast.LENGTH_LONG).show()
        }
        request.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, RequestFragment())
            fragmentTransaction.addToBackStack(null)  // Optional: Add to back stack if you want to navigate back
            fragmentTransaction.commit()
        }

        // Do something with the 'profile' ImageV iew if needed



        return view
    }



    }





