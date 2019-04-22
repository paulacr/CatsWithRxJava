package net.paulacr.fluffycat.ui.catdashboard

import io.reactivex.Observable
import net.paulacr.fluffycat.model.CatImage
import net.paulacr.fluffycat.model.Category

interface CatDataSource {

    fun getCatImage(limit: String? = null): Observable<List<CatImage>>

    fun getCategories(): Observable<List<Category>>

    fun getCachedCategories(): Observable<List<Category>>
}