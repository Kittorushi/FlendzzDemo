package com.example.flendzz

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.flendzz.info_show.ui.save.EmpDetailsViewModel
import com.example.flendzz.utlis.Utils
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class EmpDetailsActivity : AppCompatActivity(), MultiplePermissionsListener {

    private val viewModel by viewModel<EmpDetailsViewModel>()
    lateinit var empId: TextView
    lateinit var empname: TextView
    lateinit var emailId: TextView
    lateinit var address: TextView
    lateinit var phone_number: TextView
    lateinit var company: TextView
    lateinit var companyweb: TextView
    lateinit var empIdRef: String

    val permissions = listOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_SMS,
    )



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_lists)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        empIdRef = intent.getStringExtra("getId").toString()
        viewModel.fetchData(empIdRef)
        val online = Utils.isOnline(this@EmpDetailsActivity)

        empId = findViewById(R.id.empId)
        empname = findViewById(R.id.empname)
        emailId = findViewById(R.id.emailId)
        address = findViewById(R.id.address)
        phone_number = findViewById(R.id.phone_number)
        company = findViewById(R.id.company)
        companyweb = findViewById(R.id.companyweb)

        if (online) {
            setLiveDataListeners()
        } else {
            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show()
        }


        phone_number.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                Dexter.withActivity(this )
                    .withPermissions(permissions)
                    .withListener(this)
                    .check()

            } else {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:${phone_number.text.trim().toString()}")
                startActivity(callIntent)
            }

        }

        emailId.setOnClickListener {
            sendMail("Rushikesh Chavan Application for Android Develoeper",
                "Hello Team , \n " +
                        "Kindly go through functions of app.\n " +
                        "Thanks and Regards, \n " +
                        "Rushikesh Chavan",
                emailId.text.toString())
        }


    }

    private fun setLiveDataListeners() {
        Utils.observeOnce(viewModel.data) {


            empId.text = "Employee Id : ${it.id.toString()} "
            empname.text = "Name  : ${it.name.toString()} "
            emailId.text = (it.email).toString().lowercase()
            address.text =
                "${it.address.suite},${it.address.street},\n${it.address.city} - ${it.address.zipcode}  "
            phone_number.text = it.phone
            company.text =  "Company Name:  ${it.company.name}"
            companyweb.text = "Website:  ${it.website}"

        }
    }


    fun sendMail(subject: String, body: String, email: String) {

        val uri = Uri.parse("mailto:$email")
            .buildUpon()
            .appendQueryParameter("subject", subject)
            .appendQueryParameter("body", body)
            .appendQueryParameter("to", email)
            .build()

        val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
        try {
            startActivity(Intent.createChooser(emailIntent, "Select app"))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this@EmpDetailsActivity,
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

    }

    override fun onPermissionRationaleShouldBeShown(
        permissions: MutableList<PermissionRequest>?,
        token: PermissionToken?
    ) {
        Log.d("TAG", "onPermissionRationaleShouldBeShown: Permission rejected ")
        token!!.continuePermissionRequest()
    }


}