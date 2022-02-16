package com.example.pdp_kurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pdp_kurs.databinding.StListBinding
import com.example.pdp_kurs.entity.Talaba

class Talaba_Adapter (var list: ArrayList<Talaba>, var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<Talaba_Adapter.Vh>() {

    inner class Vh(var stListBinding: StListBinding) :
        RecyclerView.ViewHolder(stListBinding.root){
        fun onBind(talaba: Talaba, position:Int){
            stListBinding.ismss.text = (talaba.tl_surname+talaba.tl_name)
            stListBinding.otests.text = talaba.tl_otchestva
            stListBinding.sanana.text = talaba.tl_sana

            stListBinding.edit.setOnClickListener {
                onItemClickListener.onEditClick(talaba, position, stListBinding.edit)
            }

            stListBinding.delete.setOnClickListener {
                onItemClickListener.onDeleteClick(talaba, position, stListBinding.delete)
            }




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh (StListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onEditClick(talaba: Talaba, position: Int, linearLayout: LinearLayout)
        fun onDeleteClick(talaba: Talaba, position: Int, linearLayout: LinearLayout)
    }
}