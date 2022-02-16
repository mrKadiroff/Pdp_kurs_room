package com.example.pdp_kurs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pdp_kurs.database.AppDatabase
import com.example.pdp_kurs.databinding.MentorRvBinding
import com.example.pdp_kurs.entity.Guruh
import com.example.pdp_kurs.entity.Mentor
import com.example.pdp_kurs.entity.Talaba

lateinit var appDatabase: AppDatabase
class MentorAdapter (var list: ArrayList<Mentor>, var guruhList: List<Guruh>, var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MentorAdapter.Vh>() {

    inner class Vh(var mentorRvBinding: MentorRvBinding) :
        RecyclerView.ViewHolder(mentorRvBinding.root){
        fun onBind(mentor: Mentor, position:Int){
            appDatabase = AppDatabase.getInstance(mentorRvBinding.root.context)

//            if (guruhList.isNotEmpty()){
//                for (i in 0 until guruhList.size){
//                    if (guruhList[i].gr_mentor == mentor.ment_name){
//                        mentor.guruhId = "choosen"
//                        appDatabase.mentorDao().updateMentor(mentor)
//                    }
//                }
//            }



            mentorRvBinding.mentorName.text = (mentor.ment_name+mentor.ment_famil)
            mentorRvBinding.mentorOtchestva.text = mentor.ment_otchest

            mentorRvBinding.edit.setOnClickListener {
                onItemClickListener.onEditClick(mentor, position, mentorRvBinding.edit)
            }

            mentorRvBinding.delete.setOnClickListener {
                onItemClickListener.onDeleteClick(mentor, position, mentorRvBinding.delete)
            }




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh (MentorRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onEditClick(mentor: Mentor, position: Int, linearLayout: LinearLayout)
        fun onDeleteClick(mentor: Mentor, position: Int, linearLayout: LinearLayout)
    }
}