package com.example.pdp_kurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdp_kurs.databinding.KursRvBinding
import com.example.pdp_kurs.entity.Kurs

class KursAdapter (var list: List<Kurs>,var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<KursAdapter.Vh>() {

    inner class Vh(var kursRvBinding: KursRvBinding) :
        RecyclerView.ViewHolder(kursRvBinding.root) {

        fun onBind(kurs: Kurs,position: Int) {
            kursRvBinding.courseNameTv.text = kurs.kr_name

            kursRvBinding.root.setOnClickListener {
                onItemClickListener.onItemClick(kurs)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(KursRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onItemClick(kurs: Kurs)
    }
}