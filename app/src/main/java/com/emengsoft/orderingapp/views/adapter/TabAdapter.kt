package com.emengsoft.orderingapp.views.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */
 
class TabAdapter(mFragmentManager: FragmentManager) : FragmentPagerAdapter(mFragmentManager) {

    private val fragments = arrayListOf<Fragment?>()
    private val titles = arrayListOf<String?>()

    override fun getItem(position: Int): Fragment? = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    // set tab to array
    fun setTab(fragment: Fragment?, title: String?) {
        fragments.add(fragment)
        titles.add(title)
    }

}