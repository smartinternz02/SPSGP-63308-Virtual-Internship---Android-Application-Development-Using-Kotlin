package com.kdb.grocer.ui

import androidx.lifecycle.*
import com.kdb.grocer.data.GroceryDao
import com.kdb.grocer.data.GroceryItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AddItemViewModel(private val groceryDao: GroceryDao) : ViewModel() {

    private val _nameEmpty = MutableLiveData(false)
    val nameEmpty: LiveData<Boolean> = _nameEmpty

    private val _quantityEmpty = MutableLiveData(false)
    val quantityEmpty: LiveData<Boolean> = _quantityEmpty

    private val _priceEmpty = MutableLiveData(false)
    val priceEmpty: LiveData<Boolean> = _priceEmpty

    fun validate(name: String, quantity: String, price: String): Boolean {
        _nameEmpty.value = name.isBlank()
        _quantityEmpty.value = quantity.isBlank()
        _priceEmpty.value = price.isBlank()

        return (_nameEmpty.value!! xor _quantityEmpty.value!! xor _priceEmpty.value!!).not()
    }

    fun saveItem(name: String, quantity: String, price: String) = viewModelScope.launch {
        val item = GroceryItem(
            name = name,
            quantity = quantity.toInt(),
            price = price.toDouble()
        )

        groceryDao.insert(item)
    }
}

class AddItemViewModelFactory(private val groceryDao: GroceryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddItemViewModel(groceryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class!")
    }
}