package com.ssafy.preparetest

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ssafy.preparetest.dto.Memo

const val TABLE = "myMemo"

class MemoDBHelper(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    private lateinit var db: SQLiteDatabase

    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 생성 쿼리
        val query: String =
            "CREATE TABLE if not exists $TABLE ( _id integer primary key autoincrement, name text, content text, createdAt text, rate integer);"
        db.execSQL(query)
    }

    //  upgrade 가 필요한 경우 기존 테이블 drop 후 onCreate로 새롭게 생성
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql: String = "DROP TABLE if exists $TABLE"
        db.execSQL(sql)
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        this.db = db
    }

    fun selectAllMemo(): MutableList<Memo> {
        val db = writableDatabase
        val columns = arrayOf("_id", "name", "content", "createdAt", "rate")
        val result = arrayListOf<Memo>()
        db.query(TABLE, columns, null, null, null, null, null).use {
            while (it.moveToNext()) {
                result.add(
                    Memo(
                        it.getInt(0),
                        it.getString(1),
                        it.getString(2),
                        it.getString(3),
                        it.getInt(4)
                    )
                )
            }
        }
        /*
        * db.query(
            TABLE,
            columns,
            null,  // selection
            null,  // selectionArgs
            null,
            null,
            "rate DESC"  // ← 여기! 정렬 기준
        )
        *
        * db.query(
            TABLE,
            columns,
            "rate >= ?",  // selection
            arrayOf("3"), // selectionArgs
            null,
            null,
            "rate DESC"   // 정렬도 하고 싶으면 추가
        )
        *
        * db.query(
            TABLE,
            columns,
            "rate >= ? AND createdAt > ?",  // selection
            arrayOf("3", "2024-12-10"),     // selectionArgs
            null,
            null,
            "rate DESC"
        )
        *
        * db.query(
            TABLE,
            columns,
            "name LIKE ?",       // selection
            arrayOf("박%"),       // selectionArgs (박으로 시작하는)
            null,
            null,
            "rate DESC"
        )
        * fun selectFilteredMemos(): MutableList<Memo> {
            val db = writableDatabase
            val columns = arrayOf("_id", "name", "content", "createdAt", "rate")
            val result = arrayListOf<Memo>()

            val selection = "rate >= ? AND createdAt > ? AND name LIKE ?"
            val selectionArgs = arrayOf("3", "2024-12-10", "박%")
            val orderBy = "rate DESC"

            db.query(TABLE, columns, selection, selectionArgs, null, null, orderBy).use {
                while (it.moveToNext()) {
                    result.add(
                        Memo(
                            it.getInt(0),
                            it.getString(1),
                            it.getString(2),
                            it.getString(3),
                            it.getInt(4)
                        )
                    )
                }
            }
            return result
        }
        * */
        return result
    }

    fun getCount(): Int {
        val db = writableDatabase
        val query = "SELECT COUNT(*) FROM myMemo"
        val stmt = db.compileStatement(query)
        return stmt.simpleQueryForLong().toInt()
    }

    //    fun selectStuff(id : Int) : Stuff {
//        val db = writableDatabase
//        val columns = arrayOf("_id", "name","quantity")
//        var result = Stuff(
//            id = -1,
//            name = "temp",
//            quantity = -1
//        )
//        db.query(TABLE, columns, "_id=?", arrayOf(id.toString()), null, null, null).use{ cursor ->
//            if (cursor.moveToNext()) {
//                result = (Stuff(cursor.getInt(0), cursor.getString(1),cursor.getInt(2)))
//            }
//        }
//        return result
//    }
    fun insertMemo(dto: Memo): Long {
        val db = writableDatabase
        // ContentValues를 이용한 저장
        val contentValues = ContentValues()
        contentValues.put("name", dto.name)
        contentValues.put("content", dto.content)
        contentValues.put("createdAt", dto.createdAt)
        contentValues.put("rate", dto.rate)
        db.beginTransaction()
        val result = db.insert(TABLE, null, contentValues)

        if (result > 0) {
            db.setTransactionSuccessful()
        }
        db.endTransaction()
        return result
    }
//
//
//    fun updateStuff(dto: Stuff):Int{
//        val db = writableDatabase
//        // ContentValues를 이용한 수정
//        val contentValues = ContentValues()
//        contentValues.put("name", dto.name)
//        contentValues.put("quantity",dto.quantity)
//        db.beginTransaction()
//        val result = db.update(TABLE, contentValues, "_id=?", arrayOf(dto.id.toString()))
//        // sql을 이용한 수정
//        // val query = "update $TABLE set txt=$content where _id=$id";
//        // db.execSQL(query)
//        if (result > 0) {
//            db.setTransactionSuccessful()
//        }
//        db.endTransaction()
//        return result
//    }
//
//    fun deleteStuff(id : Int):Int{
//        val db = writableDatabase
//        db.beginTransaction()
//        val result = db.delete(TABLE, "_id=?", arrayOf(id.toString()))
//        if (result > 0) {
//            db.setTransactionSuccessful()
//        }
//        db.endTransaction()
//        return result
//    }
}
