package com.example.androidtask.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.androidtask.model.CountryModel

@Dao
interface DaoAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(item: CountryModel?): Long

    @Query("SELECT * FROM CountryModel")
    fun fetchAllItems(): LiveData<List<CountryModel?>?>?

    @Query("SELECT COUNT() FROM CountryModel WHERE numeric = :numeric")
    fun getCountry(numeric: String?): Int

    @Delete
    fun deletePost(item: CountryModel?)
}