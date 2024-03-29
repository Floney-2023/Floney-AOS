package com.aos.floney.view.onboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardViewPaperAdapter(activity: FragmentActivity) :FragmentStateAdapter(activity) {

    val fragments : List<Fragment>

    init {
        fragments = listOf(OnBoardRecordFragment() ,OnBoardShowFlowFragment(), OnBoardStartFragment())
    }
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}