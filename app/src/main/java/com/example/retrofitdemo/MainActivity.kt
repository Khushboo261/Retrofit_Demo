package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofitdemo.databinding.ActivityMainBinding
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //private lateinit var text_view : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //text_view = findViewById<TextView>(R.id.textView)
        val retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)
        val responseLiveData : LiveData<Response<Album>> = liveData {
            val response = retService.getAlbums()
            emit(response)
        }
        responseLiveData.observe(this, Observer{
            val albumsList = it.body()?.listIterator()
            if(albumsList!=null){
                while(albumsList.hasNext()){
                    val albumsItem:AlbumItem = albumsList.next()
                    Log.i("MYTAG",albumsItem.title)
                    val result:String = " "+"Album Title : ${albumsItem.title}"+"\n"+
                            " "+"Album id : ${albumsItem.id}"+"\n"+
                            " "+"User id : ${albumsItem.userId}"+"\n\n\n"
                    binding.textView.append(result)
                }
            }
        })
    }
}