package com.auf.cea.realmdiscussion.realm.operations

import com.auf.cea.realmdiscussion.realm.database.OwnerRealm
import com.auf.cea.realmdiscussion.realm.database.PetRealm
import com.auf.cea.realmdiscussion.views.model.Pet
import io.realm.Case
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers

class PetDbOperations(private var config: RealmConfiguration) {

    suspend fun insertPet(name: String, age: Int, type: String, ownerName: String = ""){
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            val pet = PetRealm(name = name, age = age, petType = type)
            realmTransaction.insert(pet)

            if(ownerName.isNotEmpty()){
                val ownerResult = realmTransaction
                    .where(OwnerRealm::class.java)
                    .equalTo("name",ownerName,Case.INSENSITIVE)
                    .findFirst()

                if(ownerResult == null){
                    //CREATE THE OWNER OBJECT
                    val owner = OwnerRealm(name = ownerName)
                    realmTransaction.insert(owner)
                    owner.pets.add(pet)
                    realmTransaction.insertOrUpdate(owner)
                }else{
                    //UPDATE THE OWNER OBJECT
                    ownerResult.pets.add(pet)
                }
            }

        }
    }

    suspend fun retrievePets():ArrayList<Pet>{
        val realm = Realm.getInstance(config)
        val realmResults = arrayListOf<Pet>()

        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            realmResults.addAll(realmTransaction
                .where(PetRealm::class.java)
                .findAll()
                .map {
                    mapPet(it)
                })
        }

        return realmResults

    }

    suspend fun removePet(petId : String){
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            val petToRemove = realmTransaction
                .where(PetRealm::class.java)
                .equalTo("id",petId)
                .findFirst()
            petToRemove?.deleteFromRealm()
        }
    }

    suspend fun searchPet(query: String): ArrayList<Pet>{
        val realm = Realm.getInstance(config)
        val realmResults = arrayListOf<Pet>()
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            realmResults.addAll(realmTransaction
                .where(PetRealm::class.java)
                .contains("name",query,Case.INSENSITIVE)
                .findAll()
                .map {
                    mapPet(it)
                })
        }

        return realmResults
    }

    suspend fun updatePets(petId: String, petName: String){
        val realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            val petToUpdate = realmTransaction
                .where(PetRealm::class.java)
                .equalTo("id",petId)
                .findFirst()
            //UPDATE ANYTHING
            petToUpdate?.name = petName
        }
    }

    private fun mapPet(pet: PetRealm): Pet{
        return Pet(
            id = pet.id,
            name = pet.name,
            petType = pet.petType,
            ownerName = pet.owner?.firstOrNull()?.name ?: "",
            age = pet.age
        )
    }

}