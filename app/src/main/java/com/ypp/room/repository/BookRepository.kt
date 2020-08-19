package com.ypp.room.repository

import androidx.lifecycle.LiveData
import com.ypp.room.dao.BookDao
import com.ypp.room.model.Book

class BookRepository (private val bookDao: BookDao){
    val allBook:LiveData<List<Book>> = bookDao.getAllBook()
    suspend fun insert(book: Book){
        bookDao.insert(book)
    }
}