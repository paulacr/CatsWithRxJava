package net.paulacr.fluffycat.ui.catdashboard

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.paulacr.fluffycat.livedata.LiveDataWithValue
import net.paulacr.fluffycat.model.CatImage
import net.paulacr.fluffycat.model.Category

class CatDashboardViewModel(app: Application, private val repository: CatDashboardRepository) : AndroidViewModel(app),
    LifecycleObserver {

    val onDataReady = LiveDataWithValue<List<CatImage>>()
    private val compositeDisposable = CompositeDisposable()
    private var catsDisposable: Disposable? = null

    fun getCats() {

        lateinit var category: Category
        catsDisposable?.dispose()
        catsDisposable =

            repository.getCachedCategories()
                .doOnNext {
                    category = it.first()
                }.flatMap {
                    repository.getCatImage("100")
                }.flatMap {
                    Observable.fromIterable(it)
                }.filter {
                    !it.categories.isNullOrEmpty()
                }.filter {
                    it.categories.contains(category)
                }.toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        onDataReady.actionOccuredPost(it)
                        Log.i("Log Category ", "$it")
                    }
                }, { error ->
                    Log.e("Error", "error", error)
                })
//            repository.getCatImage(LIMIT_ITEMS)
//                .flatMap {
//                    repository.getCatDetail(it.first().id)
//                }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    onDataReady.actionOccuredPost(listOf(it))
//                }
        catsDisposable?.let {
            compositeDisposable.add(it)
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private const val LIMIT_ITEMS = "10"
    }
}