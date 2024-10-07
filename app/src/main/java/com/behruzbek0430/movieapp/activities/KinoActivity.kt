package com.behruzbek0430.movieapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.behruzbek0430.movieapp.databinding.ActivityKinoBinding
import com.behruzbek0430.movieapp.models.Movie
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class KinoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityKinoBinding.inflate(layoutInflater) }
    lateinit var reference1: DatabaseReference
    lateinit var firebaseDatabase1: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseDatabase1 = FirebaseDatabase.getInstance()
        reference1 = firebaseDatabase1.getReference("Kinolar")

        var id = intent.getStringExtra("id")

        binding.btnWatch.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        reference1.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Movie>()
                val children = snapshot.children
                for (child in children){
                    val text = child.getValue(Movie::class.java)
                    if (id == text?.id){
                        list.add(text as Movie)
                    }
                }
                binding.movieName.text = "${list[0].name1}"
                binding.movieDate.text = "Yili: ${list[0].date}"
                binding.movieLangue.text = "Tili: ${list[0].language}"
                binding.movieNationale.text = "Davlati: ${list[0].national}"
                binding.movieDescription.text = list[0].description
                Picasso.get().load(list[0].image1).into(binding.movieImage2)
                Picasso.get().load(list[0].image1).into(binding.movieImage1)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@KinoActivity, "${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        
//        binding.movieImage2.setOnClickListener {
//            checkAndRequestPermissions()
//        }

    }
//
//
//    private fun saveImageToFile() {
//        val imageView: ImageView = findViewById(R.id.movie_image2)
//        val drawable = imageView.drawable as BitmapDrawable
//        val bitmap = drawable.bitmap
//
//        // Faylni saqlash uchun kod
//        saveBitmapToFile(bitmap)
//    }
//    private fun createDirectoriesAndSaveImage(bitmap: Bitmap) {
//        // Umumiy xotirada papkalarni yaratish
//        val movieDir = File(Environment.getExternalStorageDirectory(), "Movie")
//        if (!movieDir.exists()) {
//            movieDir.mkdirs()
//        }
//
//        val imagesDir = File(movieDir, "Images")
//        if (!imagesDir.exists()) {
//            imagesDir.mkdirs()
//        }
//
//        val fileName = "image_${System.currentTimeMillis()}.png"
//        val file = File(imagesDir, fileName)
//
//        try {
//            FileOutputStream(file).use { fos ->
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
//                fos.flush()
//                Log.d("SaveImage", "Image saved to ${file.absolutePath}")
//                Toast.makeText(this, "Image saved successfully!", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(this, "Failed to save image.", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun saveBitmapToFile(bitmap: Bitmap) {
//        createDirectoriesAndSaveImage(bitmap) // Papkalarni yaratish
//
//        val imagesDir = File(getExternalFilesDir(null), "Movie/Images")
//        val fileName = "image_${System.currentTimeMillis()}.png"
//        val file = File(imagesDir, fileName)
//
//        try {
//            FileOutputStream(file).use { fos ->
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
//                fos.flush()
//                Log.d("SaveImage", "Image saved to ${file.absolutePath}")
//                Toast.makeText(this, "Image saved successfully!", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(this, "Failed to save image.", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun checkAndRequestPermissions() {
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
//        } else {
//            // Ruxsat berilgan, rasmni saqlashni boshlang
//            saveImageToFile()
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            // Ruxsat berilgan, rasmni saqlashni boshlang
//            saveImageToFile()
//        } else {
//            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//        }
//    }
}