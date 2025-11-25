package com.pacote.cidadeconectada

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var lblNome: TextView
    private lateinit var lblDescricao: TextView
    private lateinit var lblCoords: TextView
    private lateinit var lblTipo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lblNome = findViewById(R.id.lblNome)
        lblDescricao = findViewById(R.id.lblDescricao)
        lblCoords = findViewById(R.id.lblCoords)
        lblTipo = findViewById(R.id.lblTipo)
        val btnScan = findViewById<Button>(R.id.btnScan)

        btnScan.setOnClickListener {
            val integrator = IntentIntegrator(this)

            integrator.setCaptureActivity(CustomScannerActivity::class.java)

            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.setPrompt("Aponte para a placa")
            integrator.setCameraId(0)
            integrator.setBeepEnabled(false)

            integrator.setOrientationLocked(false)

            integrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Leitura cancelada", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val gson = Gson()
                    val dados = gson.fromJson(result.contents, DadosLocal::class.java)

                    lblNome.text = "Nome: ${dados.nome}"
                    lblDescricao.text = "Descrição: ${dados.descricao}"
                    lblCoords.text = "GPS: ${dados.coordenadas}"
                    lblTipo.text = "Tipo: ${dados.tipo}"

                    Toast.makeText(this, "Local identificado!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "QR Code inválido!", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}