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
import com.example.myshop.databinding.FragmentProductBinding
import com.example.myshop.models.ProductModel

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
        val adapter = ProductAdapter()
        binding.productRecycleView.layoutManager = LinearLayoutManager(activity)
        binding.productRecycleView.adapter = adapter
        modelView.getAllProduct().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.addProduct.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_uploadProductFragment)
        }
        return binding.root
    }

}