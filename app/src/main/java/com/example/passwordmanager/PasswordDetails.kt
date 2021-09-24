package com.example.passwordmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.R.string.no
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_password_details.*


class PasswordDetails : Fragment() {
    lateinit var passwordItem: PasswordItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val actionBar = (activity as AppCompatActivity).supportActionBar
        //set actionbar title
        actionBar!!.title = "Password Details"
        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)


        return inflater.inflate(R.layout.fragment_password_details, container, false)
    }

    override fun onStart() {
        super.onStart()
        visitButton.setOnClickListener {
            var url = passwordItem.weblink
            if (!url.contains("https") && !url.contains("http")) {
                url = "https://" + url
            }
            val webIntent: Intent = Uri.parse(url).let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            try {
                it.context.startActivity(webIntent)
            } catch (e: ActivityNotFoundException) {
                // Define what your app should do if no activity can handle the intent.
//                Toast.makeText(this, "Page not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val fm = activity!!.supportFragmentManager
        fm.popBackStack("details", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        return  true
    }

    override fun onResume() {
        super.onResume()

        emailLabel.text = passwordItem.name
        websiteLabel.text = passwordItem.weblink
        passwordLabel.text = passwordItem.passwords.first()
    }

    override fun onDestroy() {
        super.onDestroy()
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar!!.title = "Password Manager"
        actionBar.setDisplayHomeAsUpEnabled(false)
    }

    companion object {
        fun createPasswordDetail(passwordItem: PasswordItem): PasswordDetails {
            var detailsFrag = PasswordDetails()
            detailsFrag.passwordItem = passwordItem
            return detailsFrag
        }
    }


}