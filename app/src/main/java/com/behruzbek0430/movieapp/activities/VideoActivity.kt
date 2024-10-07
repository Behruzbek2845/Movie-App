package com.behruzbek0430.movieapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.behruzbek0430.movieapp.R
import com.behruzbek0430.movieapp.databinding.ActivityVideoBinding
import com.behruzbek0430.movieapp.models.Movie
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityVideoBinding.inflate(layoutInflater) }
    private lateinit var youtubePlayerView: YouTubePlayerView
    lateinit var reference1: DatabaseReference
    lateinit var firebaseDatabase1: FirebaseDatabase
    lateinit var silka :String
    private var YouTubePlayer : YouTubePlayer? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseDatabase1 = FirebaseDatabase.getInstance()
        reference1 = firebaseDatabase1.getReference("Kinolar")

        var id = intent.getStringExtra("id")

        YouTubePlayer?.toggleFullscreen()
        reference1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Movie>()
                val children = snapshot.children
                for (child in children){
                    val text = child.getValue(Movie::class.java)
                    if (id == text?.id){
                        list.add(text as Movie)
                    }
                }
                silka = list[0].silka.toString()


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@VideoActivity, "${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        youtubePlayerView = findViewById(R.id.youtubePlayerView)

//        binding.btnFull.setOnClickListener {
//            youtubePlayerView.addFullscreenListener(object : FullscreenListener{
//                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
//                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // Ekran yo'nalishini gorizontal qilish
//
//                    // To'liq ekran rejimida status bar va action bar ni yashirish
//                    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//                    supportActionBar?.hide()
//
//                    // FullscreenView ni faqat to'liq ekran uchun belgilangan parent layout ga qo'shish
//                    val parentLayout = findViewById<ViewGroup>(R.id.fullscreen_layout)
//                    parentLayout.addView(fullscreenView)
//                }
//
//                override fun onExitFullscreen() {
//                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED // Ekran yo'nalishini portret holatiga qaytarish
//
//                    // Status bar va action bar ni qayta ko'rsatish
//                    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//                    supportActionBar?.show()
//
//                    // FullscreenView ni remove qilish
//                    val parentLayout = findViewById<ViewGroup>(R.id.fullscreen_layout)
//                    parentLayout.removeAllViews()
//                }
//            })
//        }



        youtubePlayerView.addYouTubePlayerListener(object : YouTubePlayerListener {
            override fun onApiChange(youTubePlayer: YouTubePlayer) {}

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {}

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {}

            override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: PlayerConstants.PlaybackQuality) {}

            override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: PlayerConstants.PlaybackRate) {}

            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(silka, 0f)
            }

            override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {}

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {}

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {}

            override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {}
        })

    }
}