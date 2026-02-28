package com.example.drinkapp.screen.shipper.orderlist

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.data.resource.dto.order.OrderShipperConfirmDTO

class OrderListPresenter(private var view: OrderListContract.View?,
private  val callApi: CallApiOrder):
OrderListContract.Presenter{
    
    override fun attachView(view: OrderListContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        view = null
    }
    
    override fun getListOrder(status: Long) {
        callApi.getAllOrderByStatus(
            status,
            object : OnResultListener<List<Order>>{
                override fun onSuccess(list: List<Order>) {
                    view?.onGetListOrderSuccess(list)
                }

                override fun onFail(message: String) {
                    view?.onGetListOrderFail()
                }

            }
        )
    }

    override fun getOrderByShipperId(shipper_id: Long) {
        callApi.getOrderByShipperId(
            shipper_id,
            object :OnResultListener<Order>{
                override fun onSuccess(list: Order) {
                    view?.onGetOrderByShipperIdSuccess(list)
                }

                override fun onFail(message: String) {
                    view?.onGetOrderByShipperIdFail()
                }

            }
        )
    }

    override fun updateShippingOrder(order_id: Long, shipper_id: Long, status: Long) {
        val orderShipperConfirmDTO = OrderShipperConfirmDTO(shipper_id,status);
       callApi.updateShippingOrder(
           order_id,
            orderShipperConfirmDTO,
           object :OnResultListener<Order>{
               override fun onSuccess(list: Order) {
                   view?.updateShippingOrderSucess()
               }

               override fun onFail(message: String) {
                   view?.onFail(MESSAGE_CONFIRM_FAIL)
               }

           }
       )
    }
    
    override fun onStart() {
    }

    override fun onStop() {
        detachView()
    }

    companion object{
        const val MESSAGE_CONFIRM_FAIL = "xác nhận không thành công"
    }

}