package com.ashart.mov.checkout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ashart.mov.R
import com.ashart.mov.home.model.Film
import com.bagicode.bwamov.checkout.model.Checkout
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() {

    var statusA3: Boolean = false
    var statusA4: Boolean = false
    var total: Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        val data = intent.getParcelableExtra<Film>("data")

        tv_kursi.text = data.judul

        a3.setOnClickListener {
            if (statusA3) {
                a3.setImageResource(R.drawable.ic_rectangle_empty)
                statusA3 = false
                total -= 1
                belitiket(total)
            } else {
                a3.setImageResource(R.drawable.ic_rectangle_selected)
                statusA3 = true
                total += 1
                belitket(total)

                val data = Checkout("A3", "70000")
                dataList.add(data)
            }
        }
    }
}
