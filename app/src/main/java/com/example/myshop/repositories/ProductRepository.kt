package com.example.myshop.repositories

import androidx.lifecycle.LiveData
import com.example.myshop.Product
import com.example.myshop.daos.ProductDao

class ProductRepository (val productDao: ProductDao) {
    suspend fun addProduct(product: Product){
        productDao.addProduct(product)
    }

    suspend fun updateProduct(product: Product){
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product){
        productDao.deleteProduct((product))
    }

    fun getAllProduct() : LiveData<List<Product>> = productDao.getAllProduct()
}