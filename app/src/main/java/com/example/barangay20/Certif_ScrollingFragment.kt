package com.example.barangay20

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class Certif_ScrollingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_certif__scrolling, container, false)
        val submit = view.findViewById<Button>(R.id.submit)
        submit.setOnClickListener {
            val certiNameEditText: TextInputEditText = requireView().findViewById(R.id.textcertiname)
            val certiAddressEditText: TextInputEditText = requireView().findViewById(R.id.textcertiAddress)
            val certiyearresEditText: TextInputEditText = requireView().findViewById(R.id.textcertiRes)
            val certicontactEditText: TextInputEditText = requireView().findViewById(R.id.textcertiContact)
            val certiemailEditText: TextInputEditText = requireView().findViewById(R.id.CertiEmail)
            val certipurposeEditText: TextInputEditText = requireView().findViewById(R.id.textcertiPurpose)
            val certirequestEditText: TextInputEditText = requireView().findViewById(R.id.textCertiRequest)

            val name = certiNameEditText.text.toString().trim()
            val address = certiAddressEditText.text.toString().trim()
            val year_res = certiyearresEditText.text.toString().trim()
            val contact = certicontactEditText.text.toString().trim()
            val email = certiemailEditText.text.toString().trim()
            val purpose = certipurposeEditText.text.toString().trim()
            val requested = certirequestEditText.text.toString().trim()

            val url = "http://brgymngmt.bsisakalam.com/api/req_brgyclrs_api.php"
            val queue = Volley.newRequestQueue(requireContext())
            val params = HashMap<String, String>()
            params["name"] = name
            params["address"] = address
            params["yrs_res"] = year_res
            params["contact"] = contact
            params["email"] = email
            params["purpose"] = purpose
            params["request"] = requested
            val request = object : StringRequest(Request.Method.POST, url,
                { response ->
                    try {
                        val jsonResponse = JSONObject(response)
                        if (jsonResponse.getBoolean("status")) {
                            Toast.makeText(requireContext(), "Request Successful", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(), jsonResponse.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(requireContext(), "Invalid response format", Toast.LENGTH_LONG).show()
                    }
                },
                { error ->
                    Toast.makeText(requireContext(), "Request Error", Toast.LENGTH_LONG).show()
                }) {

                override fun getParams(): MutableMap<String, String> {
                    return params
                }
            }

            queue.add(request)
        }
        return view
    }
}
