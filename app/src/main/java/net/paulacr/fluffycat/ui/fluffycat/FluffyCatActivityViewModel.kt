package net.paulacr.fluffycat.ui.fluffycat

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.paulacr.fluffycat.livedata.LiveDataWithValue
import net.paulacr.fluffycat.model.CatImage
import net.paulacr.fluffycat.ui.catdashboard.CatDashboardRepository

class FluffyCatActivityViewModel(app: Application, private val repository: CatDashboardRepository) :
    AndroidViewModel(app),
    LifecycleObserver {

    val actionOnFluffyReady = LiveDataWithValue<CatImage>()
    val actionSendResult = LiveDataWithValue<String>()

    private val compositeDisposable = CompositeDisposable()
    private var catsDisposable: Disposable? = null

    init {
        getFluffy()
    }

    fun getFluffy() {
        catsDisposable?.dispose()
        catsDisposable =
            repository.getCatImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.first()
                }
                .subscribe { t ->
                    actionOnFluffyReady.actionOccuredPost(t)
                }

        catsDisposable?.let {
            compositeDisposable.add(it)
        }
    }
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}