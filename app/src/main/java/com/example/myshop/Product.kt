package com.example.myshop

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    val name : String,
    val quantity : Int,
    val price: Double,
    val category : String,
    @ColumnInfo(name = "stocked_date")
    val stockDate : String,
    @ColumnInfo(name = "stocked_time")
    val stockTime : String,
    @ColumnInfo(name = "hot_item")
    var hotItem : Boolean = false,
    var image : String? = null
)

