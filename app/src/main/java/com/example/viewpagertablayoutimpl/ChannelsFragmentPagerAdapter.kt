package com.example.viewpagertablayoutimpl

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ChannelsFragmentPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragmentsList: List<Fragment>
): FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object{
            const val POS_GAMES = 0
            const val POS_FRIENDS = 1
            const val POS_CHAT = 2
    }

    override fun getItemCount(): Int = fragmentsList.size

    override fun createFragment(position: Int): Fragment = fragmentsList[position]
}