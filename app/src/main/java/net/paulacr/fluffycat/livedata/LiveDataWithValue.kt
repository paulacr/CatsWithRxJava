package net.paulacr.fluffycat.livedata

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread

class LiveDataWithValue<T> {

    private val liveData = MutableLiveData<T>()

    fun getData(): MutableLiveData<T> {
        return liveData
    }

    @MainThread
    fun observe(owner: LifecycleOwner, observer: (T) -> Unit) {
        liveData.observe(owner, Observer<T> { value ->
            value?.let { observer(value) }
        })
    }

    @MainThread
    fun observeForever(observer: (T) -> Unit) {
        liveData.observeForever { value ->
            value?.let { observer(value) }
        }
    }

    @MainThread
    fun actionOccurred(value: T?) {
        liveData.value = value
    }

    fun actionOccuredPost(value: T?) {
        liveData.postValue(value)
    }
}