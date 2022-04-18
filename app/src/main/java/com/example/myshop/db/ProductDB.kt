package com.example.myshop.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myshop.Product
import com.example.myshop.daos.ProductDao

@Database(entities = [Product::class], version = 3)
abstract class ProductDB() : RoomDatabase() {
    abstract fun getDao() : ProductDao
    companion object{
        private var db : ProductDB? = null
        private val migration_1_2 : Migration = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table 'product' add column 'hot_item' integer not null default 0")
            }
        }

        private val migration_2_3 : Migration = object : Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table 'product' add column 'image' text")
            }

        }
        fun getDB(context: Context) : ProductDB{
            if(db == null){
                db = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDB::class.java,
                    "product"
                )
                .addMigrations(migration_1_2, migration_2_3)
                .build()
                return db!!
            }
            return db!!
        }
    }
}