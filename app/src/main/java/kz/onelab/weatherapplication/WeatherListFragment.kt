package kz.onelab.weatherapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.onelab.weatherapplication.adapter.WeatherAdapter
import kz.onelab.weatherapplication.api.WeatherResponse
import kz.onelab.weatherapplication.databinding.FragmentWeatherInfoBinding
import kz.onelab.weatherapplication.databinding.FragmentWeatherListBinding


@AndroidEntryPoint
class WeatherListFragment : Fragment() {
    private lateinit var binding: FragmentWeatherListBinding
    private lateinit var adapter: WeatherAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.current.setOnClickListener {
            if (!binding.cityInput.text.isNullOrBlank()) {
                viewModel.getAllData(binding.cityInput.text.toString())
            }
            else Toast.makeText(this.context, "Input city name", Toast.LENGTH_SHORT).show()
        }
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = WeatherAdapter()

        binding.weatherRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@WeatherListFragment.adapter
            setHasFixedSize(true)
        }

        viewModel.weatherListLiveData.observe(viewLifecycleOwner) { weatherList ->
            adapter.submitList(weatherList?.items)
        }
    }

}