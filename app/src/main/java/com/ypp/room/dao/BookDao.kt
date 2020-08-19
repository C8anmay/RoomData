package com.ypp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ypp.room.model.Book

@Dao
interface BookDao{
    @Query("SELECT * FROM book_table ORDER BY book_name ASC")
    fun getAllBook(): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book)
}