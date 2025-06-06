package com.example.forklore.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RemoteViews.RemoteCollectionItems
import androidx.recyclerview.widget.RecyclerView
import com.example.forklore.databinding.CartItemBinding

class cartAdapter ( private val CartItems: MutableList<String>, private val CartItemPrice: MutableList<String>, private var CartImage: MutableList<Int>) : RecyclerView.Adapter<cartAdapter.CartViewHolder>() {

    private var itemQuantities = IntArray(CartItems.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = CartItems.size

    inner class CartViewHolder(private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                cartFoodName.text = CartItems[position]
                cartItemPrice.text = CartItemPrice[position]
                cartImage.setImageResource(CartImage[position])
                cartItemQuantity.text = quantity.toString()

                minusbutton.setOnClickListener { decreaseQuantity(position) }
                plusbutton.setOnClickListener { increaseQuantity(position) }
                deleteButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }
            }
        }
        private fun decreaseQuantity (position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun increaseQuantity (position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItem(position: Int) {
            CartItems.removeAt(position)
            CartImage.removeAt(position)
            CartItemPrice.removeAt(position)
            itemQuantities = itemQuantities.toMutableList().apply { removeAt(position) }.toIntArray() //changed/updated as it resolves the error which was
            //when we delete the above, the item below was taking the same value as the above
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, CartItems.size)
        }
    }
}