package com.ssafy.preparetest

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ssafy.preparetest.databinding.ActivityMainBinding
import com.ssafy.preparetest.dto.Memo

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var myService: BoundService
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            val binder = service as BoundService.MyLocalBinder
            myService = binder.getService()

            changeFragmentView(A_FRAGMENT)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_1 -> changeFragmentView(A_FRAGMENT)
                R.id.action_2 -> changeFragmentView(B_FRAGMENT)
            }
            true
        }



    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, BoundService::class.java)
        bindService(intent,connection, BIND_AUTO_CREATE)
    }
    fun changeFragmentView(fragment: Int, memo: Memo? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        when (fragment) {
            A_FRAGMENT -> {
                if (memo != null) {
                    transaction.replace(binding.frameLayout.id, AFragment.newInstance(memo)).commit()
                } else {
                    transaction.replace(binding.frameLayout.id, AFragment()).commit()
                }
            }
            B_FRAGMENT -> {
                transaction.replace(binding.frameLayout.id, BFragment()).commit()
            }
            C_FRAGMENT -> {
                transaction.replace(binding.frameLayout.id, AFragment())
                transaction.commit()
            }
            D_FRAGMENT -> {
                // 두번 째 프래그먼트 호출
//                val fragment2 = StuffEditFragment.newInstance(stuff, position, actionFlag)
//                transaction.replace(binding.frameLayout.id, fragment2)
//                transaction.commit()
            }
        }
    }

    companion object {
        const val A_FRAGMENT = 1
        const val B_FRAGMENT = 2
        const val C_FRAGMENT = 3
        const val D_FRAGMENT = 4
    }

}