package com.dylowen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

@Composable
fun SelectableTextWithCopy(text: String) {
//    val clipboard = LocalClipboard.current
    val clipboardManager = LocalClipboardManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SelectionContainer(modifier = Modifier.weight(1f)) {
            Text(text)
        }

        IconButton(onClick = {
            clipboardManager.setText(AnnotatedString(text))
        }) {
            Icon(Icons.Default.Share, contentDescription = "Copy")
        }
    }
}