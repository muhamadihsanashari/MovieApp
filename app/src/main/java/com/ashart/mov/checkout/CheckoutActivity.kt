package com.ashart.mov.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashart.mov.R
import com.ashart.mov.checkout.adapter.CheckoutAdapter
import com.ashart.mov.home.model.Film
import com.ashart.mov.home.tiket.TiketActivity
import com.ashart.mov.utils.Preferences
import com.bagicode.bwamov.checkout.model.Checkout
import kotlinx.android.synthetic.main.activity_checkout.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total: Int = 0

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>
        val data = intent.getParcelableExtra<Film>("datas")

        for (a in dataList.indices){
            total += dataList[a].harga!!.toInt()
        }

        dataList.add(Checkout("Total Harus Dibayar", total.toString()))

        btn_tiket.setOnClickListener {
            val intent = Intent(this@CheckoutActivity,
                    CheckoutSuccessActivity::class.java)
            startActivity(intent)

            showNotif(data)
        }

        rc_checkout.layoutManager = LinearLayoutManager(this)
        rc_checkout.adapter = CheckoutAdapter(dataList) {

        }

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        tv_saldo.setText(formatRupiah.format(preferences.getValues("saldo")!!.toDouble()))
    }

    private fun showNotif(datas: Film) {
        val NOTIFICATION_CHANNEL_ID = "channer_bwa_notif"
        val context = this.applicationContext
        var notificaionManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelName = "BWAMOV Notif Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificaionManager.createNotificationChannel(mChannel)
        }

//        val mIntent = Intent(this, CheckoutActivity::class.java)
//        val bundel = Bundle()
//        bundel.putString("id", "id_film")
//        mIntent.putExtras(bundel)

        val mIntent = Intent(this, TiketActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("data", datas)
        mIntent.putExtras(bundle)

        val pendingIntent =
                PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                        BitmapFactory.decodeResource(
                                this.resources,
                                R.mipmap.ic_launcher
                        )
                )
                .setTicker("notif bwa starting")
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000,1000,1000,1000))
                .setLights(Color.RED, 3000,3000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("Transaksi Berhasil!")
                .setContentText("Tiket "+datas.judul+" berhasil kamu dapatkan. Enjoy the movie!")

        notificaionManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificaionManager.notify(115, builder.build())
    }
}
