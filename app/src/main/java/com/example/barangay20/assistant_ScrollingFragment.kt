package com.example.barangay20

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

class assistant_ScrollingFragment: Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val barangayidScrollingfragment = inflater.inflate(R.layout.fragment_barangayid__scrolling, container, false)

        val brgyIdNameEditText: TextInputEditText = barangayidScrollingfragment.findViewById(R.id.textBrgyIdName)
        val brgyIdAddressEditText: TextInputEditText = barangayidScrollingfragment.findViewById(R.id.textBrgyIdAddress)
        val brgyIdBirthdayEditText: TextInputEditText = barangayidScrollingfragment.findViewById(R.id.textBrgyIdBirthday)
        val brgyIdStatusEditText: TextInputEditText = barangayidScrollingfragment.findViewById(R.id.textBrgyIdStatus)
        val brgyIdContactEditText: TextInputEditText = barangayidScrollingfragment.findViewById(R.id.textBrgyIdContact)
        val brgyIdPrecinctEditText: TextInputEditText = barangayidScrollingfragment.findViewById(R.id.textBrgyIdPrecinct)
        val brgyIdSssEditText: TextInputEditText = barangayidScrollingfragment.findViewById(R.id.textBrgyIdSss)
        val brgyIdTinEditText: TextInputEditText = barangayidScrollingfragment.findViewById(R.id.textBrgyIdTin)
        val brgyIdEmgNameEditText: TextInputEditText = barangayidScrollingfragment.findViewById(R.id.textBrgyIdEmgName)
        val brgyIdEmgContactEditText: TextInputEditText = barangayidScrollingfragment.findViewById(R.id.textBrgyIdEmgContact)
        val submitButton: Button = barangayidScrollingfragment.findViewById(R.id.submitbrgyid)

        // Set an OnClickListener to open DatePickerDialog
        brgyIdBirthdayEditText.setOnClickListener {
            showDatePickerDialog(brgyIdBirthdayEditText)
        }

        submitButton.setOnClickListener {


            val id = brgyIdNameEditText.text.toString().trim()
            val name = brgyIdNameEditText.text.toString().trim()
            val address = brgyIdAddressEditText.text.toString().trim()
            val birthday = brgyIdBirthdayEditText.text.toString().trim()
            val civilStatus = brgyIdStatusEditText.text.toString().trim()
            val contactNo = brgyIdContactEditText.text.toString().trim()
            val precinctNo = brgyIdPrecinctEditText.text.toString().trim()
            val gssSss = brgyIdSssEditText.text.toString().trim()
            val tin = brgyIdTinEditText.text.toString().trim()
            val emgName = brgyIdEmgNameEditText.text.toString().trim()
            val emgContactNo = brgyIdEmgContactEditText.text.toString().trim()

            val url = "https://barangaymngmt.bsisakalam.com/read_barangayId.php" // Replace with your actual API endpoint

            val params = HashMap<String, String>()
            params["id"] = id
            params["name"] = name
            params["address"] = address
            params["birthday"] = birthday
            params["civil_status"] = civilStatus
            params["contact_no"] = contactNo
            params["precinct_no"] = precinctNo
            params["gss_sss"] = gssSss
            params["tin"] = tin
            params["emg_name"] = emgName
            params["emg_contact_no"] = emgContactNo


            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, (params as Map<*, *>?)?.let { it1 -> JSONObject(it1) },
                { response ->
                    val status = response.optBoolean("status")
                    val message = response.optString("message")
                    if (status) {
                        Toast.makeText(requireContext(), "Success: $message", Toast.LENGTH_SHORT).show()

                        // Show the success AslertDialog
                        showSuccessAlertDialog()

                    } else {
                        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
                    }
                },
                { error ->
                    Toast.makeText(requireContext(), "Volley error: $error", Toast.LENGTH_SHORT).show()
                }
            )

            // Add the request to the RequestQueuez
            Volley.newRequestQueue(requireContext()).add(jsonObjectRequest)

        }

        return barangayidScrollingfragment
    }

    // Function to show DatePickerDialog
    private fun showDatePickerDialog(editText: TextInputEditText) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                // Update the TextInputEditText with the selected date
                val selectedDate = formatDate(year, month, dayOfMonth)
                editText.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }

    // Function to format the date
    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}