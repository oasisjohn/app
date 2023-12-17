import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.barangay20.R

data class UserStatus(val userName: String, val status: String)

class logrequest_ScrollingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logrequest__scrolling, container, false)

        // Sample data for user statuses
        val userStatuses = listOf(
            UserStatus("User1", "Pending"),
            UserStatus("User2", "Approved"),
            UserStatus("User3", "Rejected")
            // Add more users and statuses as needed
        )

        // Create an adapter to bind the data to the custom layout
        val adapter = object : ArrayAdapter<UserStatus>(
            requireContext(),
            R.layout.list_item_layout,
            R.id.userStatusTextView,
            userStatuses
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val itemView = super.getView(position, convertView, parent)

                // Get the user status at the current position
                val userStatus = getItem(position)

                // Set the text for the TextView
                val userStatusTextView: TextView = itemView.findViewById(R.id.userStatusTextView)
                userStatusTextView.text = "${userStatus?.userName}: ${userStatus?.status}"

                // Set up click listener for the "View" button
                val viewButton: TextView = itemView.findViewById(R.id.viewButton)
                viewButton.setOnClickListener {
                    // Handle the click event, show all information in a dialog
                    showAllInformation(userStatus)
                }

                return itemView
            }
        }

        // Get the ListView reference
        val listView: ListView = view.findViewById(R.id.list_announcement)

        // Set the adapter for the ListView
        listView.adapter = adapter

        return view
    }

    private fun showAllInformation(userStatus: UserStatus?) {
        userStatus?.let {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("User Information")
            alertDialogBuilder.setMessage("Username: ${it.userName}\nStatus: ${it.status}")
            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}
