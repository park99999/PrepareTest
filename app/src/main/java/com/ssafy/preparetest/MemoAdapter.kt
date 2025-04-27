package com.ssafy.preparetest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.preparetest.databinding.ItemMemoBinding
import com.ssafy.preparetest.dto.Memo

private const val TAG = "MenuAdapter_싸피"
class MemoAdapter(val context: Context, val prodList:List<Memo>) : RecyclerView.Adapter<MemoAdapter.MemoHolder>(){

    inner class MemoHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.textView)
        val content = itemView.findViewById<TextView>(R.id.textView2)
        val id = itemView.findViewById<TextView>(R.id.textView3)
        val rate = itemView.findViewById<TextView>(R.id.textView4)
        val createdAt = itemView.findViewById<TextView>(R.id.textView5)

        fun bindInfo(memo : Memo){
            name.text = memo.name
            content.text = memo.content
            id.text = memo.id.toString()
            rate.text = memo.rate.toString()
            createdAt.text = memo.createdAt
//            val img = context.resources.getIdentifier(product.img, "drawable", context.packageName)
//            menuImage.setImageResource(img)

            itemView.setOnClickListener{
                prodList[layoutPosition].id?.let { it1 ->
                    itemClickListner.onClick(it, layoutPosition,
                        it1
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoHolder {
        val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MemoHolder, position: Int) {
        holder.apply{
            bindInfo(prodList[position])
        }
    }

    override fun getItemCount(): Int {
        return prodList.size
    }

    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
    interface ItemClickListener {
        fun onClick(view: View, position: Int, productId:Int)
    }
    //클릭리스너 선언
    lateinit var itemClickListner: ItemClickListener
    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}
