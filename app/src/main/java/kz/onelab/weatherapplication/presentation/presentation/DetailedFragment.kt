package kz.onelab.weatherapplication.presentation.presentation

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import kz.onelab.weatherapplication.core.base.BaseFragment
import kz.onelab.weatherapplication.databinding.FragmentDetailedBinding
import kz.onelab.weatherapplication.databinding.FragmentWeatherListBinding

@AndroidEntryPoint
class DetailedFragment : BaseFragment<FragmentDetailedBinding>(FragmentDetailedBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}