package com.auf.cea.realmdiscussion.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.auf.cea.realmdiscussion.databinding.ActivityOwnersBinding
import com.auf.cea.realmdiscussion.realm.config.RealmConfig
import com.auf.cea.realmdiscussion.realm.operations.OwnerDbOperations
import com.auf.cea.realmdiscussion.views.adapters.OwnerAdapter
import com.auf.cea.realmdiscussion.views.model.Owner
import kotlinx.coroutines.*

class OwnersActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOwnersBinding
    private lateinit var adapter: OwnerAdapter
    private lateinit var ownerList: ArrayList<Owner>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ownerList = arrayListOf()
        adapter = OwnerAdapter(ownerList)

        val layoutManger = LinearLayoutManager(this)
        binding.rvOwner.layoutManager = layoutManger
        binding.rvOwner.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getOwners()
    }

    private fun getOwners(){
        val coroutineContext = Job() + Dispatchers.IO
        val scope = CoroutineScope(coroutineContext + CoroutineName("GetOwnerFromDB"))
        val realmConfig = RealmConfig.getConfiguration()
        val ownerDbOperations = OwnerDbOperations(realmConfig)

        scope.launch(Dispatchers.IO){
            val results = ownerDbOperations.retrieveOwners()
            withContext(Dispatchers.Main){
                adapter.updateList(results)
            }
        }
    }
}