package com.example.sensorluzfuncional_234963

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var rootView: RelativeLayout
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView // Mover la inicialización aquí

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootView = findViewById(R.id.rootView)
        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.imageView)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lightValue = event.values[0]
            when {
                lightValue < 20f -> { // Muy oscuro
                    rootView.setBackgroundColor(Color.BLACK)
                    textView.setTextColor(Color.WHITE)
                    textView.text = "Felicidades, tienes un Lycanroc nocturno!"
                    imageView.setImageResource(R.drawable.pokemon_noche)
                }
                lightValue < 100f -> { // Poca luz
                    val orangeColor = Color.rgb(255, 165, 0)
                    rootView.setBackgroundColor(orangeColor)
                    textView.setTextColor(Color.BLACK)
                    textView.text = "Felicidades, tienes un Lycanroc mediodia!"
                    imageView.setImageResource(R.drawable.pokemon_mediodia)
                }
                else -> { // Mucha luz
                    rootView.setBackgroundColor(Color.WHITE)
                    textView.setTextColor(Color.BLACK)
                    textView.text = "Felicidades, tienes un Lycanroc diurno!"
                    imageView.setImageResource(R.drawable.pokemon_dia)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
