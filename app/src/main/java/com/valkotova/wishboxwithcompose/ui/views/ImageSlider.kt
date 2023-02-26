

package com.valkotova.wishboxwithcompose.ui.views

import android.icu.number.Scale
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.domain.model.common.FileInfo
import com.valkotova.wishboxwithcompose.ui.main.getBigUri
import com.valkotova.wishboxwithcompose.ui.main.getSmallUri

@ExperimentalGlideComposeApi
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalFoundationApi
@Composable
fun ImageSlider(list : List<FileInfo>) {
    val state = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    )
    val imageUrl =
        remember { mutableStateOf(Uri.parse("")) }
    val context = LocalContext.current
    if(list.isEmpty()){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            painter = painterResource(id = R.drawable.placeholder_image),
            contentDescription = "Wish image",
        )
    }
    else {
        HorizontalPager(
            state = state,
            pageCount = list.size,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) { page ->
            imageUrl.value = list[page].fileInfo.getBigUri()

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomCenter) {
                    GlideImage(
                        model = imageUrl.value,
                        contentDescription = "Wish image",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        val circularProgressDrawable = CircularProgressDrawable(context)
                        circularProgressDrawable.strokeWidth = 10f
                        circularProgressDrawable.centerRadius = 160f
                        circularProgressDrawable.start()
                        it.thumbnail()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(circularProgressDrawable)
                            .fitCenter()
                            .load(imageUrl.value)
                    }
                }
            }
        }
    }
}