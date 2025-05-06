package com.omooooori.design.component.material3

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.omooooori.design.theme.GithubUserClientTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onClickTrailingIcon: (() -> Unit)? = null,
    isError: Boolean = false,
    singleLine: Boolean = true,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = singleLine,
        isError = isError,
        label = label?.let { { AppText(it) } },
        placeholder =
            placeholder?.let {
                {
                    AppText(it)
                }
            },
        leadingIcon =
            leadingIcon?.let {
                {
                    AppIcon(it, null)
                }
            },
        trailingIcon =
            trailingIcon?.let {
                {
                    AppIcon(
                        it,
                        null,
                        modifier =
                            Modifier.clickable {
                                onClickTrailingIcon?.invoke()
                            },
                    )
                }
            },
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                // error case color
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error,
                errorTrailingIconColor = MaterialTheme.colorScheme.error,
            ),
    )
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppTextFieldLightPreview() {
    GithubUserClientTheme(darkTheme = false) {
        AppTextField(
            value = "",
            onValueChange = {},
            placeholder = "Placeholder",
            leadingIcon = Icons.Default.Create,
            trailingIcon = Icons.Default.Close,
        )
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppTextFieldDarkPreview() {
    GithubUserClientTheme(darkTheme = true) {
        AppTextField(
            value = "",
            onValueChange = {},
            placeholder = "Placeholder",
            leadingIcon = Icons.Default.Create,
            trailingIcon = Icons.Default.Close,
        )
    }
}

@Preview(
    name = "Light Theme",
    showBackground = true,
    apiLevel = 33,
)
@Composable
fun AppTextFieldErrorLightPreview() {
    GithubUserClientTheme(darkTheme = false) {
        AppTextField(
            value = "",
            onValueChange = {},
            placeholder = "Placeholder",
            leadingIcon = Icons.Default.Create,
            trailingIcon = Icons.Default.Close,
            isError = true,
        )
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true,
    showSystemUi = true,
    apiLevel = 33,
)
@Composable
fun AppTextFieldErrorDarkPreview() {
    GithubUserClientTheme(darkTheme = true) {
        AppTextField(
            value = "",
            onValueChange = {},
            placeholder = "Placeholder",
            leadingIcon = Icons.Default.Create,
            trailingIcon = Icons.Default.Close,
            isError = true,
        )
    }
}
