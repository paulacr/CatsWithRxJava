package net.paulacr.fluffycat.ui.catdashboard

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import net.paulacr.fluffycat.R
import net.paulacr.fluffycat.model.CatImage

class CatDashboardAdapter(private val listOfPets: List<CatImage>) : RecyclerView.Adapter<CatDashboardAdapter.PetViewHolder>() {

    private val catsList = listOfPets.toMutableList()
    override fun onCreateViewHolder(container: ViewGroup, p1: Int): PetViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.view_pet_item, container, false)
        return PetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return catsList.size
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(catsList[position])
    }

    fun updateItems(cats: List<CatImage>) {
        catsList.addAll(cats)
        this.notifyItemRangeInserted(itemCount, cats.size - 1)
    }

    class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val petImage: ImageView = itemView.findViewById(R.id.petImage)
        private val petName: TextView = itemView.findViewById(R.id.petName)

        fun bind(pet: CatImage) {
            Glide.with(itemView.context)
                .load(pet.url)
                .placeholder(R.drawable.cat_pawprint)
                .into(petImage)

            petName.text = pet.id
        }
    }
}