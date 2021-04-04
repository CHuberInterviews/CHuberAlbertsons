package com.psiance.albertsons_acronym_api.network.responseDTO

import com.psiance.albertsons_acronym_api.models.LongFormDTO

data class AcronymResponseDTO(
        val sf: String?,
        val lfs: List<LongFormDTO>
)