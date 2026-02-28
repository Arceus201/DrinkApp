package com.example.drinkapp.screen.admin.size


import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiSize


class SizePresenter(private var view: SizeContract.View?, private val callApi: CallApiSize) :
    SizeContract.Presenter {

    override fun attachView(view: SizeContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        view = null
    }

    override fun getAllSize() {
        callApi.getAllSize(
            object : OnResultListener<List<Size>> {
                override fun onSuccess(list: List<Size>) {
                    view?.displaySuccess(list)
                }

                override fun onFail(message: String) {
                    view?.displaySizeFail()
                }
            }
        )
    }

    override fun addSize(name: String) {
        callApi.addSize(name,
            object : OnResultListener<List<Size>> {
                override fun onSuccess(list: List<Size>) {
                    view?.displaySuccess(list)
                }

                override fun onFail(message: String) {
                    view?.displayFail( ADD_FAIL)
                }
            }
        )
    }

    override fun updateSize(id: Long?, size: Size) {
        callApi.updateSize(id,
            size,
            object : OnResultListener<List<Size>> {
                override fun onSuccess(list: List<Size>) {
                    view?.displaySuccess(list)
                }

                override fun onFail(message: String) {
                    view?.displayFail(UPDATE_FAIL)
                }
            }
        )
    }


    override fun onStart() {
    }

    override fun onStop() {
        detachView()
    }
    companion object {
        const val UPDATE_FAIL = "cập nhật tên size không thành công"
        const val ADD_FAIL = "thêm tên size không thành công"
    }

}