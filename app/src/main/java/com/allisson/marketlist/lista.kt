package com.allisson.marketlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.allisson.marketlist.models.Produto
import com.google.firebase.firestore.FirebaseFirestore

class lista : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance();
    private var listaProdutos: ArrayList<Produto> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)
        getListaDeCompras()
    }

    fun getListaDeCompras() {
        db.collection("lista").get().addOnSuccessListener { response ->
            for (produto in response) {
                Log.d("Produto FIREBASE => ", "${produto.id} => ${produto.data}");
            }
        }
    }
}
