package com.capstone.rusantara.activity.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.capstone.rusantara.activity.detail.DetailActivity
import com.capstone.rusantara.activity.detail.DetailActivity.Companion.EXTRA_DATA
import com.capstone.rusantara.api.ApiConfig
import com.capstone.rusantara.databinding.ActivityUploadImageBinding
import com.capstone.rusantara.models.ImageData
import com.capstone.rusantara.utils.createCustomTempFile
import com.capstone.rusantara.utils.reduceFileImage
import com.capstone.rusantara.utils.rotateBitmap
import com.capstone.rusantara.utils.uriToFile
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadImageBinding
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        auth = FirebaseAuth.getInstance()

        binding.cameraButton.setOnClickListener {
            startTakePhoto()
        }
        binding.galleryButton.setOnClickListener {
            startGallery()
        }
        binding.uploadButton.setOnClickListener {
            uploadImage()
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@UploadImageActivity,
                "com.capstone.rusantara",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                true
            )
            binding.previewImageView.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@UploadImageActivity)

            getFile = myFile

            binding.previewImageView.setImageURI(selectedImg)
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            val firebaseUser = auth.currentUser
            firebaseUser?.getIdToken(false)?.addOnSuccessListener { result ->
                val idToken = result.token

                val service = ApiConfig.getApiService().predict(imageMultipart, "Bearer $idToken")
                service.enqueue(object : Callback<ImageData> {
                    override fun onResponse(
                        call: Call<ImageData>,
                        response: Response<ImageData>
                    ) {
                        if (!response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                val intent = Intent(applicationContext, DetailActivity::class.java)
                                intent.putExtra(EXTRA_DATA, responseBody)
                                startActivity(intent)
                            }
                        }
//                        else {
//                            Toast.makeText(
//                                this@UploadImageActivity,
//                                response.message(),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
                    }

                    override fun onFailure(call: Call<ImageData>, t: Throwable) {
                        Toast.makeText(
                            this@UploadImageActivity,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

//            val service = ApiConfig.getApiService().predict(imageMultipart,"Bearer ${idToken}")
//            service.enqueue(object : Callback<ImageData> {
//                override fun onResponse(
//                    call: Call<ImageData>,
//                    response: Response<ImageData>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//                        if (responseBody != null) {
//                            val intent = Intent(applicationContext, DetailActivity::class.java)
//                            startActivity(intent)
//                        }
//                    } else {
//                        Toast.makeText(this@UploadImageActivity, response.message(), Toast.LENGTH_SHORT).show()
//                    }
//                }
//                override fun onFailure(call: Call<ImageData>, t: Throwable) {
//                    Toast.makeText(this@UploadImageActivity, "Retrofit instance failed", Toast.LENGTH_SHORT).show()
//                }
//            })
        }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Access not granted.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}