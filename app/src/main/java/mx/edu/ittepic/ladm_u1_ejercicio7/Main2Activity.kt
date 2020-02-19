package mx.edu.ittepic.ladm_u1_ejercicio7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {
    var posicion = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var dataEnvio = intent.extras

        editText.setText(dataEnvio.getString("nombre"))
        editText2.setText(dataEnvio.getString("domicilio"))
        editText3.setText(dataEnvio.getString("telefono"))

        posicion = dataEnvio.getInt("posicion")


        button.setOnClickListener {
            var dataRegreso = Intent()

            dataRegreso.putExtra("nombre",editText.text.toString())
            dataRegreso.putExtra("domicilio", editText2.text.toString())
            dataRegreso.putExtra("telefono",editText3.text.toString())
            dataRegreso.putExtra("posicion",posicion)

            setResult(7, dataRegreso)
            finish()
        }

        button2.setOnClickListener {
            setResult(5)
            finish()
        }
    }
}
