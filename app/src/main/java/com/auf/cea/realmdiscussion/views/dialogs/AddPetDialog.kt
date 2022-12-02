package com.auf.cea.realmdiscussion.views.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.auf.cea.realmdiscussion.databinding.DialogAddPetBinding
import com.auf.cea.realmdiscussion.realm.config.RealmConfig
import com.auf.cea.realmdiscussion.realm.operations.PetDbOperations
import kotlinx.coroutines.*

class AddPetDialog : DialogFragment() {

    private lateinit var binding: DialogAddPetBinding
    lateinit var refreshDataCallback: RefreshDataInterface

    interface RefreshDataInterface{
        fun refreshData()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogAddPetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            cbHasOwner.setOnCheckedChangeListener{ _, isChecked ->
                if(isChecked){
                    edtOwner.isEnabled = true
                }
            }
            btnAdd.setOnClickListener{
                if(edtPetName.text.isNullOrEmpty()){
                    edtPetName.error = "Required"
                    return@setOnClickListener
                }
                if(edtAge.text.isNullOrEmpty()){
                    edtAge.error = "Required"
                    return@setOnClickListener
                }
                if(edtPetType.text.isNullOrEmpty()){
                    edtPetType.error = "Required"
                    return@setOnClickListener
                }
                if(cbHasOwner.isChecked && edtOwner.text.isNullOrEmpty()){
                    edtOwner.error = "Required"
                    return@setOnClickListener
                }

                val realmConfig = RealmConfig.getConfiguration()
                val petDbOperations = PetDbOperations(realmConfig)
                val petAge = edtAge.text.toString().toInt()
                val ownerName = if(cbHasOwner.isChecked) edtOwner.text.toString() else ""

                val coroutineContext = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutineContext + CoroutineName("AddToDatabase"))
                scope.launch(Dispatchers.IO){
                    petDbOperations.insertPet(edtPetName.text.toString(),petAge,edtPetType.text.toString(),ownerName)
                    withContext(Dispatchers.Main){
                        Toast.makeText(activity,"Pet has been added", Toast.LENGTH_SHORT).show()
                        refreshDataCallback.refreshData()
                        dialog?.dismiss()
                    }
                }
            }
        }


    }
}