package com.allisson.marketlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore

class NovoProdutoActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_produto)
    }

    fun addProduto(view: View?) {
        Log.d("AQUI -----> ", "AQUI")
        val txtNome = findViewById<EditText>(R.id.txtNomeProduto)
        val txtQuantidade = findViewById<EditText>(R.id.txtQtd)
        val txtValor = findViewById<EditText>(R.id.txtValor)

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

    }
}
