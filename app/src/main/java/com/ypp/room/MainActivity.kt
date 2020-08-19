package com.ypp.room


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ypp.room.adapter.BookAdaptor
import com.ypp.room.model.Book
import com.ypp.room.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BookAdaptor.ClickListener {

    private lateinit var bookViewModel:BookViewModel
    private  var addBookActivityRequestCode=1
    private lateinit var bookAdaptor:BookAdaptor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         bookAdaptor=BookAdaptor()
//        bookViewModel=Viel.of(this).get(BookViewModel::class.java)
        bookViewModel=ViewModelProviders.of(this).get(BookViewModel::class.java)



        recyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=bookAdaptor
        }

        bookAdaptor.onClickListener(this)

        bookViewModel.allBook.observe(
            this, Observer {books->
                books.let {
                    bookAdaptor.addBookList(books)
                }
            }
        )

        btnAdd.setOnClickListener{
            val intent=Intent(
                this,
                AddBookActivity::class.java
            )
            startActivityForResult(intent,addBookActivityRequestCode)

            }
        btnDelete.setOnClickListener{
            bookViewModel.delete()
        }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==addBookActivityRequestCode && resultCode ==Activity.RESULT_OK){
            data?.getStringExtra(AddBookActivity.EXTRA_REPLY)?.let {
                val book=Book(it)
                bookViewModel.insert(book)
            }
        }
    }

    override fun click(book: Book) {
        bookViewModel.deleteItem(book.bookName)
    }


}
