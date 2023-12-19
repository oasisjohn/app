import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.barangay20.R
import org.json.JSONObject

data class UserStatus(
    val id: String,
    val accountId: String,
    val name: String,
    val address: String,
    val yrsRes: String,
    val contactNo: String,
    val purpose: String,
    val request: String,
    val status: String,
    val email: String
)

class logrequest_ScrollingFragment : Fragment() {
    private lateinit var adapter: ArrayAdapter<UserStatus>
    private lateinit var userStatuses: MutableList<UserStatus>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logrequest__scrolling, container, false)

        userStatuses = mutableListOf()

        adapter = object : ArrayAdapter<UserStatus>(
            requireContext(),
            R.layout.list_item_layout,
            R.id.userStatusTextView,
            userStatuses
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val itemView = super.getView(position, convertView, parent)

                val userStatusTextView: TextView = itemView.findViewById(R.id.userStatusTextView)
                userStatusTextView.text = "${userStatuses[position].name}: ${userStatuses[position].status}"

                val viewButton: TextView = itemView.findViewById(R.id.viewButton)
                viewButton.setOnClickListener {
                    showAllInformation(userStatuses[position])
                }

                return itemView
            }
        }

        val listView: ListView = view.findViewById(R.id.list_announcement)
        listView.adapter = adapter

        fetchDataFromApi()

        return view
    }

    private fun fetchDataFromApi() {
        val url = "http://brgymngmt.bsisakalam.com/api/read_brgyclrs.php"

        // Example: Send 'id' as a parameter
        val params = mapOf(
            "id" to 6
        )

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.POST, url, (params as Map<*, *>?)?.let { JSONObject(it) },
            { response ->
                parseJsonResponse(response)
            },
            { error ->
                Log.e("Volley Error", "Error occurred: $error")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["FollowRedirects"] = "true"
                return headers
            }
        }

        Volley.newRequestQueue(requireContext()).add(jsonObjectRequest)
    }

    private fun parseJsonResponse(response: JSONObject) {
        if (response.has("error")) {
            Log.e("Volley Error", "Error occurred: ${response.getString("error")}")
            return
        }

        val id = response.optString("id")
        val accountId = response.optString("account_id")
        val name = response.optString("name")
        val address = response.optString("address")
        val yrsRes = response.optString("yrs_res")
        val contactNo = response.optString("contact_no")
        val purpose = response.optString("purpose")
        val req = response.optString("request")
        val status = response.optString("status")
        val email = response.optString("email")

        val userData = response.optJSONObject("user_data")

        userStatuses.add(
            UserStatus(
                id, accountId, name, address, yrsRes, contactNo, purpose, req, status, email
            )
        )

        adapter.notifyDataSetChanged()
    }

    private fun showAllInformation(userStatus: UserStatus?) {
        userStatus?.let {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("User Information")
            alertDialogBuilder.setMessage("Username: ${it.name}\nStatus: ${it.status}")
            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}