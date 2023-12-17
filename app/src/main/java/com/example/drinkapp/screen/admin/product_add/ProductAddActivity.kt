package com.example.drinkapp.screen.admin.product_add

import android.Manifest
import android.R
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.call.CallApiCategory
import com.example.drinkapp.data.resource.call.CallApiDrink
import com.example.drinkapp.data.resource.call.CallApiPriceSize
import com.example.drinkapp.databinding.AdminActivityProductAddBinding
import com.example.drinkapp.screen.admin.product.ProductActivity
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

class ProductAddActivity :
    BaseActivity<AdminActivityProductAddBinding>(AdminActivityProductAddBinding::inflate),
    ProductAddContract.View {

    private lateinit var presenter: ProductAddPresenter
    private var imageUri: Uri? = null
    private lateinit var storageReference: StorageReference
    private lateinit var progressDialog: ProgressDialog
    private var data: List<Long>? = null
    private var cateId: Long = 0
    private var statusCode: Long = 1

    override fun initView() {
        val status = listOf(KEY_SHOW,KEY_HIDE)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, status)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.apply {
            textName.setMaxLength(255)
            textPrice.setMaxLength(19)
            spinnerStatus.adapter = adapter
        }
    }

    override fun initData() {
        presenter =
            ProductAddPresenter(
                this, CallApiDrink.getInstance(),
                CallApiCategory.getInstance(),
                CallApiPriceSize.getInstance()
            )
        presenter.getAllCategory()
    }

    override fun handleEvent() {
        binding.apply {

            chooseImageButton.setOnClickListener {
                selectImage()
            }
            buttonAddProduct.setOnClickListener {
                buttonAddProduct.isEnabled = false
                presenter.checkInputAdd(binding.textName.text.toString(),
                    imageUri,
                    binding.textPrice.text.toString().trim())
            }
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
                    if(position == 0) statusCode = 1L
                    else statusCode = 0L
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    statusCode = 1L
                }
            }
            buttonBack.setOnClickListener {
                finish()
            }

        }

    }

    private fun uploadImageToFirebase() {
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(" Adding Products....")
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imageUri!!)
            .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                // Get the download URL
                storageReference.downloadUrl.addOnSuccessListener { uri ->
                    addProduct(uri.toString())
                }
                if (progressDialog.isShowing) progressDialog.dismiss()
            })
            .addOnFailureListener(OnFailureListener { e ->
                if (progressDialog.isShowing) progressDialog.dismiss()
                onFail( "Failed to Upload")
            })
    }

    private fun selectImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    ProductAddActivity.PERMISSION_REQUEST_CODE
                )
            } else {
                openImagePicker()
            }
        } else {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ProductAddActivity.PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                // Permission denied. Handle it as appropriate for your app.
                Toast.makeText(
                    this,
                    KEY_CANNOT_SELECT_IMAGE,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && data.data != null) {
            imageUri = data.data
            binding.imageButton.setImageURI(imageUri)
        }
    }

    fun addProduct(uri: String) {
        presenter.addProduct(
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

    override fun onCheckInputAddSuccess() {
        uploadImageToFirebase()
    }


    override fun onProductAdded(product: Product) {
        presenter.addPriceSize(product.id, 1, product.price)
    }

    override fun onFail(errorMessage: String) {
        binding.buttonAddProduct.isEnabled = true
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun addPriceSize(msg: String) {
        finish()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
        private const val  KEY_CANNOT_SELECT_IMAGE = "Permission denied. Cannot select an image."
        private const val KEY_SHOW = "hiện sản phẩm"
        private const val KEY_HIDE = "ẩn sản phẩm"
    }
}
