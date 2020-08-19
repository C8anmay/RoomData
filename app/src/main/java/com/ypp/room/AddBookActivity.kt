package com.ypp.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_add_book.*

class AddBookActivity : AppCompatActivity() {
    lateinit var bookName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        buttton.setOnClickListener{
            val replyIntent=Intent()
            bookName=editBookName.text.toString()
            if (TextUtils.isEmpty(editBookName.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else{
                bookName=editBookName.text.toString()
                replyIntent.putExtra(EXTRA_REPLY,bookName)
                setResult(Activity.RESULT_OK,replyIntent)
            }
            finish()

        }
    }
    companion object{
        const val EXTRA_REPLY="REPLY_BOOK"
    }
}