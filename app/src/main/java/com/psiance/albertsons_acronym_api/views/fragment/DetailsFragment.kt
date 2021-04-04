package com.psiance.alberstons_acronym.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.psiance.albertsons_acronym_api.databinding.FragmentAcronymDetailsBinding
import com.psiance.albertsons_acronym_api.views.viewmodel.AcronymViewModel
import kotlinx.android.synthetic.main.activity_acronym.*

class DetailsFragment : Fragment() {

    val viewModel by activityViewModels<AcronymViewModel>()

    private lateinit var binding: FragmentAcronymDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAcronymDetailsBinding.inflate(layoutInflater)
        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().toolbar.title = viewModel.acronymName.value?.toUpperCase()
    }

}