package com.omooooori.design.component.material3

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.omooooori.design.theme.GithubUserClientTheme

@Composable
fun AppIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onSurface,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppIconLightPreview() {
    GithubUserClientTheme(darkTheme = false) {
        AppIcon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
        )
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppIconDarkPreview() {
    GithubUserClientTheme(darkTheme = true) {
        AppIcon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
        )
    }
}
