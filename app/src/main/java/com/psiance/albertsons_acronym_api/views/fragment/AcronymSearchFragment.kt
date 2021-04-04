package com.psiance.alberstons_acronym.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.psiance.albertsons_acronym_api.databinding.FragmentAcronymSearchBinding
import com.psiance.albertsons_acronym_api.views.viewmodel.AcronymViewModel
import com.psiance.albertsons_acronym_api.R

class AcronymSearchFragment : Fragment() {

    private val viewModel by activityViewModels<AcronymViewModel>()

    private lateinit var binding: FragmentAcronymSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAcronymSearchBinding.inflate(layoutInflater)
        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        viewModel.navigateToAcronymListView.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_acronymSearchFragment_to_acronymListFragment)
                viewModel.navigateToAcronymListView.value = false
            }
            viewModel.progressBarVisibility.value = false
        }

        viewModel.acronymName.observe(viewLifecycleOwner) {
            viewModel.progressBarVisibility.value = false
            viewModel.liveDataAcronymSearchError.value = ""
        }
    }
}