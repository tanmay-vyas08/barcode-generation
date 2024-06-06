package com.example.bbarcode

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bbarcode.ui.theme.BbarcodeTheme
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BbarcodeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BarcodeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

fun generateBarcode(content: String): Bitmap? {
    return try {
        val barcodeEncoder = BarcodeEncoder()
        barcodeEncoder.encodeBitmap(content, BarcodeFormat.CODE_128, 600, 300)
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }
}

@Composable
fun BarcodeScreen(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }
    var barcodeBitmap by remember { mutableStateOf<Bitmap?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Enter text to generate barcode") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            barcodeBitmap = generateBarcode(inputText)
        }) {
            Text("Generate Barcode")
        }
        Spacer(modifier = Modifier.height(16.dp))
        barcodeBitmap?.let {
            Image(bitmap = it.asImageBitmap(), contentDescription = "Generated Barcode")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BarcodeScreenPreview() {
    BbarcodeTheme {
        BarcodeScreen()
    }
}
