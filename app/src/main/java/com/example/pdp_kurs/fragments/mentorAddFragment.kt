package com.example.pdp_kurs.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pdp_kurs.R
import com.example.pdp_kurs.database.AppDatabase
import com.example.pdp_kurs.databinding.FragmentMentorAddBinding
import com.example.pdp_kurs.entity.Kurs
import com.example.pdp_kurs.entity.Mentor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [mentorAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class mentorAddFragment : Fragment() {
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
    lateinit var binding: FragmentMentorAddBinding
    lateinit var appDatabase: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMentorAddBinding.inflate(layoutInflater,container, false)
        appDatabase = AppDatabase.getInstance(binding.root.context)
        var kurss = arguments?.getSerializable("kalit") as Kurs







        binding.toolbarMentor.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        binding.add.setOnClickListener {
            val familiya = binding.familyasi.text.toString().trim()
            val ismi = binding.ismi.text.toString().trim()
            val otchestva = binding.otchestva.text.toString().trim()
            val mentor = Mentor()
            mentor.ment_famil = familiya
            mentor.ment_name = ismi
            mentor.ment_otchest = otchestva
            mentor.couseId = kurss.id
            mentor.guruhId = "unchoosen"
            appDatabase.mentorDao().addMentor(mentor)
            findNavController().popBackStack()
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
         * @return A new instance of fragment mentorAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            mentorAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}