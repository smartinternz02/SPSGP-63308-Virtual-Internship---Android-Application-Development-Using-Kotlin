package com.kdb.grocer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GroceryDao {
    @Query("SELECT * FROM grocery")
    fun getItems(): LiveData<List<GroceryItem>>

    @Insert
    suspend fun insert(item: GroceryItem)

    @Delete
    suspend fun delete(item: GroceryItem)
}