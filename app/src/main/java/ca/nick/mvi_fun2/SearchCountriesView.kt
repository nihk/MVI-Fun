package ca.nick.mvi_fun2

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface SearchCountriesView : MvpView {

    fun typedText(): Observable<CharSequence>
    fun render(states: List<Resource<Country>>)
}