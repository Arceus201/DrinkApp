package com.example.drinkapp.screen.admin.revenuestatistics

import android.app.DatePickerDialog
import android.content.Intent
import android.widget.Toast
import com.example.drinkapp.R
import com.example.drinkapp.data.model.RevenueStatistics
import com.example.drinkapp.data.resource.call.CallApiRevenue
import com.example.drinkapp.databinding.AdminActivityRevenueStatisticsBinding
import com.example.drinkapp.screen.admin.adapter.RecyclerViewRevenueStatisticAdapter
import com.example.drinkapp.screen.admin.drinkorders.DrinkOrderActivity
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.base.BaseActivity
import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.formatTimeStatistic
import com.example.drinkapp.utils.listener.OnItemStatisticClickListener
import java.util.*

class RevenueStatisticsActivity :
    BaseActivity<AdminActivityRevenueStatisticsBinding>(AdminActivityRevenueStatisticsBinding::inflate),
    RevenueStatisticsContract.View,
    OnItemStatisticClickListener {
    private lateinit var presenter: RevenueStatisticsPresenter
    private val adapter = RecyclerViewRevenueStatisticAdapter(this)
    private var selectedDate: Date = Calendar.getInstance().time
    private var selectedStartTime: Date = Calendar.getInstance().time
    private var selectedEndTime: Date = Calendar.getInstance().time
    override fun initView() {
        binding.recyclerView.adapter = adapter
        val curentTime = Date()
        binding.apply {
            buttonStartTime.setText(curentTime.formatTimeStatistic())
            buttonEndTime.setText(curentTime.formatTimeStatistic())
        }

    }

    override fun initData() {
        presenter = RevenueStatisticsPresenter(this, CallApiRevenue.getInstance())
        val curentTime = Date()

        presenter.getRevenueStatistics(
            curentTime.formatTimeStatistic() + Constant.KEY_TIME_START,
            curentTime.formatTimeStatistic() + Constant.KEY_TIME_END
        )
    }

    override fun handleEvent() {
        binding.apply {
            buttonStartTime.setOnClickListener {
                showDatePickerDialog(0L)
            }
            buttonEndTime.setOnClickListener {
                showDatePickerDialog(1L)
            }
            buttonSee.setOnClickListener {
                presenter.checkTimeRevenueStatistic(selectedStartTime,selectedEndTime)
            }
            buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    fun showDatePickerDialog(action: Long) {
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
                    if (action == 0L) {
                        selectedStartTime = selectedDate
                        binding.buttonStartTime.text = formattedDate
                    } else {
                        selectedEndTime = selectedDate
                        binding.buttonEndTime.text = formattedDate
                    }
                }
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    override fun onGetRevenueStatisticsSuccess(list: List<RevenueStatistics>) {
        val totalPrice = list.map { it.revenue }.sum()
        binding.textTotalPrice.setText(totalPrice.formatAsNumber())
        adapter.setData(list)

    }

    override fun onGetRevenueStatisticsFail() {
        adapter.clearData()
        binding.textTotalPrice.setText(R.string.total_price_default)
    }

    override fun onCheckTimeRevenueStatisticSuccess() {
        binding.apply {
            presenter.getRevenueStatistics(
                buttonStartTime.text.toString() + Constant.KEY_TIME_START,
                buttonEndTime.text.toString() + Constant.KEY_TIME_END
            )
        }
    }

    override fun onCheckTimeFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        adapter.clearData()
        binding.textTotalPrice.setText(R.string.total_price_default)
    }

    override fun onFail(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(name: String) {
        val intent = Intent(this, DrinkOrderActivity::class.java)
        intent.putExtra(Constant.KEY_NAME, name)
        intent.putExtra(Constant.KEY_START, binding.buttonStartTime.text.toString())
        intent.putExtra(Constant.KEY_END,binding.buttonEndTime.text.toString())
        startActivity(intent)
    }


}