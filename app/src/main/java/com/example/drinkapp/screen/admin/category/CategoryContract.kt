package com.example.drinkapp.screen.admin.category

import com.example.drinkapp.data.model.Category
import com.example.drinkapp.utils.base.BasePresenter

interface CategoryContract {
    interface View{
        fun displaySuccess(list: List<Category>)
        fun displayFail()

        fun addUpDateFail(msg: String)
    }
    interface Presenter: BasePresenter<View>{
        fun getAllCategory()
        fun addCategory(name: String)
        fun updateCategory(id: Long?,category: Category )
    }
}