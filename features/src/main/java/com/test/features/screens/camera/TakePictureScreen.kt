package com.test.features.screens.camera

import android.Manifest
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.test.core.ContentCardType
import com.test.features.components.animate.AnimatedCircleIconButtonsRow
import com.test.features.components.camera.CameraCaptureComponent
import com.test.features.components.topbar.BackTopBar
import com.test.features.navigation.FAB_EXPLODE_BOUNDS_KEY


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalPermissionsApi::class)
@Composable
fun SharedTransitionScope.TakePictureScreen(
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .sharedBounds(
                sharedContentState = rememberSharedContentState(
                    key = FAB_EXPLODE_BOUNDS_KEY
                ), animatedVisibilityScope = animatedVisibilityScope
            ), contentAlignment = Alignment.Center
    ) {

        PermissionRequired(permissionState = cameraPermissionState, permissionNotGrantedContent = {
            LaunchedEffect(Unit) {
                cameraPermissionState.launchPermissionRequest()
            }
        }, permissionNotAvailableContent = {
            Column {
                Toast.makeText(context, "Permission denied.", Toast.LENGTH_LONG).show()
            }
        }, content = {
            var capturedImageUri by remember { mutableStateOf<Uri?>(null) }

            if (capturedImageUri == null) {
                CameraCaptureComponent(onImageCaptured = { uri ->
                    capturedImageUri = uri
                }, onError = { exception ->
                    Log.e("CameraScreen", "Capture error: ${exception.message}")
                }, onBackClicked = {
                    navController.popBackStack()
                })
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(capturedImageUri),
                        contentDescription = "Captured Image",
                        modifier = Modifier
                            .height(ContentCardType.POST.cardHeight.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .align(Alignment.Center)
                    )

                    Spacer(Modifier.height(16.dp))
                    AnimatedCircleIconButtonsRow(modifier = Modifier.align(Alignment.BottomCenter),
                        onCheckClick = {
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                "capturedImageUri",
                                capturedImageUri.toString()
                            )
                            navController.popBackStack()
                        },
                        onCloseClick = {
                            capturedImageUri = null
                        })

                    BackTopBar(
                        modifier = Modifier.align(Alignment.TopCenter), iconColor = Color.White
                    ) {
                        navController.popBackStack()
                    }
                }
            }
        })
    }

}