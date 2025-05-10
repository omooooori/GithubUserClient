package com.omooooori.design.component.material3

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.omooooori.design.theme.GithubUserClientTheme

@Composable
fun AppErrorDialog(
    message: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            AppText("Error")
        },
        text = { Text(message) },
        confirmButton = {
            AppButton(
                text = "OK",
                onClick = onDismiss,
            )
        }
    )
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppErrorDialogLightPreview() {
    GithubUserClientTheme(darkTheme = false) {
        AppErrorDialog(
            message = "This ui should be used for showing information or error to users in common.",
            onDismiss = {}
        )
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppErrorDialogDarkPreview() {
    GithubUserClientTheme(darkTheme = true) {
        AppErrorDialog(
            message = "This ui should be used for showing information or error to users in common.",
            onDismiss = {}
        )
    }
}
