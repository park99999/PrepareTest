package com.ssafy.preparetest

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ssafy.preparetest.databinding.FragmentABinding
import com.ssafy.preparetest.dto.Memo

class AFragment : Fragment() {
    //private lateinit var adapter1 : ArrayAdapter<Stuff>
    private var memo: Memo? = null
    private var _binding : FragmentABinding? = null
    private val binding : FragmentABinding
        get() = _binding!!
    private lateinit var myService: BoundService
    private lateinit var date : String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View {
        _binding = FragmentABinding.inflate(inflater, container, false)
        myService = (activity as MainActivity).myService

        //val memos = myService.getAllMemos()
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
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // month는 0부터 시작하니까 +1 해줘야 해!
            val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)

            // 예를 들면 TextView에 표시할 수도 있고
            date= selectedDate

            // 혹은 바로 저장하거나 다른 작업해도 됨
        }
        binding.fab.setOnClickListener {
            val name = binding.editTextText.text.toString()
            val content = binding.editTextText2.text.toString()
            val rate = binding.ratingBar.rating.toInt()
            myService.insert(Memo(null,name,content,date,rate))
            Toast.makeText(requireContext(),"저장",Toast.LENGTH_SHORT).show()
            //(activity as MainActivity).changeFragmentView(MainActivity.B_FRAGMENT)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        memo = arguments?.getParcelable(ARG_MEMO)
        binding.editTextText.setText(memo?.name ?: "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        private const val ARG_MEMO = "memo"

        fun newInstance(memo: Memo): AFragment {
            val fragment = AFragment()
            val args = Bundle()
            args.putParcelable(ARG_MEMO, memo)
            fragment.arguments = args
            return fragment
        }
    }
}
