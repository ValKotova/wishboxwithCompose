package com.valkotova.wishboxwithcompose.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishShortInfo
import com.valkotova.wishboxwithcompose.ui.main.getSmallUri
import com.valkotova.wishboxwithcompose.ui.main.theme.ColorTextGeneral

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WishHolderVertical(
    wish: WishShortInfo,
    onClick : (Int) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            //.background(color = MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick.invoke(wish.id)
            }
    ) {

        Column(
        ) {
            Box(
                modifier = Modifier.height(160.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                wish.preview?.fileInfo?.let { imageInfo ->
                    GlideImage(
                        model = imageInfo.getSmallUri(),
                        contentDescription = "Wish image",
                        modifier = Modifier
                            .height(160.dp)
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        val circularProgressDrawable = CircularProgressDrawable(context)
                        circularProgressDrawable.strokeWidth = 5f
                        circularProgressDrawable.centerRadius = 30f
                        circularProgressDrawable.start()
                        it.thumbnail()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(circularProgressDrawable)
                            .centerCrop()
                            .load(imageInfo.getSmallUri())
                    }
                } ?: run {
                    Image(
                        modifier = Modifier
                            .size(160.dp)
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.placeholder_image),
                        contentDescription = wish.name,
                    )
                }
                Row() {
                    if(wish.isBlocked)
                        Image(
                            modifier = Modifier.padding(2.dp),
                            painter = painterResource(id = R.drawable.ic_lock_circled),
                            contentDescription = "Blocked"
                        )
                    if(wish.isBooked)
                        Image(
                            modifier = Modifier.padding(2.dp),
                            painter = painterResource(id = R.drawable.ic_booked_circled),
                            contentDescription = "Booked"
                        )
                    if(wish.favorite)
                        Image(
                            modifier = Modifier.padding(2.dp),
                            painter = painterResource(id = R.drawable.ic_star_circled),
                            contentDescription = "Star"
                        )
                }
            }
            Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                Text(
                    modifier = Modifier.padding(6.dp).fillMaxSize(),
                    style = MaterialTheme.typography.bodyMedium,
                    text = wish.name,
                    color = ColorTextGeneral,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2)
            }

        }
    }
}