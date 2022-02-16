package com.example.pdp_kurs.guruh

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
import com.example.pdp_kurs.adapters.Group_Rv
import com.example.pdp_kurs.adapters.Mentor_Spinner
import com.example.pdp_kurs.adapters.TimeSpinner
import com.example.pdp_kurs.database.AppDatabase
import com.example.pdp_kurs.databinding.FragmentProccessBinding
import com.example.pdp_kurs.databinding.MyEditDialogBinding
import com.example.pdp_kurs.entity.Guruh
import com.example.pdp_kurs.entity.Kurs
import com.example.pdp_kurs.entity.Mentor
import com.example.pdp_kurs.entity.Talaba

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [ProccessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProccessFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Kurs? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Kurs

        }
    }
    lateinit var binding: FragmentProccessBinding
    lateinit var groupRv: Group_Rv
    lateinit var groupList:ArrayList<Guruh>
    lateinit var appDatabase: AppDatabase
    lateinit var list: ArrayList<Guruh>
    lateinit var mentorSpinner: Mentor_Spinner
    lateinit var list1: ArrayList<Mentor>
    lateinit var mentorList: List<Mentor>
    lateinit var timeSpinner: TimeSpinner
    lateinit var t_List: ArrayList<String>
    lateinit var talabaList:ArrayList<Talaba>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProccessBinding.inflate(layoutInflater,container, false)
        appDatabase = AppDatabase.getInstance(binding.root.context)


        talabaList = appDatabase.talabaDao().getAllTalaba() as ArrayList<Talaba>

        //mentor spinner
        mentorList = appDatabase.mentorDao().getAllMentor() as ArrayList<Mentor>
        list1 = ArrayList()


        if (mentorList.isNotEmpty()){
            for (mentor in mentorList){
                if (mentor.couseId == param1?.id) {
                    list1.add(mentor)
                }
            }
        }
        mentorSpinner = Mentor_Spinner(list1)




        groupList = appDatabase.guruhDao().getAllGroup() as ArrayList<Guruh>
        list = ArrayList()
        if (param1 != null) {
            for (guruh in groupList) {
                if (guruh.gr_kurs_id == param1?.id && guruh.gr_open == "closed") {
                    list.add(guruh)
                }
            }
        }


        //Time spinner bilan ishlash
        t_List = ArrayList()

        t_List.add("16:30 - 18:30")
        t_List.add("19:00 - 21:00")
        timeSpinner = TimeSpinner(t_List)

        groupRv = Group_Rv(list,talabaList,object : Group_Rv.OnItemClickListener{
            override fun onItemStartClick(guruh: Guruh, position: Int, linearLayout: LinearLayout) {
                var bundle = Bundle()
                bundle.putString("yet", "yet")
                bundle.putSerializable("pro",guruh)
                bundle.putSerializable("krus",param1)
                findNavController().navigate(R.id.proc_ResFragment,bundle)
            }

            override fun onItemEditClick(guruh: Guruh, position: Int, linearLayout: LinearLayout) {
                val alertDialog = AlertDialog.Builder(binding.root.context)
                val dialog = alertDialog.create()
                val dialogView = MyEditDialogBinding.inflate(
                    LayoutInflater.from(binding.root.context),
                    null,
                    false)
                dialogView.vaqt.setSelection(position)
                dialogView.mentor.adapter = mentorSpinner
                dialogView.vaqt.adapter = timeSpinner

                //settext
                dialogView.guruh.setText(guruh.gr_name)



                var indexMentor = -1
                for (i in 0 until list1.size){
                    if (list1[i].ment_name == guruh.gr_mentor){
                        indexMentor = i
                        break
                    }
                }
                dialogView.mentor.setSelection(indexMentor)




                var indexTime = -1
                for (i in 0 until t_List.size){
                    if (t_List[i] == guruh.gr_time) {
                        indexTime = i
                        break
                    }
                }
                dialogView.vaqt.setSelection(indexTime)

                //update
                dialogView.saveText.setOnClickListener {
                    val name = dialogView.guruh.text.toString().trim()

                    var time = dialogView.vaqt.selectedItem.toString().trim()

                    if (!list1.isNullOrEmpty() && name.isNotEmpty() && time.isNotEmpty()){
                        val mantor = list1[dialogView.mentor.selectedItemPosition].ment_name
                        guruh.gr_name = dialogView.guruh.text.toString().trim()
                        guruh.gr_mentor = mantor
                        guruh.gr_time = dialogView.vaqt.selectedItem.toString().trim()
                        appDatabase.guruhDao().updateGuruh(guruh)
                        list[position] = guruh
                        groupRv.notifyDataSetChanged()
                        groupRv.notifyItemChanged(position)
                        dialog.dismiss()
                    }else{
                        Toast.makeText(binding.root.context,"Malumot to'liq kiritng iltimos", Toast.LENGTH_SHORT).show()
                    }

                }
                dialogView.notText.setOnClickListener {
                    dialog.dismiss()
                }


                dialog.setView(dialogView.root)
                dialog.show()
            }

            override fun onItemDeleteClick(
                guruh: Guruh,
                position: Int,
                linearLayout: LinearLayout
            ) {
                appDatabase.guruhDao().deleteGuruh(guruh)
                list.remove(guruh)
                groupRv.notifyItemRemoved(position)
                groupRv.notifyItemRangeChanged(position, list.size - position)
            }

        })

        binding.mrRv.adapter = groupRv
        groupRv.notifyItemInserted(list.size)

            return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProccessFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Kurs) =
            ProccessFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}