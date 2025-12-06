package com.dylowen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun SelectableTextWithCopy(text: String, modifier: Modifier = Modifier) {
//    val clipboard = LocalClipboard.current
    val clipboardManager = LocalClipboardManager.current

    Row(
        modifier = modifier
    ) {
        TextButton(onClick = {
            clipboardManager.setText(AnnotatedString(text))
        }) {
            Text("\uD83D\uDCCB")
        }

        SelectionContainer(
            modifier = Modifier.padding(5.dp)
                .background(colorScheme.outlineVariant)
                .padding(5.dp),
        ) {
            Text(text)
        }
    }
}