package com.ypp.room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ypp.room.R
import com.ypp.room.model.Book
import kotlinx.android.synthetic.main.item_book.view.*

class BookAdaptor:RecyclerView.Adapter<BookAdaptor.BookViewHoler>(){

    var clickListener:ClickListener?=null

    fun onClickListener(clickListener: ClickListener){
        this.clickListener=clickListener
    }
    private var books= emptyList<Book>()
    inner class BookViewHoler(itemView: View) :RecyclerView.ViewHolder(itemView), View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }
        lateinit var book2: Book

        fun bind(book: Book){
            this.book2=book
            itemView.txt_book_name.text=book.bookName
        }

        override fun onClick(p0: View?) {
            clickListener?.click(book2)
        }

    }
    fun addBookList(bookList: List<Book>){
        this.books=bookList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHoler {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.item_book,parent,false)
        return BookViewHoler(view)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookViewHoler, position: Int) {
        holder.bind(books[position])
    }
    interface ClickListener{
        fun click(book: Book)
    }
}