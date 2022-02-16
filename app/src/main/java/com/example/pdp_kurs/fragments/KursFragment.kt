package com.example.pdp_kurs.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pdp_kurs.R
import com.example.pdp_kurs.adapters.KursAdapter
import com.example.pdp_kurs.database.AppDatabase
import com.example.pdp_kurs.databinding.FragmentKursBinding
import com.example.pdp_kurs.databinding.MyDialogBinding
import com.example.pdp_kurs.entity.Kurs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KursFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KursFragment : Fragment() {
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
    lateinit var binding: FragmentKursBinding
    lateinit var kurslist:ArrayList<Kurs>
    lateinit var appDatabase: AppDatabase
    lateinit var kursAdapter: KursAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKursBinding.inflate(layoutInflater,container,false)

        val kurs = arguments?.getString("kurs")
        appDatabase = AppDatabase.getInstance(binding.root.context)



        kurslist = ArrayList()
       kurslist = appDatabase.kursDao().getAllNews() as ArrayList<Kurs>



        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        if (kurs!=null){
            binding.toolbar.inflateMenu(R.menu.add_menu)
            kursAdapter = KursAdapter(kurslist, object : KursAdapter.OnItemClickListener{
                override fun onItemClick(kurs: Kurs) {
                    var bundle = Bundle()
                    bundle.putSerializable("kursi",kurs)
                    findNavController().navigate(R.id.kursChildFragment,bundle)
                }

            })
            binding.rvKurs.adapter = kursAdapter
            binding.toolbar.setOnMenuItemClickListener {
                if (it.itemId==R.id.add){
                    val alertDialog = AlertDialog.Builder(binding.root.context)
                    val dialog = alertDialog.create()
                    val dialogView =
                        MyDialogBinding.inflate(LayoutInflater.from(binding.root.context),null,false)


                    var same = false
                    dialogView.saveText.setOnClickListener {
                        val kurs = Kurs()
                        val name = dialogView.sarlavha.text.toString().trim()
                        val description = dialogView.matn.text.toString().trim()

                        kurs.kr_name = name
                        kurs.kr_des = description
                        appDatabase.kursDao().addNews(kurs)
                        kurslist.add(kurs)
                        kursAdapter.notifyItemInserted(kurslist.size)
                        dialog.dismiss()








                    }

                    dialogView.notText.setOnClickListener {
                        dialog.dismiss()
                    }


                    dialog.setView(dialogView.root)
                    dialog.show()
                }
                true
            }






        }
        val gruppa = arguments?.getString("guruh")
        if (gruppa!=null) {
            kursAdapter = KursAdapter(kurslist,object : KursAdapter.OnItemClickListener{
                override fun onItemClick(kurs: Kurs) {
                    var bundle = Bundle()
                    bundle.putSerializable("gruh",kurs)
                    findNavController().navigate(R.id.viewPagerFragment,bundle)
                }

            })
            binding.rvKurs.adapter = kursAdapter
            kursAdapter.notifyItemInserted(kurslist.size)
        }



        val mentor = arguments?.getString("mentor")
        if (mentor!=null){
            kursAdapter = KursAdapter(kurslist,object : KursAdapter.OnItemClickListener{
                override fun onItemClick(kurs: Kurs) {
                    var bundle = Bundle()
                    bundle.putSerializable("kursi",kurs)
                    findNavController().navigate(R.id.action_kursFragment_to_mentorFragment,bundle)
                }

            })
            binding.rvKurs.adapter = kursAdapter
            kursAdapter.notifyItemInserted(kurslist.size)

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
         * @return A new instance of fragment KursFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KursFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}