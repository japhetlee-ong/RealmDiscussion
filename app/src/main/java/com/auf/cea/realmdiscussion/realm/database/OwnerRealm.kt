package com.auf.cea.realmdiscussion.realm.database

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import org.bson.types.ObjectId

open class OwnerRealm(
    @PrimaryKey
    var id: String = ObjectId().toHexString(),
    @Required
    var name: String = "",
    var pets: RealmList<PetRealm> = RealmList()
): RealmObject()
