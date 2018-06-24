package ca.nick.mvi_fun2

import io.reactivex.Observable
import java.util.*

object Repository {

    private val locales: List<Locale> = Locale.getAvailableLocales()
        .filter { it.displayCountry.isNotEmpty() }
        .distinctBy { it.displayCountry }

    fun getLocales(): Observable<Locale> = Observable.fromIterable(locales)
}