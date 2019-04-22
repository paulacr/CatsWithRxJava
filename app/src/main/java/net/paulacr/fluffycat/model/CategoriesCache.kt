package net.paulacr.fluffycat.model

import io.reactivex.Observable
import net.paulacr.fluffycat.ui.catdashboard.CatDashboardRepository

object CategoriesCache {

    fun getCachedCategories(repository: CatDashboardRepository): Observable<List<Category>> {

        if (categories.isNotEmpty()) {
            return Observable.just(categories)
        } else {
            return repository.getCategories()
        }
    }
    val categories = mutableListOf<Category>()
}
