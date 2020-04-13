package com.allisson.marketlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class NovoProdutoActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_produto)
    }

    fun addProduto(view: View?) {
        val txtNome = findViewById<EditText>(R.id.txtProduto)
        val txtQuantidade = findViewById<EditText>(R.id.txtQtd)
        val txtValor = findViewById<EditText>(R.id.txtValor)

        if(txtNome.text.toString() != "" && txtQuantidade.text.toString() != "" && txtValor.text.toString() != ""){
        val produto = hashMapOf(
            "produto" to txtNome.text.toString(),
            "quantidade" to txtQuantidade.text.toString().toInt(),
            "valor" to txtValor.text.toString().toDouble(),
            "comprado" to "Não"
        )

        db.collection("lista").add(produto).addOnSuccessListener { documentReference ->
            Log.d(
                "Novo Produto",
                "Documento ID -> ${documentReference.id}"
            )
        }.addOnFailureListener { err ->
            Log.w("Prod. não adcionado", "Erro produto", err)
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
