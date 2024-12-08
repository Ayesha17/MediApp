package com.ayesha.mediapp.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayesha.core.ViewState
import com.ayesha.mediapp.data.model.Drug
import com.ayesha.mediapp.data.model.Problem
import com.ayesha.mediapp.domain.GetMedicineUseCase
import com.ayesha.mediapp.domain.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase, private val getMedicineUseCase: GetMedicineUseCase
) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        getCurrentUser()
        getMedicines()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            getUserUseCase(Unit) { userResult ->
                userResult.result(onSuccess = { user ->
                    _homeUiState.update {
                        it.copy(userName = user.userName.orEmpty())
                    }
                }, onError = {})

            }
        }
    }

    private fun getMedicines() {
        _homeUiState.update {
            it.copy(
                viewState = ViewState.LOADING,
            )
        }
        viewModelScope.launch {
            getMedicineUseCase(Unit) { medicineResult ->
                medicineResult.result(onSuccess = { medicine ->
                    val medicineList = arrayListOf<Drug>()
                    medicine.problemList.forEach { problem ->
                        medicineList.addAll(problem.drugList)
                        medicineList.addAll(problem.drugAssociatedList)
                    }
                    _homeUiState.update {
                        it.copy(
                            viewState = ViewState.SUCCESS,
                            medicineList = medicineList,
                            problemData = medicine.problemList
                        )
                    }
                }, onError = {
                    _homeUiState.update {
                        it.copy(
                            viewState = ViewState.ERROR,
                        )
                    }
                })
            }
        }
    }

    fun findProblemByMedicineName(
        medicineName: String
    ) {
        var item: Problem? = null
        var selectedDrug=Drug()
        homeUiState.value.problemData.forEach { problem ->
            val drug = problem.drugList.find { it.name == medicineName }
            if (drug != null) {
                item = problem
                selectedDrug=drug
            }
            val drug2 = problem.drugAssociatedList.find { it.name == medicineName }
            if (drug2 != null) {
                item = problem
                selectedDrug=drug2
            }
        }
        _homeUiState.update {
            it.copy(selectedProblem = item, showBottomSheet = true, selectedDrug = selectedDrug)
        }

    }

    fun dimissBottomSheet() {
        _homeUiState.update {
            it.copy(  showBottomSheet = false )
        }
    }

}

data class HomeUiState(
    val viewState: ViewState = ViewState.EMPTY,
    val userName: String = "",
    val medicineList: List<Drug> = emptyList(),
    val problemData: List<Problem> = emptyList(),
    val selectedProblem: Problem? = null,
    val selectedDrug: Drug= Drug(),
    val showBottomSheet: Boolean = false
)
