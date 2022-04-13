package com.example.myshop.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.Product
import com.example.myshop.db.ProductDB
import com.example.myshop.repositories.ProductRepository
import kotlinx.coroutines.launch

class ProductModel(application: Application) : AndroidViewModel(application) {
    private lateinit var repository: ProductRepository
    init {
        val dao = ProductDB.getDB(application).getDao()
        repository = ProductRepository(dao)
    }

    fun addProduct(product : Product){
        viewModelScope.launch {
            repository.addProduct(product)
        }
    }

    fun updateProduct(product: Product){
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product){
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    fun getAllProduct() : LiveData<List<Product>> = repository.getAllProduct()
}