package com.example.drinkapp.screen.admin.product_update

import android.R
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.call.CallApiCategory
import com.example.drinkapp.data.resource.call.CallApiDrink
import com.example.drinkapp.data.resource.call.CallApiPriceSize
import com.example.drinkapp.databinding.AdminActivityProductUpdateBinding
import com.example.drinkapp.screen.admin.product_add.ProductAddActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.parseFromNumberFormat
import com.example.drinkapp.utils.setMaxLength
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*

class ProductUpdateActivity :
    BaseActivity<AdminActivityProductUpdateBinding>(AdminActivityProductUpdateBinding::inflate),
    ProductUpdateContract.View {
    private lateinit var storageReference: StorageReference
    private lateinit var progressDialog: ProgressDialog
    private lateinit var product: Product
    private lateinit var presenter: ProductUpdatePresenter
    private var imageUri: Uri? = null
    private var data: List<Long>? = null
    private var cateId: Long = 0
    private var statusCode: Long = 0
    private var pricetxt: Double = 0.0


    override fun initView() {
        val status = listOf(KEY_HIDE, KEY_SHOW)
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, status)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.apply {
            textName.setMaxLength(255)
            textPrice.setMaxLength(19)
            spinnerStatus.adapter = adapter
        }

        presenter = ProductUpdatePresenter(
            this,
            CallApiCategory.getInstance(),
            CallApiDrink.getInstance(),
            CallApiPriceSize.getInstance()
        )
        presenter.getAllCategory()
    }

    override fun initData() {
        product = intent.getSerializableExtra(Constant.KEY_PRODUCT) as Product
        binding.apply {
            textName.setText(product.name)
            Glide.with(imageButton.context)
                .load(product.image)
                .into(imageButton)
            textPrice.setText(product.price.toLong().toString())
            spinnerStatus.setSelection(product.status.toInt())
            spinnerCategories.setSelection(product.category.id!!.toInt())
        }
    }

    override fun handleEvent() {
        binding.apply {
            spinnerCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    cateId = data?.get(position)!!
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    cateId = data?.get(0)!!
                }
            }
            spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    statusCode = position.toLong()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    statusCode = 0L
                }
            }
            chooseImageButton.setOnClickListener {
                selectImage()
            }

            buttonUpdateProduct.setOnClickListener {
                buttonUpdateProduct.isEnabled = false
                presenter.checkInputUpdate(
                    binding.textName.text.toString(),
                    product.image,
                    binding.textPrice.text.toString().trim()
                )
            }
            buttonBack.setOnClickListener {
                finish()
            }

        }
    }


    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && data.data != null) {
            imageUri = data.data
            binding.imageButton.setImageURI(imageUri)
        }
    }
//    private fun deleteImageFirebase(){
//
//        val storage = FirebaseStorage.getInstance()
//        val storageRef = storage.reference
//
//        val imageUri = product.image
//        val imageRef = storageRef.child(imageUri)
//
//        imageRef.delete()
//            .addOnSuccessListener {
//                uploadImageToFirebase()
//            }
//            .addOnFailureListener {
//                onFail("Failed to Delete Image in firebase")
//            }
//
//    }

    private fun uploadImageToFirebase() {
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(MESS_UPDATE_PRODUCT)
        progressDialog.show()

        val formatter = SimpleDateFormat(Constant.KEY_FORMAT_FULL_TIME, Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imageUri!!)
            .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                // Get the download URL
                storageReference.downloadUrl.addOnSuccessListener { uri ->
                    presenter.checkInputUpdate(
                        binding.textName.text.toString(),
                        uri.toString(),
                        binding.textPrice.text.toString().trim()
                    )
                }
                if (progressDialog.isShowing) progressDialog.dismiss()
            })
            .addOnFailureListener(OnFailureListener { e ->
                if (progressDialog.isShowing) progressDialog.dismiss()
                onFail(MESS_UPDATE_FAIL)
            })
    }

    fun updateProduct(uri: String) {
        presenter.updateProduct(
            product.id,
            binding.textName.text.toString(),
            uri,
            (binding.textPrice.text.toString().trim()).parseFromNumberFormat(),
            statusCode,
            cateId
        )
    }

    override fun displayAllCategory(result: List<Pair<Long, String>>) {
        data = result.map { it.first }
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, result.map { it.second })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategories.adapter = adapter
    }

    override fun onCheckInputUpdateSuccess(uri: String) {
        pricetxt = binding.textPrice.text.toString().toDouble()
        if (imageUri != null) uploadImageToFirebase()
        else updateProduct(uri)
    }

    override fun onUpdateSuccess() {
        presenter.updatePriceSizeDefault(product.id, 1, pricetxt, 1)
    }

    override fun onUpdatePriiceSizeSuccess(msg: String) {
        finish()
    }

    override fun onFail(msg: String) {
        binding.buttonUpdateProduct.isEnabled = false
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val KEY_SHOW = "hiện sản phẩm"
        private const val KEY_HIDE = "ẩn sản phẩm"
        private const val MESS_UPDATE_PRODUCT = "cập nhật sản phẩm ...."
        private const val MESS_UPDATE_FAIL = "cập nhật sản phẩm không thành công"
    }
}