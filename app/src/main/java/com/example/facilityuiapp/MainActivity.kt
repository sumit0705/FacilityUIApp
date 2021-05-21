package com.example.facilityuiapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.facilityuiapp.models.Exclusion
import com.example.facilityuiapp.models.Facility
import com.example.facilityuiapp.repo.Repository


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: FacilityListAdapter
    private lateinit var viewModel: MainViewModel

    private var facilitiesList: MutableList<Facility> = ArrayList()
    private var exclusionList: MutableList<List<Exclusion>> = ArrayList()
    private var exHashMap : HashMap<Int, Int> = HashMap()

    private lateinit var pbLayout: RelativeLayout // progressBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Facilities Details";
        pbLayout = findViewById(R.id.progress_layout);
        pbLayout.visibility = View.VISIBLE;

        if (!isInternetConnected()) {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show();
        }
        recyclerView = findViewById(R.id.recyclerView)

        fetchData()
        mAdapter= FacilityListAdapter(facilitiesList,exclusionList,exHashMap,this)

        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {
        val repo = Repository()
        val viewModelFactory = MainViewModelFactory(repo)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        viewModel.getFacilityData()
        exHashMap = viewModel.getexhashMap()

        viewModel.myResponse.observe(this, Observer { response ->

            if (response != null) {

                facilitiesList.addAll(response.facilities)
                exclusionList.addAll(response.exclusions)
                mAdapter.notifyDataSetChanged()
                pbLayout.visibility = View.GONE

            } else {
                Log.e("TAG", "onFailure error")
            }
        })
    }

    private fun isInternetConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}


