package com.bangkit.githubuserapplication.presentation.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String):FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply{
            putInt(FollowFragment.SECTION_NUMBER, position+1)
            putString(FollowFragment.USERNAME_EXTRA, username)
        }
        return fragment
    }
}