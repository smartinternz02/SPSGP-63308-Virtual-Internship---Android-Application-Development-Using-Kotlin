package com.kdb.grocer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity(tableName = "grocery")
data class GroceryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val quantity: Int,
    val price: Double
)

fun GroceryItem.getFormattedPrice(price: Double = this.price): String = NumberFormat.getCurrencyInstance().format(price)

fun GroceryItem.totalCost() = quantity * price