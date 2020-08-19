package com.ypp.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
//import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewModelScope
import com.ypp.room.database.BookDataBase
import com.ypp.room.model.Book
import com.ypp.room.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(application: Application)
    :AndroidViewModel(application){
    private val repository: BookRepository

    val allBook:LiveData<List<Book>>
    init {
        val bookDao= BookDataBase.getDatabase(
            application
        ).bookDao()
        repository= BookRepository(bookDao)
        allBook=repository.allBook
    }
    fun insert(book: Book)=viewModelScope.launch {
        repository.insert(book)
    }
    fun delete()=viewModelScope.launch {
        repository.delete()
    }

    fun deleteItem(name:String)=viewModelScope.launch {
        repository.deleteItem(name)
    }
}