package com.example.drinkapp.screen.admin.product_size

import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.model.Size
import com.example.drinkapp.utils.base.IBasePresenter

interface ProductSizeContract {
    interface View{
        fun onGetAllSizeSuccess(result: List<Pair<Long, String>>)
        fun onGetAllPriceSizeSucess(result: List<PriceSize>)

        fun onUpdateAndAddSuccess(result: List<PriceSize>)
        fun onFail(msg: String)
        fun onGetAllPriceSizeFail()
    }
    interface Presenter: IBasePresenter<View>{
        fun getALLSize()
        fun getAllPriceSize(idProduct: Long)

        fun addPriceSize(idProduct: Long, idSize: Long, price: Double)

        fun updatePriceSize(id: Long,idProduct: Long, idSize: Long, price: Double, status: Long)
    }
}