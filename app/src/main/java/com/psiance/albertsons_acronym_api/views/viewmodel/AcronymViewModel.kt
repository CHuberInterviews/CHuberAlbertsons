package com.psiance.albertsons_acronym_api.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.psiance.albertsons_acronym_api.models.LongFormDTO
import com.psiance.albertsons_acronym_api.network.AcronymManager
import com.psiance.albertsons_acronym_api.network.responseDTO.AcronymResponseDTO
import com.psiance.albertsons_acronym_api.utils.RxSingleSchedulers
import com.psiance.albertsons_acronym_api.views.adapter.AcronymListAdapter
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.annotations.TestOnly


/**
 * The main view model for the app. In this case since there is one requirement. We have one view model
 * This view model is shared among all the fragment, so that the data does not need to be transferred from one fragment to
 * another fragment
 */
class AcronymViewModel : ViewModel() {
    val acronymList: ArrayList<LongFormDTO> = ArrayList()
    var adapter: AcronymListAdapter? = null
    private var disposable: CompositeDisposable? = CompositeDisposable()
    private var _selectedAcronymData: MutableLiveData<LongFormDTO> = MutableLiveData()
    var selectedAcronymData: LiveData<LongFormDTO>? = _selectedAcronymData

    var acronymName: MutableLiveData<String> = MutableLiveData()
    var liveDataAcronymSearchError: MutableLiveData<String> = MutableLiveData()
    val navigateToAcronymListView: MutableLiveData<Boolean> = MutableLiveData()
    var progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData()

    private var rxSingleSchedulers: RxSingleSchedulers = RxSingleSchedulers.DEFAULT

    /**
     * Search city
     */
    fun searchAcronym() {
        if (acronymName.value?.isNotBlank() == true) {
            progressBarVisibility.value = true
            disposable?.add(
                AcronymManager().getAcronym(acronymName.value ?: "")
                    .compose(rxSingleSchedulers.applySchedulers())
                    .subscribe(this::onSuccess, this::onFailure)
            )
        } else {
            liveDataAcronymSearchError.postValue("Enter Acronym")
        }
    }

    private fun onSuccess(ListAcronymResponseDTO: List<AcronymResponseDTO>){
        progressBarVisibility.value = false
        acronymList.clear()
        acronymList.addAll(ListAcronymResponseDTO.get(0).lfs)
        navigateToAcronymListView.postValue(true)
    }
    private fun onFailure(error: Throwable){
        liveDataAcronymSearchError.postValue(error.message ?: "Failed To Search")
        progressBarVisibility.value = false
    }
    fun setSelectedAcronym(LongFormDTO: LongFormDTO) {
        _selectedAcronymData.value = LongFormDTO
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.run {
            clear()
            null
        }
    }

    @TestOnly
    fun getResponseTest(value: String){
        acronymName.setValue(value)
        searchAcronym()
    }
    @TestOnly
    fun getItemsTest() = acronymList
}