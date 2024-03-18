package kz.onelab.weatherapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private lateinit var viewModel: MainViewModel
    private var list = emptyList<WeatherResponse>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        val recyclerView = binding.weatherRecyclerView
        val adapter = WeatherAdapter()
        recyclerView.adapter = adapter


        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { weatherResponse ->
            weatherResponse?.let {
                list = list.toMutableList().apply { add(it) }
                adapter.submitList(list.toList())
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        list = emptyList()
        super.onDestroy()
    }

}