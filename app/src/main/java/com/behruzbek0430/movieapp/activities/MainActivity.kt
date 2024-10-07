package com.behruzbek0430.movieapp.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.behruzbek0430.movieapp.R
import com.behruzbek0430.movieapp.SharedPraferance.MySharedPraferance
import com.behruzbek0430.movieapp.adapters.ImageAdapter
import com.behruzbek0430.movieapp.adapters.RvAdapter
import com.behruzbek0430.movieapp.databinding.ActivityMainBinding
import com.behruzbek0430.movieapp.models.Movie
import com.behruzbek0430.movieapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity(), RvAdapter.RvAction {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var list: ArrayList<Movie>
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var firebaseDatabase1: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    lateinit var reference: DatabaseReference
    lateinit var reference1: DatabaseReference
    private lateinit var imageAdapter: ImageAdapter
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    lateinit var mySharedPreferences: MySharedPraferance
    lateinit var list1: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase1 = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users")
        reference1 = firebaseDatabase1.getReference("Kinolar")
        mySharedPreferences = MySharedPraferance
        mySharedPreferences.init(this)




        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list1 = ArrayList()
                val shareList = MySharedPraferance.sharedList
                val children = snapshot.children
                for (child in children) {
                    val user = child.getValue(User::class.java)
                    for (share in shareList) {
                        if (share == user?.uid) {
                            list1.add(user!!)
                        }
                    }
                }
//                secondAdapter = SecondAdapter(this@MainActivity2, list)
//                binding.rv.adapter = secondAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "${error.message}", Toast.LENGTH_SHORT).show()
            }
        })


//        binding.lnrKey.setOnClickListener {
//            Toast.makeText(this, "Bosildi", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, AddActivity::class.java)
//            startActivity(intent)
//        }


        list = ArrayList()
        reference1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Movie>()
                val children = snapshot.children
                for (child in children) {
                    val text = child.getValue(Movie::class.java)
                    list.add(text as Movie)
                }
//                val a = list.size.toString()
                list.shuffle()
                binding.rv.adapter = RvAdapter(this@MainActivity, list)

//                binding.size.setOnClickListener {
//                    val intent = Intent(this@MainActivity, AddActivity::class.java)
//                    intent.putExtra("s", a)
//                    Log.d(TAG, "Jonat: ${a}")
//                    startActivity(intent)
//                }

                val list1 = ArrayList<Movie>()
                if (list.size >= 3) {
                    list1.add(Movie(list.last().id, list.last().name1, list.last().image1))
                    list1.add(
                        Movie(
                            list.last().id,
                            list.dropLast(1).last().name1,
                            list.dropLast(1).last().image1
                        )
                    )
                    list1.add(
                        Movie(
                            list.last().id,
                            list.dropLast(2).last().name1,
                            list.dropLast(2).last().image1
                        )
                    )
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            if (currentPage == list1.size) {
                                currentPage = 0
                            }
                            binding.viewPager.setCurrentItem(currentPage++, true)
                            handler.postDelayed(this, 5000)
                        }
                    }, 5000)



                    imageAdapter = ImageAdapter(object : ImageAdapter.RvGo {
                        override fun onClick(id: String) {
//                            val intent = Intent(this@MainActivity, KinoActivity::class.java)
//                            intent.putExtra("id", id)
//                            startActivity(intent)
                        }
                    }, list1)
                    binding.viewPager.adapter = imageAdapter


                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "${error.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onClick(id: String) {
        val intent = Intent(this, KinoActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

}