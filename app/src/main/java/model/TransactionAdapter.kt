package model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.lasalle.stm.R
import java.util.zip.Inflater

class TransactionAdapter(private val transactionList : List<Transactions>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val txtAmount: TextView = itemView.findViewById(R.id.txtAmount)
        val txtDate : TextView = itemView.findViewById(R.id.txtDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transactions_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val currentPosition = transactionList[position]
        holder.txtAmount.text = "$ " + currentPosition.amount.toString()
        holder.txtDate.text = currentPosition.date
    }

    override fun getItemCount(): Int {
       return transactionList.size
    }
}