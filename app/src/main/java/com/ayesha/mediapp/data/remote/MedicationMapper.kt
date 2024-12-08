package com.ayesha.mediapp.data.remote

import com.ayesha.mediapp.data.model.ApiResponse
import com.ayesha.mediapp.data.model.Drug
import com.ayesha.mediapp.data.model.Lab
import com.ayesha.mediapp.data.model.Problem
import com.ayesha.mediapp.data.model.RemoteMedication

class MedicationMapper {
    fun map(apiResponse: ApiResponse): RemoteMedication {
        val problem = arrayListOf<Problem>()
        apiResponse.problems.forEach { problemMap ->
            problemMap.forEach { (problemName, details) ->
                println("Problem: $problemName")
                val drugList = arrayListOf<Drug>()
                val drugAssociateList = arrayListOf<Drug>()
                val labList = arrayListOf<Lab>()
                details.forEach { detail ->
                    println("Medications: ${detail.medications}")
                    detail.medications?.forEach { medicine ->
                        medicine.medicationsClasses?.forEach { medicineClass ->
                            medicineClass.className?.forEach { drugs ->
                                drugs.associatedDrug?.map { it1 ->
                                    drugList.add(
                                        Drug(it1.name, it1.dose, it1.strength, 1)
                                    )
                                }

                                drugs.associatedDrug2?.map { it1 ->
                                    drugAssociateList.add(
                                        Drug(it1.name, it1.dose, it1.strength, 1)
                                    )
                                }
                            }
                            medicineClass.className2?.forEach { drugs ->
                                drugs.associatedDrug?.map { it1 ->
                                    drugList.add(
                                        Drug(it1.name, it1.dose, it1.strength, 2)
                                    )
                                }
                                drugs.associatedDrug2?.map { it1 ->
                                    drugAssociateList.add(
                                        Drug(it1.name, it1.dose, it1.strength, 2)
                                    )
                                }

                            }
                        }
                    }
                    detail.labs?.let { labList.addAll(it) }

                }
                problem.add(Problem(problemName, drugList, drugAssociateList, labList))
            }


        }
        return RemoteMedication(problem)
    }
}