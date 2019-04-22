package net.paulacr.fluffycat.livedata

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread

class LiveDataWithAction {

    private val liveData = MutableLiveData<Boolean?>()

    @MainThread
    fun observe(owner: LifecycleOwner, observer: () -> Unit) {
        liveData.observe(owner, Observer { value ->
            value?.let {
                observer()
            }
        })
    }

    @MainThread
    fun actionOccurred(value: Boolean = true) {
        liveData.value = value
    }

    fun actionOccuredPost(value: Boolean = true) {
        liveData.postValue(value)
    }

    fun getValue(): Boolean? {
        return liveData.value
    }
}