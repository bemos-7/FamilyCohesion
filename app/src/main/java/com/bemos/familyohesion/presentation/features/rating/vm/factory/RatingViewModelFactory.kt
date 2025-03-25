package com.bemos.familyohesion.presentation.features.rating.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.GetFamilyIdForCurrentUserUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyRatingsUseCase
import com.bemos.familyohesion.presentation.features.rating.vm.RatingViewModel

class RatingViewModelFactory(
    private val getFamilyIdForCurrentUserUseCase: GetFamilyIdForCurrentUserUseCase,
    private val getFamilyRatingsUseCase: GetFamilyRatingsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RatingViewModel(
            getFamilyIdForCurrentUserUseCase = getFamilyIdForCurrentUserUseCase,
            getFamilyRatingsUseCase = getFamilyRatingsUseCase
        ) as T
    }
}