package com.omooooori.design.component.material3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.omooooori.design.theme.GithubUserClientTheme

@Composable
fun AppIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String?,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    apiLevel = 33
)
@Composable
fun AppIconButtonLightPreview() {
    GithubUserClientTheme(darkTheme = false) {
        AppIconButton(
            icon = Icons.Default.AccountCircle,
            contentDescription = "",
            onClick = {}
        )
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    apiLevel = 33
)
@Composable
fun AppIconButtonDarkPreview() {
    GithubUserClientTheme(darkTheme = true) {
        AppIconButton(
            icon = Icons.Default.AccountCircle,
            contentDescription = "",
            onClick = {}
        )
    }
}
