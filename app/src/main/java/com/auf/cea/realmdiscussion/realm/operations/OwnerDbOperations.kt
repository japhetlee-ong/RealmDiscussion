package com.auf.cea.realmdiscussion.realm.operations

import com.auf.cea.realmdiscussion.realm.database.OwnerRealm
import com.auf.cea.realmdiscussion.views.model.Owner
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class OwnerDbOperations(private var config: RealmConfiguration) {

    suspend fun retrieveOwners():ArrayList<Owner>{
        val realm = Realm.getInstance(config)
        val realmResults = arrayListOf<Owner>()
        realm.executeTransactionAwait(Dispatchers.IO){ realmTransaction ->
            realmResults.addAll(realmTransaction
                .where(OwnerRealm::class.java)
                .findAll()
                .map {
                    mapOwner(it)
                })
        }

        return realmResults
    }

    private fun mapOwner(owner: OwnerRealm): Owner{
        return Owner(
            id = owner.id,
            name = owner.name,
            petCount = owner.pets.size
        )
    }

}