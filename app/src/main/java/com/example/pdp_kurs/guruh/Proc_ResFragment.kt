package com.example.pdp_kurs.guruh

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pdp_kurs.R
import com.example.pdp_kurs.adapters.Talaba_Adapter
import com.example.pdp_kurs.database.AppDatabase
import com.example.pdp_kurs.databinding.FragmentProcResBinding
import com.example.pdp_kurs.entity.Guruh
import com.example.pdp_kurs.entity.Talaba

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Proc_ResFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Proc_ResFragment : Fragment() {
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
    lateinit var talabaAdapter: Talaba_Adapter
    lateinit var talabaList:ArrayList<Talaba>
    lateinit var list:ArrayList<Talaba>
    lateinit var binding: FragmentProcResBinding
    lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProcResBinding.inflate(layoutInflater,container, false)
        appDatabase = AppDatabase.getInstance(binding.root.context)

        val guruuh = arguments?.getSerializable("pro") as Guruh

        val yet = arguments?.getString("yet")
        val start = arguments?.getString("start")
        binding.tooolbarchik.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.groupName.text = guruuh.gr_name
        binding.groupTime.text = guruuh.gr_time
        binding.tooolbarchik.title = guruuh.gr_name

        talabaList= appDatabase.talabaDao().getAllTalaba() as ArrayList<Talaba>
        list = ArrayList()

        if (talabaList.isNotEmpty()){
            for (talaba in talabaList) {
                if (talaba.tl_group == guruuh.id) {
                    list.add(talaba)
                }
            }
        }

        binding.groupNumber.text = "O'quvchilar soni: ${list.size.toString()} ta"

        talabaAdapter = Talaba_Adapter(list,object : Talaba_Adapter.OnItemClickListener{
            override fun onEditClick(talaba: Talaba, position: Int, linearLayout: LinearLayout) {
                var bundle = Bundle()
                bundle.putString("edit","edit")
                bundle.putSerializable("talaba",guruuh)
                bundle.putSerializable("student",talaba)
                findNavController().navigate(R.id.group_Student_AddFragment,bundle)
            }

            override fun onDeleteClick(talaba: Talaba, position: Int, linearLayout: LinearLayout) {
                val builder = AlertDialog.Builder(binding.root.context)
                builder.setMessage("Ushbu o'quvchini rostan o'chirmoqchimisiz?")
                builder.setPositiveButton("Ha",{ dialogInterface: DialogInterface, i: Int ->
                    appDatabase.talabaDao().deleteTalaba(talaba)
                    list.remove(talaba)
                    talabaAdapter.notifyItemRemoved(position)
                    talabaAdapter.notifyItemRangeChanged(position, list.size - position)
                    binding.groupNumber.text = "O'quvchilar soni: ${list.size.toString()} ta"
                })
                builder.setNegativeButton("Yo'q", { dialogInterface: DialogInterface, i: Int ->

                })
                builder.show()
            }

        })

        binding.talabalar.adapter = talabaAdapter

        if (yet!=null) {
            binding.tooolbarchik.inflateMenu(R.menu.add_menu)
            binding.tooolbarchik.setOnMenuItemClickListener {
                if (it.itemId == R.id.add) {
                    var bundle = Bundle()
                    bundle.putString("qosh", "qosh")
                    bundle.putSerializable("talaba", guruuh)
                    findNavController().navigate(R.id.group_Student_AddFragment, bundle)
                }
                true
            }

            // rv yozish
            binding.boshla.setOnClickListener {

                if (list.isNotEmpty()){
                    guruuh.gr_open = "start"
                    appDatabase.guruhDao().updateGuruh(guruuh)
                    binding.boshla.visibility = View.GONE
                    findNavController().popBackStack()
                }else{
                    Toast.makeText(binding.root.context,"Talaba qo'shing brat",Toast.LENGTH_SHORT).show()
                }



            }
        }

        if (start!=null){
            binding.boshla.visibility = View.GONE
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
         * @return A new instance of fragment Proc_ResFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Proc_ResFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}