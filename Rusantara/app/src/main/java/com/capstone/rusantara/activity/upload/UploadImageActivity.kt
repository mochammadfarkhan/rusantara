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
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.capstone.rusantara.activity.camera.CameraActivity
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
            startCameraX()
        }
        binding.galleryButton.setOnClickListener {
            startGallery()
        }
        binding.uploadButton.setOnClickListener {
            uploadImage()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )

            binding.previewImageView.setImageBitmap(result)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
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
        binding.progressBar.visibility = View.VISIBLE
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
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
                        if (response.isSuccessful) {
                            binding.progressBar.visibility = View.GONE
                            val responseBody = response.body()
                            if (responseBody != null) {
                                val intent = Intent(applicationContext, DetailActivity::class.java)
                                intent.putExtra(EXTRA_DATA, responseBody)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ImageData>, t: Throwable) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@UploadImageActivity,
                            "Time out\nPlease check your internet connection and try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
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
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}