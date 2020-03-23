package com.dynamicdudes.kotlinretrofit
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://type.fit")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.fetchAllUser().enqueue(object : Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                val dialogBuilder = AlertDialog.Builder(this@MainActivity)
                dialogBuilder.setMessage("Have you turned on the internet connection ?")
                    .setCancelable(false)
                    .setPositiveButton("Turn on", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                        Toast.makeText(applicationContext,"Please turn on Internet and reopen the application.",Toast.LENGTH_LONG).show()
                    })
                val alert = dialogBuilder.create()
                alert.setTitle("No Internet Connection")
                alert.show()
            }
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                showData(response.body()!!)
            }
        })
    }
    private fun showData(users: List<User>) {
        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UsersAdapter(users)
        }
    }
}
