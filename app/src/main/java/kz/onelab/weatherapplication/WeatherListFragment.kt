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
    private lateinit var adapter: WeatherAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        val recyclerView = binding.weatherRecyclerView
        adapter = WeatherAdapter()
        recyclerView.adapter = adapter

        viewModel.weatherListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter.notifyDataSetChanged()
        recyclerView.setHasFixedSize(true)

        return binding.root
    }

}