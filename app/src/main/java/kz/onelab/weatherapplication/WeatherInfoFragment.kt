package kz.onelab.weatherapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import kz.onelab.weatherapplication.adapter.WeatherAdapter
import kz.onelab.weatherapplication.api.WeatherResponse
import kz.onelab.weatherapplication.databinding.FragmentWeatherInfoBinding


class WeatherInfoFragment : Fragment() {
    private lateinit var binding: FragmentWeatherInfoBinding
    private lateinit var viewModel: MainViewModel
    private var list = emptyList<WeatherResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeatherInfoBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val recyclerView = binding.weatherRecyclerView
        val adapter = WeatherAdapter()
        recyclerView.adapter = adapter

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { weatherResponse ->
            weatherResponse?.let {
                list = list.toMutableList().apply { add(it) }
                adapter.submitList(list)
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        list = emptyList()
        super.onDestroy()
    }

}