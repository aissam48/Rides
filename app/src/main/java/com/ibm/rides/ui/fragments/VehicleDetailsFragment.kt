package com.ibm.rides.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibm.rides.core.utils.Constants
import com.ibm.rides.databinding.FragmentVehicleDetailsBinding
import com.ibm.rides.remote.dto.VehicleModelItem
import com.ibm.rides.ui.viewmodel.HomeViewModel


class VehicleDetailsFragment : Fragment() {

    private lateinit var binding: FragmentVehicleDetailsBinding
    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentVehicleDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val itemAsString = bundle?.getString(Constants.VEHICLE_EXTRA)
        if (itemAsString != null) {
            val item = Gson().fromJson<VehicleModelItem>(
                itemAsString, object : TypeToken<VehicleModelItem>() {}.type
            )
            bindDataWithUI(item)
        }

        setUpClicks()

    }

    private fun setUpClicks() {
        binding.ivBack.setOnClickListener {
            viewModel.back()
        }
    }


    private fun bindDataWithUI(item: VehicleModelItem?) {
        if (item == null) {
            return
        }

        binding.tvVinValue.text = item.vin
        binding.tvMakeAndModelValue.text = item.make_and_model
        binding.tvColorValue.text = item.color
        binding.tvCarTypeValue.text = item.car_type

    }


}