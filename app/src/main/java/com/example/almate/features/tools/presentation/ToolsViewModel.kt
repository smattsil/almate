package com.example.almate.features.tools.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class Tool(
    val icon: Int,
    val name: String,
    val description: String,
    val route: Any
)

@HiltViewModel
class ToolsViewModel @Inject constructor(

) : ViewModel() {

}
