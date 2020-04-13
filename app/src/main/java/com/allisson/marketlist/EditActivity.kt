package com.allisson.marketlist

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    private var idProduto: String? = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        idProduto = intent.getStringExtra("idProduto")
        db.collection("lista")
            .document(idProduto.toString())
            .get()
            .addOnSuccessListener { result ->
                Log.d("FIREBASE >>>>> ", "${result.id} => ${result.data}")
                val nome = findViewById<EditText>(R.id.txtProduto)
                nome.setText(result["produto"].toString())
                val valor = findViewById<EditText>(R.id.txtValor)
                valor.setText(result["valor"].toString())
                val qtd = findViewById<EditText>(R.id.txtQtd)
                qtd.setText(result["quantidade"].toString())
                val comprado = findViewById<Switch>(R.id.swComprado)
                comprado.isChecked = result["comprado"].toString() != "Não"
            }
            .addOnFailureListener { exception ->
                Log.w("FIREBASE >>>>> ", "Error getting documents.", exception)
            }

    }

    fun apagar(view: View?) {
        db.collection("lista")
            .document(idProduto.toString())
            .delete()
            .addOnSuccessListener { documentReference ->
                Log.d("Produto Excluido", "Documento ID: ${documentReference}")
            }
            .addOnFailureListener { err ->
                Log.w("Prod. Exclusão Error", "Error adding document", err)
            }
        setResult(666)
        finish()
    }

    fun cancelar(view: View?) {
        finish()
    }

    fun salvar(view: View?) {
        val txtNome = findViewById<EditText>(R.id.txtProduto)
        val txtQuantidade = findViewById<EditText>(R.id.txtQtd)
        val txtValor = findViewById<EditText>(R.id.txtValor)
        val swComprado = findViewById<Switch>(R.id.swComprado)

        if (txtNome.text.toString() != "" && txtQuantidade.text.toString() != "" && txtValor.text.toString() != "") {
            var comprado: String;
            if (swComprado.isChecked) {
                comprado = "Sim"
            } else {
                comprado = "Não"
            }
            val produto = hashMapOf(
                "produto" to txtNome.text.toString(),
                "quantidade" to txtQuantidade.text.toString().toInt(),
                "valor" to txtValor.text.toString().toDouble(),
                "comprado" to comprado
            )

            db.collection("lista").document(idProduto.toString()).update(produto)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        "Produto Editado",
                        "Documento ID -> $idProduto"
                    )
                }.addOnFailureListener { err ->
                    Log.w("Prod. não editado", "Erro produto", err)
                }

            val it = Intent().apply {

            }

            setResult(Activity.RESULT_OK, it)
            finish()
        } else {
            Toast.makeText(this, "Todos os campos são obrigatórios.", Toast.LENGTH_SHORT).show()
        }
    }
}


