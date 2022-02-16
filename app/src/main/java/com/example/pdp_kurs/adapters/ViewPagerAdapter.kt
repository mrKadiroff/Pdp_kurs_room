package com.example.pdp_kurs.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pdp_kurs.entity.Kurs
import com.example.pdp_kurs.guruh.ProccessFragment
import com.example.pdp_kurs.guruh.StartFragment

class ViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle, var kurs: Kurs): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                StartFragment.newInstance(kurs)
            }
            1->{
                ProccessFragment.newInstance(kurs)
            }
            else->{
                Fragment()
            }
        }
    }


}