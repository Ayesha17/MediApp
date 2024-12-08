package com.ayesha.mediapp.ui.home.view

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayesha.core.ViewState
import com.ayesha.mediapp.R
import com.ayesha.mediapp.data.model.Drug
import com.ayesha.mediapp.ui.common.BaseAnimatedLoader
import com.ayesha.mediapp.ui.common.BaseGenericBottomSheet
import com.ayesha.mediapp.ui.common.BaseToast
import com.ayesha.mediapp.ui.common.ToastType
import com.ayesha.mediapp.ui.home.viewmodel.HomeUiState
import com.ayesha.mediapp.ui.home.viewmodel.HomeViewModel
import java.util.Calendar

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val homeUiState by homeViewModel.homeUiState.collectAsState()

    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.app_name),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        GreetingSection(homeUiState.userName)
        when (homeUiState.viewState) {
            ViewState.LOADING -> {
                BaseAnimatedLoader()
            }

            ViewState.ERROR -> {
                BaseToast(ToastType.ERROR, stringResource(id = R.string.something_went_wrong))
            }

            ViewState.SUCCESS -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(homeUiState.medicineList) {
                        MedicineItem(it) { name ->
                            homeViewModel.findProblemByMedicineName(name)

                        }
                    }
                }
            }

            ViewState.EMPTY -> {

            }

        }

    }
    if (homeUiState.showBottomSheet) {
        DetailBottomSheet( homeUiState){
            homeViewModel.dimissBottomSheet()
        }
    }
}

@Composable
private fun GreetingSection(userName: String) {
    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(getGreeting( Calendar.getInstance())),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = userName,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun MedicineItem(
    it: Drug,
    onClickItem: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onClickItem.invoke(it.name.orEmpty())
            }
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
    ) {
        Image(
            modifier = Modifier
                .size(32.dp)
                .padding(end = 8.dp),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )

        DrugDetail(it, Modifier.weight(1f))
        Image(
            modifier = Modifier
                .size(24.dp)
                .padding(start = 8.dp),
            painter = painterResource(id = R.drawable.ic_arrow_right_gray),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailBottomSheet(
     homeUiState: HomeUiState,
     onDismiss:()->Unit
) {
    BaseGenericBottomSheet(onDismiss = {
                                       onDismiss.invoke()
    }, content = {
        val problem = homeUiState.selectedProblem
        Column(modifier= Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(end = 8.dp, bottom = 16.dp),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                text = homeUiState.selectedDrug.name.orEmpty(),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(
                    id = R.string.dose_strength,
                    homeUiState.selectedDrug.dose.orEmpty(),
                    homeUiState.selectedDrug.strength.orEmpty()
                ),
                color = Color.Black,
                fontSize = 14.sp,
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                text = stringResource(id = R.string.problem, problem?.problemName.orEmpty()),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            val classType = problem?.drugList?.map { it.classType }?.distinct()
            classType?.forEach { classType ->
                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    text = stringResource(id = R.string.class_num, classType),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                val filterDrug = problem.drugList.filter { it.classType == classType }
                if (filterDrug.isNotEmpty()) {
                    AssociatedDrugSection(1, filterDrug)
                }
                val filterDrugList = problem.drugAssociatedList.filter { it.classType == classType }
                if (filterDrugList.isNotEmpty()) {
                    Spacer(Modifier.size(16.dp))
                    AssociatedDrugSection(2, filterDrugList)
                }
                Spacer(Modifier.size(16.dp))
            }

        }
    })
}

@Composable
private fun AssociatedDrugSection(
    classType: Int,
    filterDrugList: List<Drug>
) {
    Text(
        modifier = Modifier
            .padding(bottom = 16.dp),
        text = stringResource(id = R.string.associated_drug, classType),
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    LazyColumn {
        items(filterDrugList) {
            DrugDetail(it)
        }
    }
}

@Composable
private fun DrugDetail(it: Drug, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier

    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp),
            text = it.name.orEmpty(),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(
                id = R.string.dose_strength,
                it.dose.orEmpty(),
                it.strength.orEmpty()
            ),
            color = Color.Black,
            fontSize = 14.sp,
        )

    }
}


fun getGreeting(calendar: Calendar): Int {
    val currentHour =calendar.get(Calendar.HOUR_OF_DAY)
    return when (currentHour) {
        in 0..11 -> R.string.good_morning
        in 12..17 -> R.string.good_afternoon
        in 18..23 -> R.string.good_evening
        else -> R.string.hello
    }
}