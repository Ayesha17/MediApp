package com.ayesha.mediapp.data.model

data class ApiResponse(
    val problems: List<Map<String, List<ProblemDetail>>>
)

data class ProblemDetail(
    val medications: List<Medication>? = null,
    val labs: List<Lab>? = null
)

data class Medication(
    val medicationsClasses: List<MedicationsClass>? = null
)

data class MedicationsClass(
    val className: List<ClassName>? = null,
    val className2: List<ClassName>? = null
)

data class ClassName(
    val associatedDrug: List<Drug>? = null,
    val associatedDrug2: List<Drug>? = null
)

data class Drug(
    val name: String? = null,
    val dose: String? = null,
    val strength: String? = null,
    val classType:Int =0
)

data class Lab(
    val missing_field: String? = null
)
