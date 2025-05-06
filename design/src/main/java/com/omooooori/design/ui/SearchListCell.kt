package com.omooooori.design.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omooooori.design.component.material3.AppIcon
import com.omooooori.design.component.material3.AppText
import com.omooooori.design.component.other.AppAsyncImage
import com.omooooori.design.theme.GithubUserClientTheme

@Composable
fun SearchListCell(
    username: String,
    avatarUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
) {
    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = shape,
        tonalElevation = 2.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp),
        ) {
            AppAsyncImage(
                model = avatarUrl,
                contentDescription = "Avatar",
                modifier =
                    Modifier
                        .size(48.dp)
                        .clip(MaterialTheme.shapes.small),
            )

            Spacer(modifier = Modifier.width(16.dp))

            AppText(
                text = username,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
            )

            AppIcon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Go to detail",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchListCellLightPreview() {
    GithubUserClientTheme {
        SearchListCell(
            username = "omooooori",
            avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4",
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchListCellDarkPreview() {
    GithubUserClientTheme(darkTheme = true) {
        SearchListCell(
            username = "omooooori",
            avatarUrl = "https://avatars.githubusercontent.com/u/583231?v=4",
            onClick = {},
        )
    }
}
