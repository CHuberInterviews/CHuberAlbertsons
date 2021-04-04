package com.psiance.alberstons_acronym.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.psiance.albertsons_acronym_api.views.adapter.AcronymListAdapter
import com.psiance.albertsons_acronym_api.R
import com.psiance.albertsons_acronym_api.databinding.FragmentAcronymListBinding
import com.psiance.albertsons_acronym_api.views.viewmodel.AcronymViewModel
import kotlinx.android.synthetic.main.activity_acronym.*


class ListFragment : Fragment() {

    val viewModel by activityViewModels<AcronymViewModel>()

    private lateinit var binding: FragmentAcronymListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAcronymListBinding.inflate(layoutInflater)
        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        requireActivity().toolbar.title = viewModel.acronymName.value?.toUpperCase()

        viewModel.adapter = AcronymListAdapter {
            viewModel.setSelectedAcronym(it)
            findNavController().navigate(R.id.action_acronymListFragment_to_acronymDetailsFragment)
        }
    }

}