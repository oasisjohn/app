package com.example.barangay20

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class BusinessScrollingFragment : Fragment() {
    private fun showSuccessAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        // Set the title and message for success
        alertDialogBuilder.setTitle("Success")
        alertDialogBuilder.setMessage("Data added successfully!")

        // Set the positive button
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, RequestFragment())
            fragmentTransaction.addToBackStack(null)  // Optional: Add to back stack if you want to navigate back
            fragmentTransaction.commit()
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
        var businessFragment = inflater.inflate(R.layout.fragment_business_scrolling, container, false)


        val textBusinessName: TextInputEditText =
            businessFragment.findViewById(R.id.textBusinessName)
        val textBusinessOwner: TextInputEditText =
            businessFragment.findViewById(R.id.textBusinessOwner)
        val textBusinessKind: TextInputEditText =
            businessFragment.findViewById(R.id.textBusinessKiind)
        val textBusinessYrs: TextInputEditText =
            businessFragment.findViewById(R.id.textBusinessYrs)
        val textBusinessContact: TextInputEditText =
            businessFragment.findViewById(R.id.textBusinessContact)
        val textBusinessPurpose: TextInputEditText =
            businessFragment.findViewById(R.id.textBusinessPurpose)

        val submitButton: Button = businessFragment.findViewById(R.id.submit)

        // Set OnClickListener for the Button
        submitButton.setOnClickListener {
            val bname = textBusinessName.text.toString()
            val oname = textBusinessOwner.text.toString()
            val kof_business = textBusinessKind.text.toString()
            val yrs_res = textBusinessYrs.text.toString()
            val contact = textBusinessContact.text.toString()
            val purpose = textBusinessPurpose.text.toString()

            // Make a Volley request
            val url = "https://barangaymngmt.bsisakalam.com/business_clearance.php"
            val params = HashMap<String, String>()
            params["bname"] = bname
            params["oname"] = oname
            params["kof_business"] = kof_business
            params["yrs_res"] = yrs_res
            params["contact_no"] = contact
            params["purpose"] = purpose

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, (params as Map<*, *>?)?.let { it1 -> JSONObject(it1) },
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

        return businessFragment
    }
}