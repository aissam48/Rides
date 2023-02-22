package com.ibm.rides.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ibm.rides.R
import com.ibm.rides.core.utils.*
import com.ibm.rides.databinding.FragmentVehicleListBinding
import com.ibm.rides.remote.dto.VehicleModelItem
import com.ibm.rides.ui.fragments.adapter.VehicleListAdapter
import com.ibm.rides.ui.viewmodel.HomeViewModel


class VehicleListFragment : Fragment() {

    lateinit var binding: FragmentVehicleListBinding
    private val viewModel by activityViewModels<HomeViewModel>()
    lateinit var vehicleListAdapter: VehicleListAdapter
    private val listOfRides = mutableListOf<VehicleModelItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentVehicleListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        collectData()
        setUpClicks()
    }


    private fun setUpClicks() {

        binding.buttonGetRides.setOnClickListener {
            val size = binding.etValues.text.toString().trim()
            val params = hashMapOf<String, Any>()
            params[ApiField.SIZE] = size
            viewModel.onGetRides(params)
            binding.etValues.text.clear()
            requireActivity().hideKeyboard()
        }

    }

    private fun initAdapter() {
        vehicleListAdapter = VehicleListAdapter {
            viewModel.shareData(it)
        }

        binding.rvRides.adapter = vehicleListAdapter
    }


    private fun collectData() {

        requireActivity().collectLatestLifecycleFlow(viewModel.uiFlowRides) {
            when (it) {
                is Resource.Loading -> {
                    updateLoading(true)

                }
                is Resource.Success -> {
                    updateLoading(false)
                    val ridesModel = it.data

                    if (ridesModel != null) {
                        if (ridesModel.data?.isEmpty() != true) {
                            listOfRides.clear()
                            listOfRides.addAll(ridesModel.data ?: arrayListOf())
                        }
                        bindDataWithUI(listOfRides)
                    }


                }
                is Resource.Error -> {
                    updateLoading(false)
                    when (it.errorBody?.statusCode) {
                        400 -> {
                        }
                        500 -> {
                        }
                    }
                }
            }
        }


    }

    private fun bindDataWithUI(listData: MutableList<VehicleModelItem>) {
        if (listData.isEmpty()) {
            showEmptyData()
        } else {
            hideEmptyData()
            val listSortedByVin = listData.sortedBy { it.vin }
            vehicleListAdapter.submitList(listSortedByVin)
            vehicleListAdapter.notifyDataSetChanged()
        }

    }

    private fun showEmptyData() {
        binding.rvRides.gone()
        binding.tvEmptyData.visible()
    }

    private fun hideEmptyData() {
        binding.rvRides.visible()
        binding.tvEmptyData.gone()
    }

    private fun updateLoading(b: Boolean) {

        if (b) {
            binding.buttonGetRides.text = ""
            binding.prLoadingData.visible()
        } else {
            if (context != null)
                binding.buttonGetRides.text =
                    requireContext().resources.getString(R.string.get_rides)
            binding.prLoadingData.gone()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}