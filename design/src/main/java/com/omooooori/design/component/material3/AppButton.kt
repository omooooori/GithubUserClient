package com.omooooori.design.component.material3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omooooori.design.theme.GithubUserClientTheme

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.5f),
        ),
    ) {
        AppText(text = text)
    }
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppButtonLightPreview() {
    GithubUserClientTheme(darkTheme = false) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            repeat(2) { index ->
                AppButton(
                    text = "OK",
                    onClick = {},
                    enabled = index % 2 == 0
                )
            }
        }
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppButtonDarkPreview() {
    GithubUserClientTheme(darkTheme = true) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            repeat(2) { index ->
                AppButton(
                    text = "OK",
                    onClick = {},
                    enabled = index % 2 == 0
                )
            }
        }
    }
}
