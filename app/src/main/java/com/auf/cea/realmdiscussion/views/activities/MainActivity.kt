package com.auf.cea.realmdiscussion.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.auf.cea.realmdiscussion.R
import com.auf.cea.realmdiscussion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener{
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_owner_list -> {
                val intent = Intent(this,OwnersActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_pet_list -> {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}