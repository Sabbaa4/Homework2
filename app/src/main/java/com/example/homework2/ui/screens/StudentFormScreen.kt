package com.example.homework2.ui.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework2.ui.components.CustomTextField
import java.util.*

@Composable
fun StudentFormScreen() {
    var nameState by remember { mutableStateOf("") }
    var surnameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val formattedDay = String.format("%02d", dayOfMonth)
            val formattedMonth = String.format("%02d", month + 1)
            dateState = "$formattedDay/$formattedMonth/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Custom Colors for Unique UI
    val bgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF1E1E2C), Color(0xFF2A2A40))
    )
    val accentColor = Color(0xFF00E5FF)
    val cardColor = Color(0xFF32324E)
    val textColor = Color.White
    val hintColor = Color(0xFFA0A0B5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Student Form",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = accentColor,
            modifier = Modifier.padding(bottom = 32.dp, top = 24.dp)
        )

        // Text Fields
        CustomTextField(
            value = nameState,
            onValueChange = { nameState = it },
            label = "Name",
            cardColor = cardColor,
            textColor = textColor,
            hintColor = hintColor,
            accentColor = accentColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = surnameState,
            onValueChange = { surnameState = it },
            label = "Surname",
            cardColor = cardColor,
            textColor = textColor,
            hintColor = hintColor,
            accentColor = accentColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = emailState,
            onValueChange = { emailState = it },
            label = "Email",
            cardColor = cardColor,
            textColor = textColor,
            hintColor = hintColor,
            accentColor = accentColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Date Picker Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(cardColor)
                .clickable { datePickerDialog.show() }
                .padding(16.dp)
        ) {
            Column {
                Text("Date of Birth", fontSize = 12.sp, color = hintColor)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = dateState.ifEmpty { "DD/MM/YYYY" },
                    color = if (dateState.isEmpty()) hintColor else textColor,
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Radio Buttons Title
        Text(
            text = "Favorite Direction",
            fontSize = 18.sp,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(12.dp))

        val options = listOf("Android", "iOS", "Web")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(cardColor)
                .padding(12.dp)
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { selectedOption = option }
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                ) {
                    RadioButton(
                        selected = (selectedOption == option),
                        onClick = { selectedOption = option },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = accentColor,
                            unselectedColor = hintColor
                        )
                    )
                    Text(
                        text = option,
                        color = textColor,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Switch row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(cardColor)
                .padding(16.dp)
        ) {
            Text(
                text = "ვეთანხმები წესებს და პირობებს",
                color = textColor,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isAgreed,
                onCheckedChange = { isAgreed = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = accentColor,
                    uncheckedThumbColor = hintColor,
                    uncheckedTrackColor = Color(0xFF4A4A6A)
                )
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        // Submit Button
        Button(
            onClick = {
                val isTextFilled = nameState.isNotBlank() && 
                                   surnameState.isNotBlank() && 
                                   emailState.isNotBlank() && 
                                   dateState.isNotBlank()
                val isOptionSelected = selectedOption.isNotBlank()
                
                if (isTextFilled && isOptionSelected && isAgreed) {
                    Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accentColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "SUBMIT",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
