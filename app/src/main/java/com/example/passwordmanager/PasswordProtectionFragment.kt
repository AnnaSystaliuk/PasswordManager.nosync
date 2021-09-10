package com.example.passwordmanager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_password_protection.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PasswordProtectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PasswordProtectionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var finish: () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_password_protection,container,false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val preferences = this.activity
            ?.getSharedPreferences("shared", Context.MODE_PRIVATE)
        val currentPassword = preferences?.getString("secretPassword", "")

        donePasswordButton.setOnClickListener {
            if (protectionPasswordEditText.text.isNotBlank()){
                if (protectionPasswordEditText.text.toString() == currentPassword) {
                    Toast.makeText(this.activity, "Access granted!", Toast.LENGTH_SHORT).show()
                    this.activity?.supportFragmentManager?.popBackStackImmediate()
                    finish()
                } else {
                    Toast.makeText(this.activity, "Wrong password!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this.activity, "Please enter password!", Toast.LENGTH_SHORT).show()
            }

        }
        }
}