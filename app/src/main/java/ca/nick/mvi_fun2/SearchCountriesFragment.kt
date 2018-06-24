package ca.nick.mvi_fun2

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding2.widget.textChanges
import kotlinx.android.synthetic.main.fragment_search_countries.*
import kotlinx.android.synthetic.main.item_country.view.*

class SearchCountriesFragment : MviFragment<SearchCountriesView, SearchCountriesPresenter>(),
    SearchCountriesView {

    private val adapter = CountriesAdapter()

    companion object {
        fun create() = SearchCountriesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_countries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_results.adapter = adapter
    }

    override fun createPresenter() = SearchCountriesPresenter()

    override fun typedText() = search.textChanges()

    override fun render(states: List<Resource<Country>>) = adapter.submitList(states)

    class CountriesAdapter
        : ListAdapter<Resource<Country>, CountryViewHolder>(CountryDiffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_country, parent, false)
                .let { CountryViewHolder(it) }

        override fun onBindViewHolder(holder: CountryViewHolder, position: Int) =
            holder.bind(getItem(position))
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(country: Resource<Country>) {
            setLoading(country is Resource.Loading)
            setError(country is Resource.Error)
            if (country is Resource.Success) {
                setSuccess(country.data)
            }
        }

        private fun setLoading(isLoading: Boolean) {
            itemView.progressBar.visible = isLoading
        }

        private fun setError(isError: Boolean) {
            itemView.error.visible = isError
        }

        private fun setSuccess(country: Country) {
            itemView.success_group.visible = true
            itemView.flag.text = country.flag
            itemView.name.text = country.name
        }
    }

    object CountryDiffCallback : DiffUtil.ItemCallback<Resource<Country>>() {

        override fun areItemsTheSame(
            oldItem: Resource<Country>,
            newItem: Resource<Country>
        ): Boolean {
            return oldItem is Resource.Success && newItem is Resource.Success
                    && oldItem.data.code == newItem.data.code
        }

        override fun areContentsTheSame(
            oldItem: Resource<Country>,
            newItem: Resource<Country>
        ) = oldItem is Resource.Success && newItem is Resource.Success
                && oldItem.data == newItem.data
    }
}