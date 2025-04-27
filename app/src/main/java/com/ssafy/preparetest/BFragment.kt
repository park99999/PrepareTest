package com.ssafy.preparetest

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.preparetest.databinding.FragmentABinding
import com.ssafy.preparetest.databinding.FragmentBBinding

class BFragment : Fragment() {
    private lateinit var memoAdapter: MemoAdapter
    private var _binding : FragmentBBinding? = null
    private val binding : FragmentBBinding
        get() = _binding!!
    private lateinit var myService: BoundService
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View {
        _binding = FragmentBBinding.inflate(inflater, container, false)

        myService = (activity as MainActivity).myService
        val stuffs = myService.getAllMemos()
        memoAdapter = MemoAdapter(requireContext(), stuffs)
        memoAdapter.setItemClickListener(object : MemoAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, productId:Int) {
                val selectedMemo = memoAdapter.prodList[position]
                (activity as MainActivity).changeFragmentView(1,selectedMemo)
            }
        })

        //adapter1 = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, stuffs)
        //binding.listview1.adapter = adapter1
        //binding.listview1.setOnItemClickListener { parent, view, position, id ->
//            val selectedStuff = parent.getItemAtPosition(position) as Stuff
//            selectedStuff.id?.let {
//                (activity as MainActivity).changeFragmentView(MainActivity.STUFF_EDIT_FRAGMENT,selectedStuff,
//                    it,0)
//            }
//        }
//
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = memoAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}