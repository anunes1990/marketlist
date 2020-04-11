package com.allisson.marketlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allisson.marketlist.adapter.ProdutoAdapter
import com.allisson.marketlist.models.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.produto.*

class lista : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance();
    private var listaProdutos: ArrayList<Produto> = ArrayList();

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)
        getListaDeCompras()
    }

    fun getListaDeCompras() {
        db.collection("lista").get().addOnSuccessListener { response ->
            for (produto in response) {
                Log.d("Produto FIREBASE => ", "${produto.id} => ${produto.data}");
                listaProdutos.add(
                    Produto(
                        id = produto.id,
                        nome = produto.data["produto"].toString(),
                        quantidade = produto.data["quantidade"].toString().toDouble(),
                        comprado = produto.data["comprado"].toString()
                    )
                )
            }

            viewManager = LinearLayoutManager(this)
            viewAdapter = ProdutoAdapter(listaProdutos)
            recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProdutos).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    fun addProduto(view: View?){

    }
}
