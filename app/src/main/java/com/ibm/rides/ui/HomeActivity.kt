package com.ibm.rides.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.ibm.rides.R
import com.ibm.rides.core.utils.Constants
import com.ibm.rides.core.utils.collectLatestLifecycleFlow
import com.ibm.rides.databinding.ActivityHomeBinding
import com.ibm.rides.remote.dto.VehicleModelItem
import com.ibm.rides.ui.fragments.VehicleDetailsFragment
import com.ibm.rides.ui.fragments.VehicleListFragment
import com.ibm.rides.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        submitFragment(VehicleListFragment(), null)
        initViewModelListener()

    }

    private fun initViewModelListener() {
        collectLatestLifecycleFlow(viewModel.uiFlowShareData) {
            submitFragment(VehicleDetailsFragment(), it)
        }

        collectLatestLifecycleFlow(viewModel.uiFlowBack) {
            submitFragment(VehicleListFragment(), null)
        }
    }

    private fun submitFragment(fragment: Fragment, item: VehicleModelItem?) {
        if (item != null) {
            val bundle = Bundle()
            bundle.putString(Constants.VEHICLE_EXTRA, Gson().toJson(item))
            fragment.arguments = bundle;
        }
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

}