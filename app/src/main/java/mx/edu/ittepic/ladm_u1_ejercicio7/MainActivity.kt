package mx.edu.ittepic.ladm_u1_ejercicio7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var vector = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertar.setOnClickListener {
            insertarEnVector()
        }

        mostrar.setOnClickListener {
            //mostrarDatos()
            mostrarDatosEnLista()
        }

        eliminar.setOnClickListener {
            eliminarCeldilla()
        }
    }

    private fun mostrarDatosEnLista() {
        var ultimoIndice = vector.size-1
        var arregloEstatico : Array<String> = Array(vector.size,{""})

        (0..ultimoIndice).forEach {
            arregloEstatico[it] = vector[it]
            arregloEstatico[it] = arregloEstatico[it].replace("&","\n")
        }

        //SE CONSTRUYE EL ADAPTER
        var adaptadorListView = ArrayAdapter<String>(this,
                       android.R.layout.simple_list_item_1, arregloEstatico)

        lista.adapter = adaptadorListView

        lista.setOnItemClickListener { parent, view, position, id ->
            var otroventana = Intent(this, Main2Activity::class.java)

            otroventana.putExtra("posicion",position)

            var vectorcito = vector[position].split("&")
            otroventana.putExtra("nombre", vectorcito[0])
            otroventana.putExtra("domicilio", vectorcito[1])
            otroventana.putExtra("telefono",vectorcito[2])


            startActivityForResult(otroventana,8)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //SE ACTIVA SI Y SOLO SI, ES INVOCADO A TRAVES DE startActivityForResult
        if(requestCode==8) {
            //lo llamó LISTVIEW
            if(resultCode==5){
                AlertDialog.Builder(this)
                    .setMessage("CANCELASTE ACTUALIZACION DE DATOS")
                    .show()
            }
            if(resultCode==7){
                var datosRegresa2doActivity = data!!.extras

                var nombre = datosRegresa2doActivity.getString("nombre")
                var domicilio = datosRegresa2doActivity.getString("domicilio")
                var telefono = datosRegresa2doActivity.getString("telefono")
                var posicion = datosRegresa2doActivity.getInt("posicion")

                var datosConcatenado = nombre+"&"+domicilio+"&"+telefono
                vector.set(posicion,datosConcatenado)

                AlertDialog.Builder(this)
                    .setMessage("SE MODIFICO CORRECTAMENTE")
                    .show()
            }
        }

    }

    private fun eliminarCeldilla() {
        var campoID = EditText(this)

        campoID.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage("ESCRIBA EL ID A ELIMINAR")
            .setView(campoID)
            .setPositiveButton("ELIMINAR"){d,i->
                eliminarCeldilla2(campoID.text.toString().toInt())
            }
            .setNegativeButton("Cancelar"){d,i->}
            .show()

    }

    private fun eliminarCeldilla2(indiceEliminar : Int) {
        var data = vector[indiceEliminar].split("&")

        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage("¿SEGURO QUE DESEAS ELIMINAR A: \nNOMBRE: "+data[0]+"\nDOMICILIO: "+data[1])
            .setPositiveButton("SI"){d,i->
                vector.removeAt(indiceEliminar)
            }
            .setNegativeButton("NO"){d,i->}
            .show()
    }

    /*private fun mostrarDatos() {
        var total = vector.size-1
        var data = ""

        (0..total).forEach {
            data += vector[it] + "\n------------\n"
            data = data.replace("&","\n")
        }

        resultado.setText(data)
    }*/

    private fun insertarEnVector() {
        if(nombre.text.isEmpty() || telefono.text.isEmpty() || domicilio.text.isEmpty()){
            mensaje("ERROR TODOS LOS CAMPOS DEBEN SER LLENADOS")
            return
        }
        var data = nombre.text.toString()+"&"+domicilio.text.toString()+"&"+telefono.text.toString()

        vector.add( data)
        limpiarCampos()
        mensaje("SE INSERTO EN VECTOR")
    }

    private fun limpiarCampos() {
        nombre.setText("")
        domicilio.setText("")
        telefono.setText("")
    }

    private fun mensaje(s: String) {
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(s)
            .setPositiveButton("ok"){d,i->}
            .show()
    }
}
