package com.auf.cea.realmdiscussion.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auf.cea.realmdiscussion.databinding.ContentOwnerRvBinding
import com.auf.cea.realmdiscussion.views.model.Owner

class OwnerAdapter(private var ownerList: ArrayList<Owner>) : RecyclerView.Adapter<OwnerAdapter.OwnerViewHolder>() {

    inner class OwnerViewHolder(private val binding: ContentOwnerRvBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemData: Owner){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerViewHolder {
        val binding = ContentOwnerRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OwnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OwnerViewHolder, position: Int) {
        val ownerData = ownerList[position]
        holder.bind(ownerData)
    }

    override fun getItemCount(): Int {
        return ownerList.size
    }

    fun updateList(ownerList: ArrayList<Owner>){
        this.ownerList = arrayListOf()
        notifyDataSetChanged()
        this.ownerList = ownerList
        this.notifyItemInserted(this.ownerList.size)
    }

}