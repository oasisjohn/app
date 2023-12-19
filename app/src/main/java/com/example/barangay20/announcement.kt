package com.example.barangay20

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

data class announcementList(val title: String, val headline: String)
class announcement : Fragment() {
    private lateinit var announcementListView: ListView
    private lateinit var announcementAdapter: ArrayAdapter<announcementList>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val announcementFragment =
            inflater.inflate(R.layout.fragment_announcement, container, false)
        announcementListView = announcementFragment.findViewById(R.id.list_announcement)

        announcementAdapter = object : ArrayAdapter<announcementList>(
            requireContext(),
            R.layout.announcement_list_item,
            R.id.titleTextView,
            mutableListOf()
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var listItemView = convertView
                if (listItemView == null) {
                    listItemView = LayoutInflater.from(context).inflate(
                        R.layout.announcement_list_item, parent, false
                    )
                }

                val currentAnnouncement = getItem(position)

                val titleTextView: TextView = listItemView!!.findViewById(R.id.titleTextView)
                val headlineTextView: TextView = listItemView.findViewById(R.id.headlineTextView)

                titleTextView.text = currentAnnouncement?.title
                headlineTextView.text = currentAnnouncement?.headline

                listItemView.setOnClickListener {
                    // Handle click event, show all information in a dialog
                    showAllInformation(currentAnnouncement)
                }

                return listItemView
            }
        }

        announcementListView.adapter = announcementAdapter

        val url = "https://brgymngmt.bsisakalam.com/api/read_announcement.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.optBoolean("status")

                    if (status) {
                        val announcementsArray = jsonResponse.optJSONArray("announcementData")
                        val announcements = mutableListOf<announcementList>()

                        if (announcementsArray != null) {
                            for (i in 0 until announcementsArray.length()) {
                                val announcementObject = announcementsArray.getJSONObject(i)
                                val announcement = announcementList(
                                    announcementObject.optString("title"),
                                    announcementObject.optString("headline")
                                )
                                announcements.add(announcement)
                            }
                        }
                        announcementAdapter.clear()
                        announcementAdapter.addAll(announcements)
                    } else {
                        // Handle error or show a message
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            })
        Volley.newRequestQueue(requireContext()).add(stringRequest)
        return announcementFragment
    }

    private fun showAllInformation(announcement: announcementList?) {
        announcement?.let {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Announcement Information")
            alertDialogBuilder.setMessage("Title: ${it.title}\nHeadline: ${it.headline}")
            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}