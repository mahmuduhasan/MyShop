package com.example.myshop.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myshop.Product

@Dao
interface ProductDao {
    @Insert
    suspend fun addProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("select * from product")
    fun getAllProduct() : LiveData<List<Product>>
}