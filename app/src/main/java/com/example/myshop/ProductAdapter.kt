package com.example.myshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ProductViewBinding

class ProductAdapter(val hotItemCallback : (Product) -> Unit,val callBack : (Product,RowAction) -> Unit) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffUtil()) {
    class ProductViewHolder(val binding : ProductViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun productBind(product: Product){
            binding.product = product
        }
    }

    class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.productBind(product)
        holder.binding.hotIV.setOnClickListener {
            product.hotItem = !product.hotItem
            holder.productBind(product)
            hotItemCallback(product)
        }
        val menuIV = holder.binding.menuIV
        menuIV.setOnClickListener {
            val popUpMenu = PopupMenu(menuIV.context, menuIV)
            popUpMenu.menuInflater.inflate(R.menu.menu_item, popUpMenu.menu)
            popUpMenu.show()
            popUpMenu.setOnMenuItemClickListener {
                val action = when(it.itemId){
                    R.id.edit -> RowAction.EDIT
                    R.id.delete -> RowAction.DELETE
                    else -> RowAction.NONE
                }
                callBack(product,action)
                true
            }
        }
    }
}

enum class RowAction{
    EDIT, DELETE, NONE
}

