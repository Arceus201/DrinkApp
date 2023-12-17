package com.example.drinkapp.screen.admin.size

import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.resource.dto.category.CategoryDTO
import com.example.drinkapp.utils.base.BasePresenter

interface SizeContract {
    interface View{
        fun displaySuccess(list: List<Size>)
        fun displaySizeFail()
        fun displayFail(msg: String)
    }
    interface Presenter: BasePresenter<View> {
        fun getAllSize()
        fun addSize(name: String)
        fun updateSize(id: Long?,size: Size)
    }
}