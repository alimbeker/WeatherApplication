package kz.onelab.weatherapplication.presentation.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.onelab.weatherapplication.core.base.BaseFragment
import kz.onelab.weatherapplication.core.functional.Resource
import kz.onelab.weatherapplication.presentation.presentation.adapter.WeatherAdapter
import kz.onelab.weatherapplication.databinding.FragmentWeatherListBinding
import kz.onelab.weatherapplication.presentation.decoration.OffsetDecoration
import kz.onelab.weatherapplication.presentation.model.WeatherList


@AndroidEntryPoint
class WeatherListFragment : BaseFragment<FragmentWeatherListBinding>(FragmentWeatherListBinding::inflate) {
    private lateinit var adapter: WeatherAdapter
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val args = WeatherListFragmentArgs.fromBundle(requireArguments())
        viewModel.getAllData(args.city)
        setupRecyclerView()

        viewModel.weatherListLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loading.isVisible = true
                }

                is Resource.Success -> {
                    binding.loading.isVisible = false

                    val weatherData = resource.data
                    weatherData?.let { weatherList.add(weatherData) }
                    adapter.submitList(weatherList)
                }

                is Resource.Error -> {
                    binding.loading.isVisible = false

                    Toast.makeText(this.context, resource.exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> Unit
            }
        }
    }


    private fun setupRecyclerView() {
        adapter = WeatherAdapter()

        binding.weatherRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@WeatherListFragment.adapter
            setHasFixedSize(true)

            val offsetDecoration = OffsetDecoration(start = 4, top = 20, end = 2, bottom = 16)
            binding.weatherRecyclerView.addItemDecoration(offsetDecoration)
        }
    }

    companion object {
        val weatherList = mutableListOf<WeatherList>()
    }

}

