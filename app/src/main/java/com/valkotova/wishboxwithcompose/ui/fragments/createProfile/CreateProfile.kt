package com.valkotova.wishboxwithcompose.ui.fragments.createProfile

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.canhub.cropper.CropImage.CancelledResult.bitmap
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.ui.fragments.browseWish.BrowseWishViewModel
import com.valkotova.wishboxwithcompose.ui.main.AppState
import com.valkotova.wishboxwithcompose.ui.main.StatesManagement
import com.valkotova.wishboxwithcompose.ui.views.DatePickerDialog
import com.valkotova.wishboxwithcompose.ui.views.GenderSelector
import com.valkotova.wishboxwithcompose.ui.views.Header
import com.valkotova.wishboxwithcompose.ui.views.WhiteEditText
import com.valkotova.wishboxwithcompose.ui.views.buttons.BlueButton
import kotlinx.coroutines.launch
import java.io.File
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Formatter
import java.util.TimeZone


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun CreateProfile(appState : AppState,
               viewModel : CreateProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var uriPhoto: Uri? = null
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scrollState = rememberScrollState()
    val state = viewModel.state.collectAsState()
    val image = viewModel.image.collectAsState()
    val birthday = viewModel.birthday.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    val createPhoto = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            uriPhoto?.let  { uri ->
                viewModel.launchCropper(uri)
            }
        }
    }
    val pickImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.launchCropper(it)
        }
    }

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the cropped image
            viewModel.setImage(
                result.uriContent?.toString()?:""
            )
        } else {
            val exception = result.error
        }
    }

    val requestPermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (result[Manifest.permission.CAMERA] != null) {
                if (result.all { it.value }) {
                    val file = File(context.filesDir, "picFromCamera.jpg")
                    if (file.exists())
                        file.delete()
                    file.createNewFile()
                    uriPhoto = FileProvider.getUriForFile(
                        context,
                        context.packageName + ".fileProvider",
                        file
                    )
                    createPhoto.launch(uriPhoto)
                }
            } else if(result[Manifest.permission.READ_EXTERNAL_STORAGE] != null
                || result[Manifest.permission.READ_MEDIA_IMAGES] != null){
                if (result.all { it.value }) {
                    pickImage.launch("image/*")
                }
            }
        }

    StatesManagement(appState = appState, state = state){
        when(state.value){
            is CreateProfileEvents.LaunchCropping -> {
                val cropOptions = CropImageContractOptions((state.value as CreateProfileEvents.LaunchCropping).uri, CropImageOptions())
                imageCropLauncher.launch(cropOptions)
                viewModel.clearState()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
        ,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Header(title = stringResource(id = R.string.create_profile), hasBack = false)
        ModalBottomSheetLayout(
            sheetContent = {
                DatePickerDialog(birthday.value) {
                    viewModel.setBirthday(it)
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
                ,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                //Avatar
                Box(contentAlignment = Alignment.BottomEnd) {
                    if (image.value.isEmpty()) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            painter = painterResource(id = R.drawable.placeholder_image),
                            contentDescription = "Wish image",
                        )
                    } else {
                        GlideImage(
                            model = image.value,
                            contentDescription = "Profile image",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
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
                                .load(image.value)
                        }
                    }
                    Button(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(10.dp),
                        onClick = {
                            requestPermission.launch(arrayOf(Manifest.permission.CAMERA))
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            tint = MaterialTheme.colorScheme.background,
                            contentDescription = "Add image",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                //Nickname
                WhiteEditText(
                    state = viewModel.nickname.collectAsState(),
                    onValueChange = {
                        viewModel.onNickNameChange(it)
                    },
                    hint = stringResource(id = R.string.hint_nickname),
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 15.dp)
                        .fillMaxWidth()
                )
                //Name
                WhiteEditText(
                    state = viewModel.name.collectAsState(), onValueChange = {
                        viewModel.onNameChange(it)
                    },
                    hint = stringResource(id = R.string.hint_fio),
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 15.dp)
                        .fillMaxWidth()
                )
                //Birthday
                Text(
                    text = formatter.format(
                        LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(birthday.value),
                            TimeZone.getDefault().toZoneId()
                        )
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 15.dp)
                        .background(
                            MaterialTheme.colorScheme.inversePrimary,
                            RoundedCornerShape(15.dp)
                        )
                        .padding(22.dp, 14.dp)
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                bottomSheetState.show()
                            }
                        }
                )
                //Gender
                val gender = viewModel.gender.collectAsState()
                GenderSelector(curGender = gender.value) { viewModel.setGender(it) }
                BlueButton(
                    text = stringResource(id = R.string.save_changes),
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                    onClick = {
                        //appState.navigateToBottomBarRoute(HomeSections.LISTS.route)
                        viewModel.saveProfile()
                    }
                )
            }
        }
    }
}