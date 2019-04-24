package net.paulacr.fluffycat.ui.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import net.paulacr.fluffycat.livedata.LiveDataWithAction

class HomeActivityViewModel(app: Application) : AndroidViewModel(app) {

    init {
        createObservable()
        transformerObservables()
        utilOperators()
        chainOperators()
        subscribeOnDemo()
    }

    var actionClickedCatDashboard = LiveDataWithAction()
    var actionClickedFluffyCat = LiveDataWithAction()

    fun clickedCatDashboard() {
        actionClickedCatDashboard.actionOccurred()
    }

    fun clickedFluffyCat() {
        actionClickedFluffyCat.actionOccurred()
    }

    private fun createObservable() {

        val welcomeMessage = "Hello TDC"
        val observable = Observable.just(welcomeMessage)
        observable.subscribe(object : Observer<String> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: String) {
                Log.i("Log observable ", "onNext -> $t")
            }

            override fun onError(e: Throwable) {
                Log.e("Log error ", "error -> $e")
            }
        })

        observable.subscribe(object : Observer<String> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: String) {
                Log.i("Log observable ", "second subscribe onNext -> $t")
            }

            override fun onError(e: Throwable) {
            }
        })

        val observerNumbers = object : Observer<Int> {
            override fun onComplete() {
                Log.i("Log observable numbers ", "completed")
            }

            override fun onSubscribe(d: Disposable) {
                Log.i("Log obervable numbers", "subscribed")
            }

            override fun onNext(t: Int) {
                Log.i("Log observable numbers", "onNext -> $t")
            }

            override fun onError(e: Throwable) {
                Log.e("Log error numbers", "error -> $e")
            }
        }

        val observableNumbers = Observable.just(1, 2, 3, 4)
        observableNumbers.subscribe(observerNumbers)

        val numberSequence = listOf<Int>(1, 2, 3, 4)
        val otherObservable = Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {

                numberSequence.forEach {
                    emitter.onNext(it)
                }

                emitter.onComplete()
            }
        })

        otherObservable.subscribe(object : Observer<Int> {
            override fun onComplete() {
                Log.i("Log ObsOnSubscribe ", "On Complete")
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Int) {
                Log.i("Log ObsOnSubscribe ", "onNext -> $t")
            }

            override fun onError(e: Throwable) {
                Log.e("Log error ", "error -> $e")
            }
        })

        val myList = listOf<Int>(2, 4, 6)
        val newObservable = Observable.fromIterable(myList)
        newObservable.subscribe(object : Observer<Int> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Int) {
                Log.i("Log other observable ", "onNext -> $t")
            }

            override fun onError(e: Throwable) {
                Log.e("Log error ", "error -> $e")
            }
        })
    }

    private fun transformerObservables() {
        val observable =
            Observable.just(1, 2, 3)
                .map(object : Function<Int, Int> {
                    override fun apply(t: Int): Int {
                        return t * 10
                    }
                })
        observable.subscribe(object : Observer<Int> {

            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Int) {
                Log.i("Log map", "Result = $t")
            }

            override fun onError(e: Throwable) {
            }
        })

        val observableFilter =
            Observable.just(1, 2, 3, 4, 5)
                .filter(object : Predicate<Int> {
                    override fun test(t: Int): Boolean {
                        return t > 2
                    }
                })
        observableFilter.subscribe(object : Observer<Int> {

            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Int) {
                Log.i("Log filter", "Result = $t")
            }

            override fun onError(e: Throwable) {
            }
        })
    }

    private fun utilOperators() {
        val observableTake =
            Observable.just(1, 2, 3, 4, 5)
                .take(3)
        observableTake.subscribe(object : Observer<Int> {

            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Int) {
                Log.i("Log take", "Result = $t")
            }

            override fun onError(e: Throwable) {
            }
        })
    }

    private fun chainOperators() {
        val observableTake =
            Observable.just(1, 2, 3, 4, 5)
                .take(3)
                .filter(object : Predicate<Int> {
                    override fun test(t: Int): Boolean {
                        return t < 3
                    }
                })

        observableTake.subscribe(object : Observer<Int> {

            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Int) {
                Log.i("Log with Error", "onNext Result = $t")
            }

            override fun onError(e: Throwable) {
                Log.i("Log with Error", "onError Result = $e")
            }
        })
    }

    private var disposable: Disposable? = null

    private fun subscribeOnDemo() {
        val observable = Observable.just("Hello:")
            .subscribeOn(Schedulers.computation())
            .doOnNext {
                Log.i("Log thread", Thread.currentThread().name)
            }
            .observeOn(Schedulers.io())
            .doOnNext {
                Log.i("Log thread", Thread.currentThread().name)
            }
            .observeOn(Schedulers.single())
            .doOnNext {
                Log.i("Log thread", Thread.currentThread().name)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.i("Log thread", Thread.currentThread().name)
            }
            .concatMap {
                Observable.just(it + " RxJava is awesome")
            }

        disposable = observable.subscribe {
                Log.i("Log thread", "Result $it thread: ${Thread.currentThread().name}")
            }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}