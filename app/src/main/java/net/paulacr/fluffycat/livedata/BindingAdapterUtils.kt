package net.paulacr.fluffycat.livedata

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView

@BindingAdapter(value = ["binding:imageUrl", "binding:placeholder"], requireAll = false)
fun loadImage(imageView: ImageView, url: String?, placeholder: Drawable) {
//    val imageCache = HoundourServiceLocator.resolve(ImageCache::class.java)
//    val picasso =
//    val imageUrl = url ?: ""
//
//    if (imageUrl.isNotEmpty() || imageUrl.isNotBlank()) {
//        val image = imageCache.getImage(imageUrl)
//
//        if (image != null) {
//            imageView.setImageBitmap(image)
//        } else {
//            picasso.load(imageUrl, imageView, placeholder)
//        }
//    }
}
