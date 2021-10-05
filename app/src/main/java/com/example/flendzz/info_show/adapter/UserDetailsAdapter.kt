package com.example.flendzz.info_show.adapter


import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flendzz.R
import com.example.flendzz.EmpDetailsActivity
import com.example.flendzz.info_show.model.data_class.data_classItem
import com.karumi.dexter.Dexter
import android.app.Activity
import android.util.Log
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class UserDetailsAdapter() : RecyclerView.Adapter<AllMatchesViewHolder>() ,
    MultiplePermissionsListener {

    val permissions = listOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_SMS,
    )

    var empList = mutableListOf<data_classItem>()

    lateinit var context: Context

    /** Getting data from RecyclerActivity in to empList
     * */
    fun setUserList(empList: List<data_classItem>, context: Context) {
        this.empList = empList.toMutableList()

        this.context = context
        notifyDataSetChanged()

    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMatchesViewHolder {
        val inflatedView = parent.inflate(R.layout.item_layout, false)
        return AllMatchesViewHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: AllMatchesViewHolder, position: Int) {
        val empList = empList[position] // extracting data from movies list
        holder.titleName.text = empList.name.toString()
        holder.emailTxt.text = empList.email.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context.applicationContext,EmpDetailsActivity::class.java)
            intent.putExtra("getId",empList.id.toString())
            context.startActivity(intent)
        }


        holder.emailTxt.setOnClickListener {
         holder.sendMail("Rushikesh Chavan Application for Android Develoeper",
             "Hello Team , \n " +
                 "Kindly go through functions of app.\n " +
                 "Thanks and Regards, \n " +
                 "Rushikesh Chavan",
             holder.emailTxt.text.toString())
        }




    }

    override fun getItemCount(): Int {
        return empList.size
    }


    override fun getItemViewType(position: Int): Int {
        return position
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

class AllMatchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleName = itemView.findViewById(R.id.title_name) as TextView
    val emailTxt = itemView.findViewById(R.id.emailItem) as TextView


    fun sendMail(subject: String, body: String, email: String) {

        val uri = Uri.parse("mailto:$email")
            .buildUpon()
            .appendQueryParameter("subject", subject)
            .appendQueryParameter("body", body)
            .appendQueryParameter("to", email)
            .build()

        val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
        try {
            itemView.context.startActivity(Intent.createChooser(emailIntent, "Select app"))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                itemView.context,
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}