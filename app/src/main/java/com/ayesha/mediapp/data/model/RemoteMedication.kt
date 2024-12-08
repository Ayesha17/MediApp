package com.ayesha.mediapp.data.model

data class RemoteMedication(
    val problemList: List<Problem>
)

data class Problem(
    val problemName: String = "",
    val drugList: List<Drug> = emptyList(),
    val drugAssociatedList: List<Drug> = emptyList(),
    val labs: List<Lab> = emptyList(),
)