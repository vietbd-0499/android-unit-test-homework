package com.sun.training.ut.ui.exercise_two

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import com.sun.training.ut.data.Constant
import com.sun.training.ut.data.model.Customer
import com.sun.training.ut.ui.base.BaseViewModel

class ExerciseTwoViewModel : BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var isVip: Boolean = false

    val feeLiveData: MutableLiveData<Int> = MutableLiveData()

    private var holidays = arrayOf("2/9", "1/1", "30/4", "1/5")

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var hour: Int = 0

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var minute: Int = 0

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var dayOfMonth: Int = 0

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var monthOfYear: Int = 0

    fun onVipChecked(isChecked: Boolean) {
        isVip = isChecked
    }

    fun onTimeChanged(hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute
    }

    fun onDateChanged(dayOfMonth: Int, monthOfYear: Int) {
        this.monthOfYear = monthOfYear + 1
        this.dayOfMonth = dayOfMonth
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun validateDate(dayOfMonth: Int, monthOfYear: Int): Boolean {
        val date = String.format("%d/%d", dayOfMonth, monthOfYear)
        return holidays.contains(date)
    }

    fun calculateFee() {
        val customer = Customer(
            hour = hour,
            minute = minute,
            isVip = isVip,
            dayOfMonth = dayOfMonth,
            monthOfYear = monthOfYear
        )
        val fee = when {
            customer.isVip -> 0
            validateDate(dayOfMonth, monthOfYear) -> Constant.FEE_110
            else -> validateHourRegularDay(hour = customer.hour, minute = customer.minute)
        }
        feeLiveData.postValue(fee)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun validateHourRegularDay(hour: Int, minute: Int): Int {
        return when (hour * 60 + minute) {
            in Constant.TIME_8_45..Constant.TIME_17_59 -> 0
            else -> Constant.FEE_110
        }
    }

}