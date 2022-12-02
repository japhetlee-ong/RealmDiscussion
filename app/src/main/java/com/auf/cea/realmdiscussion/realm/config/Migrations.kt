package com.auf.cea.realmdiscussion.realm.config

import io.realm.FieldAttribute
import io.realm.RealmMigration

val migration = RealmMigration {realm, oldVersion, newVersion ->
    if(oldVersion == 1L){
        val petSchema = realm.schema.get("PetRealm")
        petSchema?.let {
            it.removeField("ownerName")
            realm.schema.create("OwnerRealm")
                .addField("id", String::class.java,FieldAttribute.PRIMARY_KEY,FieldAttribute.REQUIRED)
                .addField("name", String::class.java,FieldAttribute.REQUIRED)
                .addRealmListField("pets",it)
        }
    }
}