package com.example.lab10_demo_navigation_drawer_et_fragments

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment

class FragmentList : ListFragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)

        val items = arrayOf(
            "Item 1", "Item 2", "Item 3", "Item 4",
            "Item 5", "Item 6", "Item 7", "Item 8",
            "Item 9", "Item 10"
        )

        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            items
        )
        listAdapter = adapter
    }
}