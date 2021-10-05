package com.example.flendzz

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.flendzz.info_show.adapter.UserDetailsAdapter
import com.example.flendzz.info_show.ui.allmatches.EmpListViewModel
import com.example.flendzz.utlis.Utils
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmpListActivity : AppCompatActivity() {

    private val viewModel by viewModel<EmpListViewModel>()

    private val adapter = UserDetailsAdapter()
    lateinit var progressBar: ProgressBar

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        progressBar = findViewById(R.id.progressBar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        initRv()

        val online = Utils.isOnline(this@EmpListActivity)

        if (online) {
            setLiveDataListeners()
        } else {
            progressBar.visibility = View.GONE
            findViewById<ConstraintLayout>(R.id.drawer_layout)?.snack("Check Internet Connection!!")
        }

    }

    private fun initRv() {
        findViewById<RecyclerView>(R.id.recyclerview).adapter = adapter
    }

    private fun setLiveDataListeners() {
        Utils.observeOnce(viewModel.data) {
            adapter.setUserList(it, this)
            progressBar.visibility = View.GONE
        }
    }

    fun View.snack(str: String) = Snackbar.make(this, str, Snackbar.LENGTH_LONG).show()


}