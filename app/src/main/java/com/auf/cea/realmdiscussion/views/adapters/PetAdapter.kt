package com.auf.cea.realmdiscussion.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auf.cea.realmdiscussion.databinding.ContentPetRvBinding
import com.auf.cea.realmdiscussion.views.model.Pet

class PetAdapter(private var petList: ArrayList<Pet>): RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    inner class PetViewHolder(private val binding: ContentPetRvBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemData : Pet){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ContentPetRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val petData = petList[position]
        holder.bind(petData)
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    fun updatePetList(petList: ArrayList<Pet>){
        this.petList = arrayListOf()
        notifyDataSetChanged()
        this.petList = petList
        this.notifyItemInserted(this.petList.size)
    }

}