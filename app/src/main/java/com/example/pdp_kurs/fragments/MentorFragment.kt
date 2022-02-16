package com.example.pdp_kurs.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pdp_kurs.R
import com.example.pdp_kurs.adapters.MentorAdapter
import com.example.pdp_kurs.database.AppDatabase
import com.example.pdp_kurs.databinding.FragmentMentorBinding
import com.example.pdp_kurs.databinding.MentorDialogBinding
import com.example.pdp_kurs.entity.Guruh
import com.example.pdp_kurs.entity.Kurs
import com.example.pdp_kurs.entity.Mentor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MentorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var binding: FragmentMentorBinding
    lateinit var mentorlist:ArrayList<Mentor>
    lateinit var appDatabase: AppDatabase
    lateinit var list: ArrayList<Mentor>
    lateinit var mentorsAdapter: MentorAdapter
    lateinit var groupList: List<Guruh>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMentorBinding.inflate(layoutInflater,container, false)
        appDatabase = AppDatabase.getInstance(binding.root.context)

        binding.toolbarMentor.inflateMenu(R.menu.add_menu)

        val kurslar = arguments?.getSerializable("kursi") as Kurs
        binding.toolbarMentor.title = kurslar.kr_name

        binding.toolbarMentor.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        mentorlist = appDatabase.mentorDao().getAllMentor() as ArrayList<Mentor>
        list = ArrayList()

//        list = appDatabase.mentorDao().getAllMentorsByKursId(kurslar.id!!) as ArrayList<Mentor>

//        list = appDatabase.mentorDao().loadKursAndMentorNames() as ArrayList<Mentor

        if (mentorlist.isNotEmpty()){
            for (mentor in mentorlist) {
                if (mentor.couseId == kurslar.id) {
                    list.add(mentor)

                }
            }
        }

        groupList = appDatabase.guruhDao().getAllGroup() as ArrayList<Guruh>
        mentorsAdapter = MentorAdapter(list,groupList,object : MentorAdapter.OnItemClickListener{
            override fun onEditClick(mentor: Mentor, position: Int, linearLayout: LinearLayout) {
                val alertDialog = AlertDialog.Builder(binding.root.context)
                val dialog = alertDialog.create()
                val dialogView = MentorDialogBinding.inflate(
                    LayoutInflater.from(binding.root.context),null,false
                )

                dialogView.familya.setText(mentor.ment_famil)
                dialogView.imya.setText(mentor.ment_name)
                dialogView.otchest.setText(mentor.ment_otchest)

                dialogView.change.setOnClickListener {
                    val surName =dialogView.familya.text.toString().trim()
                    val name = dialogView.imya.text.toString().trim()
                    val fatherName = dialogView.otchest.text.toString().trim()

                    mentor.ment_famil = surName
                    mentor.ment_name = name
                    mentor.ment_otchest = fatherName

                    if (surName.isNotEmpty() && name.isNotEmpty() && fatherName.isNotEmpty()){
                       appDatabase.mentorDao().updateMentor(mentor)
                        mentorsAdapter.notifyItemChanged(position)
                        dialog.dismiss()










                    }else{
                        Toast.makeText(binding.root.context,"Malumot to'liq kiritng iltimos", Toast.LENGTH_SHORT).show()
                    }




                }

                dialogView.cancel.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.setView(dialogView.root)
                dialog.show()
            }

            var same = false
            override fun onDeleteClick(mentor: Mentor, position: Int, linearLayout: LinearLayout) {
                groupList = appDatabase.guruhDao().getAllGroup() as ArrayList<Guruh>


                for (i in 0 until groupList.size){
                        if (groupList[i].gr_mentor == mentor.ment_name){
                            mentor.guruhId = "tanlandi"
                            appDatabase.mentorDao().updateMentor(mentor)
                            same = true
                            break
                        }
                }
                if (!same) {
                    mentor.guruhId = "unchoosen"
                    appDatabase.mentorDao().updateMentor(mentor)
                }





                if (mentor.guruhId != "tanlandi"){
                    appDatabase.mentorDao().deleteMentor(mentor)
                    list.remove(mentor)
                    mentorsAdapter.notifyItemRemoved(position)
                    mentorsAdapter.notifyItemRangeChanged(position, list.size - position)
                }















            }

        })
        binding.rvMentor.adapter=mentorsAdapter
        mentorsAdapter.notifyItemInserted(list.size)
        mentorsAdapter.notifyItemChanged(list.size)

        binding.toolbarMentor.setOnMenuItemClickListener {

            if (it.itemId == R.id.add){
                var bundle = Bundle()
                bundle.putSerializable("kalit",kurslar)
                findNavController().navigate(R.id.mentorAddFragment,bundle)
            }

            true
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MentorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MentorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}