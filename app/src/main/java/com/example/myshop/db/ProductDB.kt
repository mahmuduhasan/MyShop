package com.example.myshop.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myshop.Product
import com.example.myshop.daos.ProductDao
@Database(entities = [Product::class], version = 1)
abstract class ProductDB() : RoomDatabase() {
    abstract fun getDao() : ProductDao
    companion object{
        private var db : ProductDB? = null
        fun getDB(context: Context) : ProductDB{
            if(db == null){
                db = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDB::class.java,
                    "product"
                ).allowMainThreadQueries().build()
                return db!!
            }
            return db!!
        }
    }
}