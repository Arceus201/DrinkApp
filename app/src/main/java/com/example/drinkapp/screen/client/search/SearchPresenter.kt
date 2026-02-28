package com.example.drinkapp.screen.client.search

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiDrink

class SearchPresenter(
    private var view: SearchContract.View?,
    private val callApiDrink: CallApiDrink
) : SearchContract.Presenter {
    
    override fun attachView(view: SearchContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        view = null
    }
    
    override fun getAllProduct() {
        callApiDrink.getAllDrinkClient(
            object : OnResultListener<List<Product>>{
                override fun onSuccess(list: List<Product>) {
                    view?.onGetAllProductSuccess(list)
                }

                override fun onFail(message: String) {
                    view?.onFail()
                }

            }
        )
    }

    override fun onStart() {
    }

    override fun onStop() {
        detachView()
    }
}