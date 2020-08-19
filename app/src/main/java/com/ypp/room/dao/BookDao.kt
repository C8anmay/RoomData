package com.ypp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ypp.room.model.Book

@Dao
interface BookDao{
    @Query("SELECT * FROM book_table ORDER BY book_name ASC")
    fun getAllBook(): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book)

    @Query("DELETE FROM book_table")
    suspend fun deleteAll()

    @Query("DELETE FROM book_table WHERE book_name=:name")
    suspend fun deleteItem(name:String)


}