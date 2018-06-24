package ca.nick.mvi_fun2

import io.reactivex.Observable

object SearchCountriesUseCase {

    fun searchCountries(searchTerm: String): Observable<List<Resource<Country>>> =
        Repository.getLocales()
            .filter { it.displayCountry.contains(searchTerm, ignoreCase = true) }
            .map { Country(it.displayCountry, it.country) }
            .map<Resource<Country>> { Resource.Success(it) }
            .toList()
            .toObservable()
}