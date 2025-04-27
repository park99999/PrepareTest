package com.ssafy.preparetest

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ssafy.preparetest.dto.Memo

class BoundService : Service() {

    private val binder = MyLocalBinder()
    private lateinit var memoDBHelper: MemoDBHelper
    override fun onBind(intent: Intent): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        memoDBHelper = MemoDBHelper(this,"myMemo",null,1)
    }
    inner class MyLocalBinder : Binder() {
        fun getService(): BoundService {
            return this@BoundService
        }
    }
    fun getAllMemos(): MutableList<Memo> {
        return memoDBHelper.selectAllMemo()
    }
//    fun select(id : Int) : Stuff
//    {
//        return stuffDBHelper.selectStuff(id)
//    }
//    fun update(dto : Stuff) : Int{
//        return stuffDBHelper.updateStuff(dto)
//    }
    fun insert(dto : Memo) : Long {
        return memoDBHelper.insertMemo(dto)
    }
//    fun delete(id : Int) : Int{
//        return stuffDBHelper.deleteStuff(id)
//    }


}