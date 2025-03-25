package com.bemos.familyohesion.presentation.features.rating

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.presentation.features.rating.vm.RatingViewModel
import com.bemos.familyohesion.presentation.features.rating.vm.factory.RatingViewModelFactory

@Composable
fun RatingScreen(
    navController: NavController,
    ratingViewModelFactory: RatingViewModelFactory
) {
    var context = LocalContext.current
    val viewModel = viewModel<RatingViewModel>(
        factory = ratingViewModelFactory
    )
    val familyId by viewModel.callback.collectAsState()
    val familyRatings by viewModel.familyRatingCallback.collectAsState()
    Log.d("askdjfhk", familyRatings.toString())
    LaunchedEffect(Unit) {
        viewModel.getFamilyRating()
    }
    RatingContent(
        families = familyRatings,
        currentFamilyId = familyId
    )
}