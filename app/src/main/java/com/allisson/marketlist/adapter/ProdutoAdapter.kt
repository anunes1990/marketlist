package com.allisson.marketlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allisson.marketlist.R
import com.allisson.marketlist.models.Produto


class ProdutoAdapter(private var listaProdutos: ArrayList<Produto>) :
    RecyclerView.Adapter<ProdutoAdapter.ProdutoHolder>() {

    class ProdutoHolder : RecyclerView.ViewHolder {
        var txtProduto: TextView
        var txtQuantidade: TextView
        var txtComprado: TextView
        var txtValor: TextView
        var txtTotal: TextView

        constructor(view: View) : super(view) {
            txtProduto = view.findViewById(R.id.txtProduto)
            txtQuantidade = view.findViewById(R.id.txtQtd)
            txtComprado = view.findViewById(R.id.txtComprado)
            txtValor = view.findViewById(R.id.txtValor)
            txtTotal = view.findViewById(R.id.txtTotal)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProdutoAdapter.ProdutoHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.produto,
            parent,
            false
        ) as View
        return ProdutoHolder(view)
    }

    override fun onBindViewHolder(holder: ProdutoHolder, position: Int) {
        holder.txtProduto.text = listaProdutos.get(position).nome
        holder.txtQuantidade.text = listaProdutos.get(position).quantidade.toString()
        holder.txtComprado.text = listaProdutos.get(position).comprado
        holder.txtValor.text = listaProdutos.get(position).valor.toString()
        holder.txtTotal.text =
            (listaProdutos.get(position).quantidade * listaProdutos.get(position).valor).toString()
    }

    override fun getItemCount() = listaProdutos.size

}
