package com.example.pdp_kurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pdp_kurs.databinding.GrListBinding
import com.example.pdp_kurs.entity.Guruh
import com.example.pdp_kurs.entity.Talaba

lateinit var talab_list:ArrayList<Talaba>
class Group_Rv (var list: List<Guruh>, var talabaList: List<Talaba>, var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<Group_Rv.Vh>(){

    inner class Vh(var grListBinding: GrListBinding) : RecyclerView.ViewHolder(grListBinding.root){
        fun onBind(guruh: Guruh){

            talab_list = ArrayList()

            if (talabaList.isNotEmpty()){
                for (talaba in talabaList) {
                    if (talaba.tl_group == guruh.id) {
                        talab_list.add(talaba)
                    }
                }
            }
            grListBinding.grNumberr.text = "O'qubvchilar soni: ${talab_list.size.toString()} ta"
            grListBinding.grName.text = guruh.gr_name
            grListBinding.startt.setOnClickListener {
                onItemClickListener.onItemStartClick(guruh, position, grListBinding.startt)
            }
            grListBinding.editt.setOnClickListener {
                onItemClickListener.onItemEditClick(guruh,position, grListBinding.editt)
            }
            grListBinding.delete.setOnClickListener {
                onItemClickListener.onItemDeleteClick(guruh,position,grListBinding.delete)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(GrListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onItemStartClick(guruh: Guruh,position: Int, linearLayout: LinearLayout)
        fun onItemEditClick(guruh: Guruh,position: Int,linearLayout: LinearLayout)
        fun onItemDeleteClick(guruh: Guruh,position: Int,linearLayout: LinearLayout)
    }
}