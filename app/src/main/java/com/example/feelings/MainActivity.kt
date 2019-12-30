package com.example.feelings

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var feelingViewModel: FeelingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Create instance of Recycler View
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        //Create instance of Adapter
        val adapter = FeelingAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Initiliaze view Model
        feelingViewModel = ViewModelProvider(this).get(FeelingViewModel::class.java)

        feelingViewModel.allFeeling.observe(
            this,
            Observer {
                if(it.isNotEmpty()){
                    adapter.setFeelingList(it)
                }
            }
        )
        fab.setOnClickListener{
            val intent: Intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE){
            if(requestCode == Activity.RESULT_OK){
                val _mode = data?.getIntExtra(AddActivity.EXTRA_MOOD, 0)
                val _remark = data?.getStringExtra(AddActivity.EXTRA_REMARK)

                val feeling:Feeling = Feeling(
                    id=0,
                    mood= _mode!!,
                    remarks = _remark!!,
                    created_at = System.currentTimeMillis())
            }
            //Insert record into DATAVASE
            feelingViewModel.insert(feeling)
        }

    }
    companion object{
        val REQUEST_CODE = 1
    }
}
