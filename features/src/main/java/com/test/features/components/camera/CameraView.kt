package com.test.features.components.camera

import android.net.Uri
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.test.core.R
import com.test.features.components.animate.CircleIconButton
import com.test.features.components.topbar.BackTopBar
import java.io.File

@Composable
fun CameraCaptureComponent(
    onImageCaptured: (Uri) -> Unit,
    onError: (Exception) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    val executor = remember { ContextCompat.getMainExecutor(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }

    var previewView: PreviewView? by remember { mutableStateOf(null) }
    var isFrontCamera by remember { mutableStateOf(false) }  // État pour la caméra actuelle

    val cameraSelector = if (isFrontCamera)
        CameraSelector.DEFAULT_FRONT_CAMERA
    else
        CameraSelector.DEFAULT_BACK_CAMERA

    fun startCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val previewUseCase = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView?.surfaceProvider)
            }

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, previewUseCase, imageCapture
            )
        }, executor)
    }

    AndroidView(
        factory = { ctx ->
            previewView = PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            previewView!!
        },
        modifier = modifier.fillMaxSize()
    ) { startCamera() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            // Bouton pour changer la caméra
            CircleIconButton(
                drawableIcon = R.drawable.baseline_crop_rotate_24,
                modifier = Modifier
                    .size(58.dp)
                    .align(Alignment.BottomStart),
                onClick = {
                    isFrontCamera = !isFrontCamera
                    startCamera()  // Relance la caméra avec la nouvelle caméra sélectionnée
                })

            // Bouton pour prendre la photo
            CircleIconButton(
                drawableIcon = R.drawable.baseline_add_a_photo_24,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    val photoFile = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                    imageCapture.takePicture(outputOptions, executor,
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                val savedUri = Uri.fromFile(photoFile)
                                onImageCaptured(savedUri)
                            }

                            override fun onError(exception: ImageCaptureException) {
                                onError(exception)
                            }
                        })
                })

            BackTopBar(
                modifier = Modifier.align(Alignment.TopCenter), iconColor = Color.White, onLeftIconClick = onBackClicked)
        }
    }
}
