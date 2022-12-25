package space.beka.newcodialgram.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.beka.newcodialgram.databinding.ItemPostBinding
import space.beka.newcodialgram.models.MyPosts

class MyPostAdapter(var list: List<MyPosts>) : RecyclerView.Adapter<MyPostAdapter.Vh>() {
    inner class Vh(var itemRvBinding: ItemPostBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(myPosts: MyPosts, position: Int) {
            if (myPosts.isImage) {
                itemRvBinding.itemImagePost.visibility =View.VISIBLE
                itemRvBinding.itemVideoView.visibility =View.GONE
                Glide.with(itemRvBinding.root.context).load(myPosts.fileLocation).into(itemRvBinding.itemImagePost)
            }else{
                itemRvBinding.itemImagePost.visibility =View.GONE
                itemRvBinding.itemVideoView.visibility =View.VISIBLE
                itemRvBinding.itemVideoView.setVideoURI(Uri.parse(myPosts.fileLocation))
                itemRvBinding.itemVideoView.start()
            }
            itemRvBinding.tvDesc.text =myPosts.desc
            itemRvBinding.tvNameItem.text =myPosts.userName +" : "
            itemRvBinding.tvLikeCount.text = myPosts.likes.toString()
            itemRvBinding.tvDate.text  =myPosts.date +"<- Sanasi"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size


}