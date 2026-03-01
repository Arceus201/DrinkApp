package com.example.drinkapp.screen.common.profile

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.repository.UserRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.utils.base.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val repository: UserRepository
) : BasePresenter<ProfileContract.View>(), ProfileContract.Presenter {
    
    override fun getUser(user_id: Long) {
        launch {
            when (val result = repository.getUserById(user_id)) {
                is Result.Success -> {
                    view?.onGetUserSuccess(result.data)
                }
                is Result.Error -> {
                    // Silent fail as per original implementation
                }
                is Result.HttpError -> {
                    // Silent fail as per original implementation
                }
            }
        }
    }

    override fun updateUser(user_id: Long, username: String, dob: String) {
        launch {
            // Create a User object with the updated fields
            // Note: We need to provide all required fields, even if they won't be used in the update
            val user = User(
                id = user_id,
                username = username,
                dob = dob,
                password = "", // Required field, but not used in update
                phone = "", // Required field, but not used in update
                role = 0L // Required field, but not used in update
            )
            
            when (val result = repository.updateUser(user_id, user)) {
                is Result.Success -> {
                    view?.onUpdateUserSuccess(result.data)
                }
                is Result.Error -> {
                    view?.onFail(MESS_UPDATE_ACCOUNT_FAIL)
                }
                is Result.HttpError -> {
                    view?.onFail(MESS_UPDATE_ACCOUNT_FAIL)
                }
            }
        }
    }
    
    companion object {
        const val MESS_UPDATE_ACCOUNT_FAIL = "cập nhật tài khoản không thành công"
    }
}