package com.example.myshop

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.FragmentProductBinding
import com.example.myshop.dialogs.AlertShowDialog
import com.example.myshop.models.ProductModel
import com.google.android.material.snackbar.Snackbar

class ProductFragment : Fragment() {
    private val modelView : ProductModel by activityViewModels()
    private lateinit var binding : FragmentProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater,container,false)
        val anim = binding.shopAnimation
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            anim.playAnimation()
        }, 2000)
        val adapter = ProductAdapter(::onClickMenuItem)
        binding.productRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.productRecycleView.adapter = adapter
        modelView.getAllProduct().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.productRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy>0){
                    binding.addProduct.visibility = View.GONE
                }
                if(dy<0){
                    binding.addProduct.visibility = View.VISIBLE
                }
            }
        })

        binding.addProduct.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_uploadProductFragment)
        }
        return binding.root
    }

    private fun onClickMenuItem(product: Product, action: RowAction){
        when(action){
            RowAction.EDIT -> {

            }
            RowAction.DELETE -> {
                AlertShowDialog(
                    title = "Delete ${product.name}",
                    body = "Are you sure to delete?",
                    positiveText = "Yes",
                    negativeText = "Cancel"
                ){
                    modelView.deleteProduct(product)
                    val snackbar = Snackbar.make(binding.productRecycleView, "Wanna change your mind?", Snackbar.LENGTH_LONG)
                    snackbar.setAction("UNDO!"){
                        modelView.addProduct(product)
                    }
                    snackbar.show()
                }.show(childFragmentManager,null)
            }
            else -> {

            }
        }
    }
}