package com.example.barangay20

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject


class Certif_ScrollingFragment : Fragment() {
    private fun showSuccessAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        // Set the title and message for success
        alertDialogBuilder.setTitle("Success")
        alertDialogBuilder.setMessage("Data added successfully!")

        // Set the positive button
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            // Navigate to the next page
            val registerIntent = Intent(requireContext(), RequestFragment::class.java)
            startActivity(registerIntent)

            // Finish this fragment to prevent going back to it
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()


            dialog.dismiss()
        }

        // Create and show the AlertDialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var barangayclearance = inflater.inflate(R.layout.fragment_certif__scrolling, container, false)
        val textcertiname: TextInputEditText =
            barangayclearance.findViewById(R.id.textcertiname)
        val textcertiAddress: TextInputEditText =
            barangayclearance.findViewById(R.id.textcertiAddress)
        val textcertiRes: TextInputEditText =
            barangayclearance.findViewById(R.id.textcertiRes)
        val textcertiContact: TextInputEditText =
            barangayclearance.findViewById(R.id.textcertiContact)
        val textcertiPurpose: TextInputEditText =
            barangayclearance.findViewById(R.id.textcertiPurpose)
        val textCertiRequest: TextInputEditText =
            barangayclearance.findViewById(R.id.textCertiRequest)




        val submitButton: Button = barangayclearance.findViewById(R.id.submit)
        // Set OnClickListener for the Button
        submitButton.setOnClickListener {
            val name = textcertiname.text.toString()
            val address = textcertiAddress.text.toString()
            val yrs_res = textcertiRes.text.toString()
            val contact = textcertiContact.text.toString()
            val purpose = textcertiPurpose.text.toString()
            val Request =  textCertiRequest.text.toString()

            // Make a Volley request
            val url = "https://barangaymngmt.bsisakalam.com/barangay_clearance.php"
            val params = HashMap<String, String>()
            params["name"] = name
            params["address"] = address
            params["yrs_res"] = yrs_res
            params["contact_no"] = contact
            params["purpose"] = purpose
            params["request"] = Request
            val jsonObjectRequest = JsonObjectRequest(
                com.android.volley.Request.Method.POST, url, (params as Map<*, *>?)?.let { it1 -> JSONObject(it1) },
                { response ->
                    val status = response.optBoolean("status")
                    val message = response.optString("message")

                    if (status) {
                        Toast.makeText(requireContext(), "Added successfully: $message", Toast.LENGTH_SHORT).show()
                        showSuccessAlertDialog()
                    } else {
                        Toast.makeText(requireContext(), "Failed to add: $message", Toast.LENGTH_SHORT).show()
                    }
                },
                { error ->
                    Toast.makeText(requireContext(), "Request error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            )
            Volley.newRequestQueue(requireContext()).add(jsonObjectRequest)
        }


        return barangayclearance
    }
}