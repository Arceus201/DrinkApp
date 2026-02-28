package com.example.drinkapp.screen.admin.custom_manager_order

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiOrder

class CustomManagerOrderPresenter(private var view: CustomManagerOrderContract.View?,
                                  private val callApi: CallApiOrder
):CustomManagerOrderContract.Presenter  {
    
    override fun attachView(view: CustomManagerOrderContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        view = null
    }
    
    override fun getAllOrderByUserInUserManager(user_id: Long) {
        callApi.getAllOrderByUserInUserManager(
            user_id,
            object : OnResultListener<List<Order>> {
                override fun onSuccess(list: List<Order>) {
                    view?.onGetAllOrderByUserInUserManagerSuccess(list)
                }

                override fun onFail(message: String) {
                    view?.onGetAllOrderByUserInUserManagerFail()
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