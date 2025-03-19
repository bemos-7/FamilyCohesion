package com.bemos.familyohesion.presentation.main_activity.vm

import androidx.lifecycle.ViewModel
import com.bemos.familyohesion.domain.use_cases.GetCurrentUserIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {

    private val _onResult = MutableStateFlow("")
    val onResult: StateFlow<String> get() = _onResult

    init {
        getCurrentUserId()
    }

    private fun getCurrentUserId() {
        val curUser = getCurrentUserIdUseCase.execute()
        if (curUser != null) {
            _onResult.update {
                curUser
            }
        }
    }

}