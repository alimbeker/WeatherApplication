package kz.onelab.weatherapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.onelab.weatherapplication.adapter.WeatherAdapter
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
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        val args = WeatherListFragmentArgs.fromBundle(requireArguments())
        viewModel.getAllData(args.city)

        setupRecyclerView()
        setUpError()

        return binding.root
    }

    private fun setUpError() {
        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = WeatherAdapter()

        binding.weatherRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@WeatherListFragment.adapter
            setHasFixedSize(true)
        }

        viewModel.weatherListLiveData.observe(viewLifecycleOwner) { weatherList ->
            adapter.submitList(weatherList)
        }
    }

}