package com.example.ex3_draw_sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var mPosition: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // valitaan sensorityypiksi kiihtyvyysanturi
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mPosition = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    // SensorEventListenerin metodi sensorin arvon muuttuessa
    override fun onSensorChanged(p0: SensorEvent?) {
        val upDown = p0!!.values[0]  // otetaan talteen sensorista tuleva tieto sekä x- että y-suunnassa
        val sides = p0!!.values[1]
        val tv = findViewById<TextView>(R.id.textView)
        tv.text = upDown.toString() + "  /  " + sides.toString()  // kirjoitetaan sensoritieto näytölle
        val piirto = findViewById<MyView>(R.id.myView)
        piirto.setXY(upDown, sides)  // Lähetetään sensoritieto myView-luokkaan
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // Ei käytetä tätä metodia
    }

    // Kerätään sensorilta mittausdataa kun käyttäjä käyttää sovellusta
    override fun onResume() {
        super.onResume()
        mPosition?.also { pos ->
            sensorManager.registerListener(this, pos, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // mittausdatan kerääminen sensorilta keskeytyy kun käyttäjä ottaa toisen sovelluksen käyttöön esim.
    // tulee puhelu
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}