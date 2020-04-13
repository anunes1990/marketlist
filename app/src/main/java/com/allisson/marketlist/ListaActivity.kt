package com.allisson.marketlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allisson.marketlist.adapter.ProdutoAdapter
import com.allisson.marketlist.models.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.produto.*
import kotlinx.android.synthetic.main.produto.view.*

class ListaActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance();
    private var listaProdutos: ArrayList<Produto> = ArrayList();
    private var totalPagar: Double = 0.00;

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
            totalPagar = 0.00;
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

                totalPagar += (produto.data["quantidade"].toString().toInt() * produto.data["valor"].toString().toDouble())
            }

            findViewById<TextView>(R.id.txtTotalPagar).text = totalPagar.toString()
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

    fun editProduto(view: View) {
        val intent = Intent(this, EditActivity::class.java).apply {
            val nome = view.findViewById<TextView>(R.id.txtProduto).text.toString()
            //val nome = cardProduto.findViewById<TextView>(R.id.txtProduto).text.toString()
            Log.d("Nome >>>", nome)
            val idProduto = listaProdutos.find { p -> p.nome == nome }?.id
            Log.d("ID >>>", idProduto.toString())

            putExtra("idProduto", idProduto.toString())
        }
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                listaProdutos = ArrayList()
                getListaDeCompras()
                Toast.makeText(this, "Produto registrado com sucesso!", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                listaProdutos = ArrayList()
                getListaDeCompras()
                Toast.makeText(this, "Produto editado com sucesso!", Toast.LENGTH_SHORT).show()
            } else if (resultCode == 666) {
                listaProdutos = ArrayList()
                getListaDeCompras()
                Toast.makeText(this, "Produto removido com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

