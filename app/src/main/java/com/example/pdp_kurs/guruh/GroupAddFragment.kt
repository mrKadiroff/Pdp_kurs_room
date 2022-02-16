package com.example.pdp_kurs.guruh

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pdp_kurs.R
import com.example.pdp_kurs.adapters.Mentor_Spinner
import com.example.pdp_kurs.database.AppDatabase
import com.example.pdp_kurs.databinding.FragmentGroupAddBinding
import com.example.pdp_kurs.entity.Guruh
import com.example.pdp_kurs.entity.Kurs
import com.example.pdp_kurs.entity.Mentor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupAddFragment : Fragment() {
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
    lateinit var binding: FragmentGroupAddBinding
    lateinit var mentorSpinner: Mentor_Spinner
    lateinit var mentorList: List<Mentor>
    lateinit var list: ArrayList<Mentor>
    lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupAddBinding.inflate(layoutInflater,container,false)
        appDatabase = AppDatabase.getInstance(binding.root.context)

        setUi()
        setMentor()
        setTime()
        setDay()


        val kurslar = arguments?.getSerializable("guruhbek") as Kurs



        binding.saqlash.setOnClickListener {

            val name = binding.grNomi.text.toString().trim()
            val time = binding.timee.selectedItem.toString()
            val day = binding.days.selectedItem.toString()

            if (!mentorList.isNullOrEmpty() && name.isNotEmpty()){
                val guruh = Guruh()

                val gr_kurs_id = kurslar.id


                guruh.gr_name = binding.grNomi.text.toString().trim()
                guruh.gr_kurs_id = gr_kurs_id
                guruh.gr_mentor = list[binding.mentorName.selectedItemPosition].ment_name
                guruh.gr_time = time
                guruh.gr_day = day
                guruh.gr_open = "closed"


                appDatabase.guruhDao().addGuruh(guruh)

//                    for (i in 0 until mentorList.size){
//                        if (mentorList[i].ment_name == guruh.gr_mentor){
//                            Toast.makeText(binding.root.context, "it is equal", Toast.LENGTH_LONG).show()
//                            val mentor = Mentor()
//                            mentor.guruhId = "tanlandi"
//                            appDatabase.mentorDao().updateMentor(mentor)
//                        }
//                    }


                findNavController().popBackStack()
            }else{
                Toast.makeText(binding.root.context,"Malumot to'liq kiritng iltimos", Toast.LENGTH_SHORT).show()
            }


        }




        return binding.root
    }



    private fun setDay() {
        val adapter_kun = ArrayAdapter<String>(binding.root.context,R.layout.spinner_group,resources.getStringArray(R.array.kunlar))
        adapter_kun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.days.adapter = adapter_kun
    }

    private fun setTime() {
        val adapter = ArrayAdapter<String>(binding.root.context,R.layout.spinner_group,resources.getStringArray(R.array.vaqt))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.timee.adapter = adapter
    }

    private fun setUi() {
        val kurslar = arguments?.getSerializable("guruhbek") as Kurs
        binding.toolbarGr.title = kurslar.kr_name

        binding.toolbarGr.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setMentor() {
        val kurslar = arguments?.getSerializable("guruhbek") as Kurs
        mentorList = appDatabase.mentorDao().getAllMentor() as ArrayList<Mentor>
        list = ArrayList()
        if (mentorList.isNotEmpty()){
            for (mentor in mentorList){
                if (mentor.couseId == kurslar.id) {
                    list.add(mentor)
                }
            }
        }


        mentorSpinner = Mentor_Spinner(list)
        binding.mentorName.adapter = mentorSpinner


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}