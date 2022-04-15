package com.example.myshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.databinding.FragmentUploadProductBinding
import com.example.myshop.dialogs.DatePickerDialog
import com.example.myshop.dialogs.TimePickerDialog
import com.example.myshop.models.ProductModel

class UploadProductFragment : Fragment() {
    private val modelView : ProductModel by activityViewModels()
    private lateinit var binding : FragmentUploadProductBinding
    private var category = "Grocery"
    private var id : Long? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUploadProductBinding.inflate(inflater,container,false)
        categorySpinner()
        id = arguments?.getLong("id")
        if(id != null){
            binding.uploadButton.setText("Update")
            modelView.getProductById(id!!).observe(viewLifecycleOwner){
                binding.productName.setText(it.name)
                binding.timeShow.text = it.stockTime
                binding.dateView.text = it.stockDate
                binding.productQuantity.setText("${it.quantity}")
                binding.productPrice.setText("${it.price}")
                val index = categoryList.indexOf(it.category)
                binding.productCategory.setSelection(index)
            }
        }
        binding.dateButton.setOnClickListener {
            DatePickerDialog{date ->
                binding.dateView.text = date
            }.show(childFragmentManager,null)
        }

        binding.timeButton.setOnClickListener {
            TimePickerDialog{time ->
                binding.timeShow.text = time
            }.show(childFragmentManager, null)
        }

        binding.uploadButton.setOnClickListener {
            saveInfo()
        }

        return binding.root
    }

    private fun saveInfo() {
        val productName = binding.productName.text.toString()
        val productPrice = binding.productPrice.text.toString().toDouble()
        val productQuantity = binding.productQuantity.text.toString().toInt()
        val stockedDate = binding.dateView.text.toString()
        val stockedTime = binding.timeShow.text.toString()

        val newProduct = Product(
            name = productName,
            price = productPrice,
            quantity = productQuantity,
            category = category,
            stockDate = stockedDate,
            stockTime = stockedTime
        )

        if(id != null){
            newProduct.id = id!!
            modelView.updateProduct(newProduct)
        }else{
            modelView.addProduct(newProduct)
        }



        findNavController().navigate(R.id.action_uploadProductFragment_to_productFragment)

    }

    private fun categorySpinner() {
        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            categoryList
        )

        binding.productCategory.adapter = adapter

        binding.productCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    category = p0?.getItemAtPosition(p2).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
    }

}

val categoryList = listOf("Grocery", "Drink", "Fruit", "Vegetable", "Makeup", "Health")