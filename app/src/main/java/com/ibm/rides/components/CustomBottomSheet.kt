package com.ibm.rides.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ibm.rides.R
import com.ibm.rides.core.utils.Calculation
import com.ibm.rides.databinding.CustomBottomSheetBinding
import com.ibm.rides.remote.dto.VehicleModelItem

class CustomBottomSheet(private val vehicle: VehicleModelItem) : BottomSheetDialogFragment() {

    lateinit var binding: CustomBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CustomBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvEmittedCarbonValue.text = "${Calculation.calculateCarbonEmitted(vehicle.kilometrage)} of carbon"

    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme


}