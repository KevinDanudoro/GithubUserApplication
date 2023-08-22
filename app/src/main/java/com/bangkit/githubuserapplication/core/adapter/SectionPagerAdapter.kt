package com.bangkit.githubuserapplication.core.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.githubuserapplication.ui.detail.FollowFragment

class SectionPagerAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply{
            putInt(FollowFragment.SECTION_NUMBER, position+1)
        }
        return fragment
    }
}