package com.dynamicdudes.kotlinretrofit
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import stream.customalert.CustomAlertDialogue
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://type.fit")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiService::class.java)
        api.fetchAllUser().enqueue(object : Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                val alert =
                    CustomAlertDialogue.Builder(this@MainActivity)
                        .setStyle(CustomAlertDialogue.Style.DIALOGUE)
                        .setTitle("No Internet Connection")
                        .setMessage("Please check you internet connection. If it is not turned on please turn it on. ")
                        .setNegativeText("OK")
                        .setNegativeColor(R.color.colorPrimaryDark)
                        .setOnNegativeClicked { view, dialog -> dialog.dismiss() }
                        .setDecorView(window.decorView)
                        .build()
                alert.show()
                call.clone().enqueue(this)
            }
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                showData(response.body()!!)
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.example_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.about_us -> {
                val alert = CustomAlertDialogue.Builder(this@MainActivity)
                .setStyle(CustomAlertDialogue.Style.DIALOGUE)
                .setTitle("This App Was Developed By")
                .setMessage("- R.Malini \n- R.Vishweshwaran\n- K. Magesh Babu \n(Founder of Dynamic Dudes)  ")
                .setNegativeText("OK")
                .setNegativeColor(R.color.redbar)
                .setOnNegativeClicked { view, dialog -> dialog.dismiss() }
                .setDecorView(window.decorView)
                .build()
            alert.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showData(users: List<User>) {
        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UsersAdapter(users)
        }
    }
}
