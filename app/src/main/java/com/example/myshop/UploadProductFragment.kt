package com.example.myshop

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.databinding.FragmentUploadProductBinding
import com.example.myshop.dialogs.DatePickerDialog
import com.example.myshop.dialogs.TimePickerDialog
import com.example.myshop.models.ProductModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UploadProductFragment : Fragment() {
    val REQUEST_IMAGE_CAPTURE = 1
    private val modelView : ProductModel by activityViewModels()
    private lateinit var binding : FragmentUploadProductBinding
    private var category = "Grocery"
    private var id : Long? = null
    private var currentPhotoPath: String? = null
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                binding.uploadButton.tag = it.hotItem
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
        binding.uploadImageButton.setOnClickListener {
            dispatchTakePictureIntent()
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
            stockTime = stockedTime,
            image = currentPhotoPath
        )

        if(id != null){
            newProduct.id = id!!
            newProduct.hotItem = binding.uploadButton.tag as Boolean
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
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.example.myshop.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            binding.showImageIV.setImageURI(Uri.parse(currentPhotoPath))
        }
    }


}

val categoryList = listOf("Grocery", "Drink", "Fruit", "Vegetable", "Makeup", "Health")