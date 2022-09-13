package com.kdb.grocer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kdb.grocer.data.GroceryDao
import com.kdb.grocer.data.GroceryItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class GroceryViewModel(private val groceryDao: GroceryDao) : ViewModel() {

    val items: LiveData<List<GroceryItem>> = groceryDao.getItems()

    fun deleteItem(item: GroceryItem) = viewModelScope.launch { groceryDao.delete(item) }
}

class GroceryViewModelFactory(private val groceryDao: GroceryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GroceryViewModel(groceryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class!")
    }
}