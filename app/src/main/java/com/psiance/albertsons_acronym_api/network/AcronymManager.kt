package com.psiance.albertsons_acronym_api.network


import com.psiance.albertsons_acronym_api.network.responseDTO.AcronymResponseDTO
import com.psiance.albertsons_acronym_api.utils.GET_ACROYNM
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

class AcronymManager {
    private val service: AcronymService
    private val retrofit = RetrofitService.providesRetrofit()

    init {
        service = retrofit.create(AcronymService::class.java)
    }

    fun getAcronym(acronym: String) = service.getAcroynm(acronym)

    interface AcronymService {
        @GET(GET_ACROYNM)
        fun getAcroynm(
            @Query("sf") shortForm: String
        ): Single<List<AcronymResponseDTO>>
    }
}