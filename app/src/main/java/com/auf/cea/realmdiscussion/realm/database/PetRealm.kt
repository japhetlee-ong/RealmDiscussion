package com.auf.cea.realmdiscussion.realm.database

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class PetRealm(
    @PrimaryKey
    var id: String = ObjectId().toHexString(),
    @Required
    var name: String = "",
    var age: Int = 0,
    @Required
    var petType: String ="",
    @LinkingObjects("pets")
    val owner: RealmResults<OwnerRealm>? = null
): RealmObject()
