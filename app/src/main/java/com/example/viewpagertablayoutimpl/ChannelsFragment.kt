package com.example.viewpagertablayoutimpl

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.viewpagertablayoutimpl.databinding.FragmentChannelsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ChannelsFragment: Fragment() {
    companion object {
        const val TAG = "ChannelsFragment"
        fun newInstance() = ChannelsFragment()
        var isActive = false
    }

    private val iconList by lazy { mutableListOf<Int>() }
    private val pageHeadings by lazy { mutableListOf<String>() }
    private val titleList by lazy { mutableListOf<String>() }
    private val fragments by lazy { mutableListOf<Fragment>() }
    private val friendsFragment by lazy { FriendsFragment.newInstance() }
    private val handler = Handler(Looper.getMainLooper())

    private var _binding: FragmentChannelsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChannelsBinding.inflate(layoutInflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
        attachTabsToViewPager(0)
    }

    private fun attachTabsToViewPager(tabPosition: Int) {
        if (_binding == null) return
        binding.viewPager.adapter =
            ChannelsFragmentPagerAdapter(
                childFragmentManager,
                lifecycle,
                fragments
            )
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.customView = View.inflate(requireContext(), R.layout.custom_chat_tab_layout, null)
            val customView = tab.customView
            val imageView = customView?.findViewById<ImageView>(R.id.tabImageView)
            val textView = customView?.findViewById<TextView>(R.id.tabTextView)
            imageView?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    iconList[position]
                )
            )
            textView?.text = titleList[position]
        }.attach()
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { setPageTitleByPosition(it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        selectTab(tabPosition)

    }

    private fun selectTab(tabPosition: Int, afterSelected: () -> Unit = {}) {
        handler.postDelayed({
            if (_binding == null) return@postDelayed
            binding.tabLayout.getTabAt(tabPosition)?.customView
                ?.findViewById<TextView>(R.id.tabTextView)
                ?.visibility = View.VISIBLE
            binding.tabLayout.getTabAt(tabPosition)?.select()
            afterSelected()
        }, 100)
    }

    private fun setPageTitleByPosition(position: Int) {

    }

    private fun initTabs() {
        initIconList()
        initTitleList()
        initPageHeadingList()
        initFragments()
    }

    private fun initIconList() {
        iconList.add(R.drawable.ic_games)
        iconList.add(R.drawable.ic_friends_tab)
        iconList.add(R.drawable.ic_group_chat_icon)
    }

    private fun initFragments() {
        fragments.add(GamesFragment.newInstance())
        fragments.add(friendsFragment)
        fragments.add(GroupChatFragment.newInstance())
    }

    private fun initPageHeadingList() {
        pageHeadings.add(getString(R.string.title_games))
        pageHeadings.add(getString(R.string.title_chat))
        pageHeadings.add(getString(R.string.title_chat))
    }

    private fun initTitleList() {
        titleList.add(getString(R.string.games))
        titleList.add(getString(R.string.friends))
        titleList.add(getString(R.string.title_groups))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}