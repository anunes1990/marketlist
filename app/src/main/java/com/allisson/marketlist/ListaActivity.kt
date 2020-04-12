package com.allisson.marketlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allisson.marketlist.adapter.ProdutoAdapter
import com.allisson.marketlist.models.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.produto.*

class ListaActivity : AppCompatActivity() {
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
                        quantidade = produto.data["quantidade"].toString().toInt(),
                        comprado = produto.data["comprado"].toString(),
                        valor = produto.data["valor"].toString().toDouble()
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

    fun addProduto(view: View?) {
        val it = Intent(this, NovoProdutoActivity::class.java).apply {
        }
        startActivityForResult(it, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                listaProdutos = ArrayList()
                getListaDeCompras()
                Toast.makeText(this, "Produto registrado com sucesso!", Toast.LENGTH_SHORT).show()
            }
        } /*else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                listaProdutos = ArrayList()
                getProdutos()
                Toast.makeText(this, "Produto editado com sucesso!", Toast.LENGTH_SHORT).show()
            }*/ else if (resultCode == 666) {
            listaProdutos = ArrayList()
            getListaDeCompras()
            Toast.makeText(this, "Produto removido com sucesso!", Toast.LENGTH_SHORT).show()
        }
    }
}

