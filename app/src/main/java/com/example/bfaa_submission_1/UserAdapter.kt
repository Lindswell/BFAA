package com.example.bfaa_submission_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bfaa_submission_1.databinding.UserDataLayoutBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.ListViewHolder>(){
    private val listUser = ArrayList<User>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_data_layout, viewGroup, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserDataLayoutBinding.bind(itemView)
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.photo)
                    .into(userPhoto)
                binding.userName.text = user.name
                binding.userHtml.text = user.html

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }

            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }


}