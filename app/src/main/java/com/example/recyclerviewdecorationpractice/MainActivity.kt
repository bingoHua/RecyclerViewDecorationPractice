package com.example.recyclerviewdecorationpractice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdecorationpractice.drag.ItemDragManager
import java.util.*

class MainActivity : AppCompatActivity() {

    val recyclerView by lazy<RecyclerView> {
        findViewById(R.id.recycler_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myAdapter = MyAdapter()
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        ItemDragManager(recyclerView)
    }


    internal class MyItem(val id: Long, val text: String)
    internal class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(android.R.id.text1)
    }

    internal class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
        var mItems: MutableList<MyItem>
        override fun getItemId(position: Int): Long {
            return mItems[position].id // need to return stable (= not change even after reordered) value
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_minimal, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item = mItems[position]
            holder.textView.text = item.text
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        init {
            setHasStableIds(true) // this is required for D&D feature.
            mItems = ArrayList()
            for (i in 0..19) {
                mItems.add(MyItem(i.toLong(), "Item $i"))
            }
        }
    }
}