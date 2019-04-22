package net.paulacr.fluffycat.network

import io.reactivex.Observable
import net.paulacr.fluffycat.model.CatImage
import net.paulacr.fluffycat.model.Category
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("images/search")
    fun searchCats(@Query("limit") limit: String?): Observable<List<CatImage>>

    @GET("categories")
    fun getCategories(): Observable<List<Category>>
}