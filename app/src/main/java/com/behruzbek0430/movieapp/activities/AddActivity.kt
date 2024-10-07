package com.behruzbek0430.movieapp.activities

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.behruzbek0430.movieapp.R
import com.behruzbek0430.movieapp.databinding.ActivityAddBinding
import com.behruzbek0430.movieapp.models.Movie
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }

    lateinit var firebaseStorage: FirebaseStorage
    lateinit var reference: StorageReference
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference1: DatabaseReference
    lateinit var dialog: AlertDialog
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference1 = firebaseDatabase.getReference("Kinolar")
        binding.btnAdd.isEnabled = false

        dialog = AlertDialog.Builder(this).create()
        dialog.setTitle("Kuting...")
        dialog.setMessage("Rasm yuklanmoqda...")

        val size = intent.getStringExtra("s")
        Log.d(TAG, "Nechchi: $size")
        val d = size?.toInt()
        val ab = d?.plus(1)
        binding.size.text = "Kino kodi: ${ab.toString()}"

        firebaseStorage = FirebaseStorage.getInstance()
        reference = firebaseStorage.getReference("myPhotos")

        binding.image.setOnClickListener {
            getImageContent.launch("image/*")
            dialog.show()
        }

        binding.btnAdd.setOnClickListener {
            if (binding.kinoName.text.isBlank() && binding.kinoDate.text.isBlank() && binding.kinoLanguage.text.isBlank() && binding.kinoNationale.text.isBlank()){
                binding.kinoName.error = "Kino nomini kirgizmadingiz"
                binding.kinoDate.error = "Kino yilini kirgizmadingiz"
                binding.kinoLanguage.error = "Kino tilini kirgizmadingiz"
                binding.kinoNationale.error = "Kino davlatini kirgizmadingiz"
            }else if (binding.kinoId.text.isNotBlank() && binding.kinoName.text.isNotBlank() && binding.kinoDate.text.isNotBlank() && binding.kinoLanguage.text.isNotBlank() && binding.kinoSilka.text.isNotBlank()){
                val kino = Movie(binding.kinoId.text.toString(),binding.kinoName.text.toString(), imageUrl , binding.kinoDate.text.toString(), binding.kinoDescription.text.toString(), binding.kinoLanguage.text.toString(), binding.kinoNationale.text.toString(), binding.kinoSilka.text.toString())
                reference1.child(reference1.push().key!!).setValue(kino)
                binding.kinoName.text.clear()
                binding.kinoId.text.clear()
                binding.kinoSilka.text.clear()
                binding.kinoDate.text.clear()
                binding.kinoLanguage.text.clear()
                binding.kinoNationale.text.clear()
                binding.kinoDescription.text.clear()
                binding.image.setImageResource(R.drawable.ic_add)
            }
        }




    }

    var imageUrl = ""
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            val task = reference.child(binding.kinoId.text.toString()).putFile(uri)

            task.addOnSuccessListener {
                if (it.task.isSuccessful) {
                    val downloadUrl = it.metadata?.reference?.downloadUrl
                    downloadUrl?.addOnSuccessListener { imageUri ->
                        imageUrl = imageUri.toString()
                    }
                }
                binding.image.setImageURI(uri)
                dialog.dismiss()
                binding.btnAdd.isEnabled = true

            }
            task.addOnFailureListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
}