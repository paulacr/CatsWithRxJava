package net.paulacr.fluffycat.ui.catdashboard

import io.reactivex.Observable
import net.paulacr.fluffycat.model.CatImage
import net.paulacr.fluffycat.model.Category
import net.paulacr.fluffycat.model.CategoriesCache
import net.paulacr.fluffycat.network.ApiInterface

class CatDashboardRepository(private val apiInterface: ApiInterface) : CatDataSource {

    override fun getCachedCategories(): Observable<List<Category>> {
        return CategoriesCache.getCachedCategories(this)
    }

    override fun getCategories(): Observable<List<Category>> {
        return apiInterface.getCategories()
    }

    override fun getCatImages(limit: String?, categoryId: Category?): Observable<List<CatImage>> {
        return apiInterface.searchCats(limit, categoryId?.id)
    }
}