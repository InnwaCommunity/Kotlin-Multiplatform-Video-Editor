package com.manage.composesamples.ui.components

import android.content.res.Configuration
import android.text.Highlights
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manage.composesamples.R
import com.manage.composesamples.model.CollectionType
import com.manage.composesamples.model.Snack
import com.manage.composesamples.model.SnackCollection
import com.manage.composesamples.model.snacks
import com.manage.composesamples.ui.theme.JetsnackTheme
import com.manage.composesamples.ui.utils.mirroringIcon

private val HighlightCardWidth = 170.dp
private val HighlightCardPadding = 16.dp

private val Density.cardWidthWithPaddingPx
    get() = (HighlightCardWidth + HighlightCardPadding).toPx()

@Composable
fun SnackCollection(
    snackCollection: SnackCollection,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    index: Int = 0,
    highlight: Boolean = true
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = snackCollection.name,
                style = MaterialTheme.typography.h6,
                color = JetsnackTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = mirroringIcon(
                        ltrIcon = Icons.AutoMirrored.Outlined.ArrowForward,
                        rtlIcon = Icons.AutoMirrored.Outlined.ArrowBack
                    ), contentDescription = null,
                    tint = JetsnackTheme.colors.brand
                )
            }
        }
        if (highlight && snackCollection.type == CollectionType.Highlight) {
            HighlightedSnacks(
                index = index,
                snacks = snackCollection.snacks,
                onSnackClick = onSnackClick
            )
        } else {

        }
    }
}

@Composable
private fun HighlightedSnacks(
    index: Int,
    snacks: List<Snack>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val rowState = rememberLazyListState()
    val cardWidthWithPaddingPx = with(LocalDensity.current) { cardWidthWithPaddingPx }
    val scrollProvider = {
        val offsetFromStart = cardWidthWithPaddingPx * rowState.firstVisibleItemIndex
        offsetFromStart + rowState.firstVisibleItemScrollOffset
    }

    val gradient = when ((index / 2) % 2) {
        0 -> JetsnackTheme.colors.gradient6_1
        else -> JetsnackTheme.colors.gradient6_2
    }

    LazyRow(
        state = rowState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp)
    ) {
        itemsIndexed(snacks) { index, snack ->
            HighlightSnackItem(
                snack = snack,
                onSnackClick = onSnackClick,
                index = index,
                gradient = gradient,
                scrollProvider = scrollProvider
            )
        }
    }
}

@Composable
private fun HighlightSnackItem(
    snack: Snack,
    onSnackClick: (Long) -> Unit,
    index: Int,
    gradient: List<Color>,
    scrollProvider: () -> Float,
    modifier: Modifier = Modifier
) {
    JetsnackCard(
        modifier = modifier
            .size(
                width = HighlightCardWidth,
                height = 250.dp
            )
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = { onSnackClick(snack.id) })
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .offsetGradientBackground(
                            colors = gradient,
                            width = {
                                // The Cards show a gradient which spans 6 cards and scrolls with parallax.
                                6 * cardWidthWithPaddingPx
                            },
                            offset = {
                                val left = index * cardWidthWithPaddingPx
                                val gradientOffset = left - (scrollProvider() / 3f)
                                gradientOffset
                            }
                        )
                )
                SnackImage(
                    imageUrl = snack.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.BottomCenter)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = snack.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h6,
                color = JetsnackTheme.colors.textSecondary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = snack.tagline,
                style = MaterialTheme.typography.body1,
                color = JetsnackTheme.colors.textHelp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun SnackImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    JetsnackSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(), contentDescription = contentDescription,
            placeholder = painterResource(R.drawable.placeholder),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun SnackCardPreview() {
    JetsnackTheme {
        val snack = snacks.first()
        HighlightSnackItem(
            snack = snack,
            onSnackClick = { },
            index = 0,
            gradient = JetsnackTheme.colors.gradient6_1,
            scrollProvider = { 0f })
    }
}