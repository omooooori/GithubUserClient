package com.omooooori.design.component.material3

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.omooooori.design.theme.GithubUserClientTheme

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppTextLightPreview() {
    GithubUserClientTheme(darkTheme = false) {
        AppText(
            text = "All developer should use this ui component\nfor all text ui in this application.",
        )
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppTextDarkPreview() {
    GithubUserClientTheme(darkTheme = true) {
        AppText(
            text = "All developer should use this ui component\nfor all text ui in this application.",
        )
    }
}
