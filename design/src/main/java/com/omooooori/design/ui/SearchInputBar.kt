package com.omooooori.design.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omooooori.design.component.material3.AppTextField
import com.omooooori.design.theme.GithubUserClientTheme

@Composable
fun SearchInputBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search username...",
    onClearClick: () -> Unit = { onQueryChange("") },
) {
    AppTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        placeholder = placeholder,
        singleLine = true,
        trailingIcon = Icons.Default.Close,
        onClickTrailingIcon = {
            onClearClick()
        },
    )
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun SearchInputBarLightPreview() {
    var text by remember { mutableStateOf("") }
    GithubUserClientTheme {
        SearchInputBar(
            query = text,
            onQueryChange = { text = it },
        )
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun SearchInputBarDarkPreview() {
    var text by remember { mutableStateOf("") }
    GithubUserClientTheme(darkTheme = true) {
        SearchInputBar(
            query = text,
            onQueryChange = { text = it },
        )
    }
}
