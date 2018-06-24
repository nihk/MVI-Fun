package ca.nick.mvi_fun2

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchCountriesPresenter
    : MviBasePresenter<SearchCountriesView, List<Resource<Country>>>() {

    override fun bindIntents() {
        val countries: Observable<List<Resource<Country>>> = intent(SearchCountriesView::typedText)
            .subscribeOn(Schedulers.io())
            .flatMap { text -> SearchCountriesUseCase.searchCountries(text.toString()) }
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(countries, SearchCountriesView::render)
    }
}