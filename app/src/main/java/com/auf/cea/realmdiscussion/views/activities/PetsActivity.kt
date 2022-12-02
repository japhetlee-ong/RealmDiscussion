package com.auf.cea.realmdiscussion.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.auf.cea.realmdiscussion.databinding.ActivityPetsBinding
import com.auf.cea.realmdiscussion.views.adapters.PetAdapter
import com.auf.cea.realmdiscussion.views.dialogs.AddPetDialog
import com.auf.cea.realmdiscussion.views.model.Pet

class PetsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetsBinding
    private lateinit var petList: ArrayList<Pet>
    private lateinit var adapter: PetAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        petList = arrayListOf()
        adapter = PetAdapter(petList)

        val layoutManager = LinearLayoutManager(this)
        binding.rvPets.layoutManager = layoutManager
        binding.rvPets.adapter = adapter

        binding.fab.setOnClickListener{
            val addPetDialog = AddPetDialog()
            addPetDialog.show(supportFragmentManager,null)
        }
    }
}