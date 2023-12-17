package com.example.drinkapp.screen.common.profile

import android.app.DatePickerDialog
import android.widget.Toast
import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.call.CallApiUser
import com.example.drinkapp.databinding.ClientActivityProfileBinding
import com.example.drinkapp.utils.UserManager
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.formatTimeStatistic
import com.example.drinkapp.utils.setMaxLength
import java.util.*

class ProfileActivity:BaseActivity<ClientActivityProfileBinding>(ClientActivityProfileBinding::inflate) ,
ProfileContract.View{
    private lateinit var presenter: ProfilePresenter
    private var selectedDate: Date? = null
    override fun initView() {
        binding.apply {
            textUserName.setMaxLength(25)
        }

    }

    override fun initData() {
        val user = UserManager.getUserInfo(this)
        presenter = ProfilePresenter(this, CallApiUser.getInstance())
        user.id?.let { presenter.getUser(it) }
    }

    override fun handleEvent() {
        val user = UserManager.getUserInfo(this)
        binding.apply {
            textDob.setOnClickListener{
                showDatePickerDialog()
            }
            buttonBack.setOnClickListener {
                finish()
            }
            buttonUpdate.setOnClickListener {
                user.id?.let { it1 ->
                    presenter.updateUser(
                        it1,
                        textUserName.text.toString().trim(),
                        textDob.text.toString().trim()
                    )
                }
            }
        }
    }

    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)
                selectedDate = calendar.time
                selectedDate?.let {
                    val formattedDate = it.formatTimeStatistic()
                    binding.textDob.setText(formattedDate)
                }
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    override fun onGetUserSuccess(user: User) {
        binding.apply {
            textUserName.setText(user.username)
            textPhone.setText(user.phone)
            if(user.dob!=null){
                textDob.setText(user.dob)
            }
        }
    }

    override fun onUpdateUserSuccess(user: User) {
        val id = user.id
        val role = user.role
        val name = user.username
        UserManager.saveUserInfo(id, role,name, applicationContext)

        onFail(MESS_UPDATE_ACCOUNT_SUCCESS)
        finish()
    }

    override fun onFail(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
    companion object{
        const val MESS_UPDATE_ACCOUNT_SUCCESS = "cập nhật tài khoản thành công"
    }
}