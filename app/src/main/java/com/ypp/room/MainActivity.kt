package com.ypp.room


import android.app.Activity
import android.app.AlertDialog
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
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_update.view.*

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
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Delete item")
            setMessage("Are you sure to delete?")
            setIcon(android.R.drawable.ic_dialog_alert)
            setPositiveButton("YES"){
                dialogInterface, i ->
                bookViewModel.deleteItem(book.bookName)
            }

            setNegativeButton("No"){
                dialogInterface, i ->
                Toast.makeText(applicationContext,
                "Delete Cancel", Toast.LENGTH_LONG).show()
            }
            setNeutralButton("Update"){
                dialogInterface, i ->
                val updateBuilder=AlertDialog.Builder(context)
                val dialogLayout=layoutInflater.inflate(R.layout.dialog_update,null,false)
                updateBuilder.apply {
                    setTitle("Update Book")
                    setView(dialogLayout)
                    setPositiveButton("OK"){
                        dialogInterface, i ->
                        val updatText= dialogLayout.dialogUpdate.text.toString()
                        bookViewModel.updateItem(
                            updatText,book.bookName
                        )
                    }
                }
                val updateDialog:AlertDialog=updateBuilder.create()
                updateDialog.show()
            }
        }

//        setNeutralButton("text"){
//            dialo
//        }

        val alertDialog=builder.create()
        alertDialog.show()
    }


}
