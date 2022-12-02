package com.auf.cea.realmdiscussion.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.auf.cea.realmdiscussion.databinding.ActivityPetsBinding
import com.auf.cea.realmdiscussion.realm.config.RealmConfig
import com.auf.cea.realmdiscussion.realm.operations.PetDbOperations
import com.auf.cea.realmdiscussion.views.adapters.PetAdapter
import com.auf.cea.realmdiscussion.views.dialogs.AddPetDialog
import com.auf.cea.realmdiscussion.views.model.Pet
import io.realm.RealmConfiguration
import kotlinx.coroutines.*

class PetsActivity : AppCompatActivity(), AddPetDialog.RefreshDataInterface, PetAdapter.PetAdapterInterface {
    private lateinit var binding: ActivityPetsBinding
    private lateinit var petList: ArrayList<Pet>
    private lateinit var adapter: PetAdapter
    private lateinit var realmConfiguration: RealmConfiguration
    private lateinit var petDbOperations: PetDbOperations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        realmConfiguration = RealmConfig.getConfiguration()
        petDbOperations = PetDbOperations(realmConfiguration)

        petList = arrayListOf()
        adapter = PetAdapter(petList,this,this)

        val layoutManager = LinearLayoutManager(this)
        binding.rvPets.layoutManager = layoutManager
        binding.rvPets.adapter = adapter

        binding.fab.setOnClickListener{
            val addPetDialog = AddPetDialog()
            addPetDialog.refreshDataCallback = this
            addPetDialog.show(supportFragmentManager,null)
        }

        binding.btnSearch.setOnClickListener{
            if(binding.edtSearch.text.toString().isEmpty()){
                binding.edtSearch.error = "Required"
                return@setOnClickListener
            }

            val coroutineContext = Job() + Dispatchers.IO
            val scope = CoroutineScope(coroutineContext + CoroutineName("SearchPets"))
            scope.launch (Dispatchers.IO){
                val result = petDbOperations.searchPet(binding.edtSearch.text.toString())
                withContext(Dispatchers.Main){
                    adapter.updatePetList(result)
                }
            }

        }

    }


    override fun onResume() {
        super.onResume()
        getPets()
    }

    override fun refreshData() {
        getPets()
    }

    private fun getPets(){

        val coroutineContext = Job() + Dispatchers.IO
        val scope = CoroutineScope(coroutineContext + CoroutineName("GetFromDatabase"))
        scope.launch(Dispatchers.IO){
            val result = petDbOperations.retrievePets()
            withContext(Dispatchers.Main){
                adapter.updatePetList(result)
            }
        }
    }

    override fun deletePet(id: String) {

        val coroutineContext = Job() + Dispatchers.IO
        val scope = CoroutineScope(coroutineContext + CoroutineName("DeleteFromDatabase"))
        scope.launch(Dispatchers.IO){
            petDbOperations.removePet(id)
//            withContext(Dispatchers.Main){
//                getPets()
//            }
        }

    }


}