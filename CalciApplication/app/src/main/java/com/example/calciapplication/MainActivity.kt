package com.example.calciapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calciapplication.ui.theme.CalciApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalciApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("0") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(text = "1") { inputText = appendInput("1", inputText) }
            CalculatorButton(text = "2") { inputText = appendInput("2", inputText) }
            CalculatorButton(text = "3") { inputText = appendInput("3", inputText) }
            CalculatorButton(text = "+") { inputText = appendInput("+", inputText) }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(text = "4") { inputText = appendInput("4", inputText) }
            CalculatorButton(text = "5") { inputText = appendInput("5", inputText) }
            CalculatorButton(text = "6") { inputText = appendInput("6", inputText) }
            CalculatorButton(text = "-") { inputText = appendInput("-", inputText) }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(text = "7") { inputText = appendInput("7", inputText) }
            CalculatorButton(text = "8") { inputText = appendInput("8", inputText) }
            CalculatorButton(text = "9") { inputText = appendInput("9", inputText) }
            CalculatorButton(text = "*") { inputText = appendInput("*", inputText) }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(text = ".") { inputText = appendInput(".", inputText) }
            CalculatorButton(text = "0") { inputText = appendInput("0", inputText) }
            CalculatorButton(text = "=") { inputText = calculateResult(inputText) }
            CalculatorButton(text = "/") { inputText = appendInput("/", inputText) }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(text = "C") { inputText = clearInput(inputText) }
        }
    }
}

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}

fun appendInput(text: String, inputText: String): String {
    return inputText + text
}

fun clearInput(inputText: String): String {
    return ""
}

fun calculateResult(inputText: String): String {
    val result = try {
        evaluateExpression(inputText)
    } catch (e: ArithmeticException) {
        "Error"
    }
    // Update inputText with the result
    return result.toString()
}

fun evaluateExpression(expression: String): Double {
    // Split the expression by operators (+, -, *, /)
    val operands = expression.split(Regex("[+\\-*/]"))
    val operator = expression.find { it.isDigit().not() } ?: return operands.firstOrNull()?.toDoubleOrNull() ?: 0.0

    // Get the first and second operands
    val operand1 = operands[0].toDouble()
    val operand2 = operands[1].toDoubleOrNull() ?: throw ArithmeticException()

    // Perform the calculation based on the operator
    return when (operator) {
        '+' -> operand1 + operand2
        '-' -> operand1 - operand2
        '*' -> operand1 * operand2
        '/' -> operand1 / operand2
        else -> throw ArithmeticException()
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalciApplicationTheme {
        Calculator()
    }
}
