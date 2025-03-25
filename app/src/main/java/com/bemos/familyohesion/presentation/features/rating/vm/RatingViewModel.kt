package com.bemos.familyohesion.presentation.features.rating.vm

import androidx.lifecycle.ViewModel
import com.bemos.familyohesion.domain.use_cases.GetFamilyIdForCurrentUserUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyRatingsUseCase
import com.bemos.familyohesion.presentation.features.rating.utils.model.FamilyRating
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class RatingViewModel @Inject constructor(
    private val getFamilyIdForCurrentUserUseCase: GetFamilyIdForCurrentUserUseCase,
    private val getFamilyRatingsUseCase: GetFamilyRatingsUseCase
) : ViewModel() {
    private val _callback = MutableStateFlow<String?>(null)
    val callback: StateFlow<String?> get() = _callback

    private val _familyRatingCallback = MutableStateFlow<List<FamilyRating>>(listOf())
    val familyRatingCallback: StateFlow<List<FamilyRating>> get() = _familyRatingCallback

    init {
        getFamilyId()
    }

    fun getFamilyRating() {
        getFamilyRatingsUseCase.execute { family ->
            _familyRatingCallback.update {
                family
            }
        }
    }

    private fun getFamilyId() {
        getFamilyIdForCurrentUserUseCase.execute { id ->
            _callback.update {
                id
            }
        }
    }
}