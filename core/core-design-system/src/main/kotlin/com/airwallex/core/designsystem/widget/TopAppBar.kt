package com.airwallex.core.designsystem.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.theme.Dimens.Global

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes navIconRes: Int,
    title: String = "",
    headline: String = "", // headline that shows under the top bar
    onNavIconClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TopAppBar(
            title = {
                if (title.isNotBlank()) {
                    Text(text = title, style = MaterialTheme.typography.h6)
                }
            },
            navigationIcon = {
                androidx.compose.material.IconButton(onClick = onNavIconClicked) {
                    Icon(painterResource(id = navIconRes), contentDescription = "Nav Icon")
                }
            },
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp
        )
        if (headline.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = Global.MediumPadding.get(),
                        bottom = Global.MediumLargePadding.get()
                    ),
                text = headline,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    navIconIV: ImageVector = Icons.Filled.ArrowBack,
    title: String = "", // title that embedded inside the top bar
    headline: String = "", // headline that shows under the top bar
    onNavIconClicked: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    appBarBackgroundColor: Color = MaterialTheme.colors.background
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TopAppBar(
            title = {
                if (title.isNotBlank()) {
                    Text(text = title, style = MaterialTheme.typography.h6)
                }
            },
            navigationIcon = {
                androidx.compose.material.IconButton(onClick = onNavIconClicked) {
                    Icon(imageVector = navIconIV, contentDescription = "Nav Icon")
                }
            },
            backgroundColor = appBarBackgroundColor,
            contentColor = contentColorFor(appBarBackgroundColor),
            elevation = 0.dp,
            actions = actions
        )
        if (headline.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = Global.MediumPadding.get(),
                        bottom = Global.MediumLargePadding.get()
                    ),
                text = headline,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

val AppBarHeight = 56.dp
