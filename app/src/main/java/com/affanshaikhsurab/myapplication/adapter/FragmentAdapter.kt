package com.affanshaikhsurab.myapplication.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.affanshaikhsurab.myapplication.Information_Fragment
import com.affanshaikhsurab.myapplication.carrersList.coursesList

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Information_Fragment()
            else -> coursesList()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}