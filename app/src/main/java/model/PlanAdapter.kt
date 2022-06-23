package model

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.lasalle.stm.MainActivity
import com.project.lasalle.stm.R

class PlanAdapter(private val plansList : List<Plans>, private val listener : OnItemClickListener) : RecyclerView.Adapter<PlanAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.plans_list_view, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = plansList[position]
        holder.txtPlanName.text = currentItem.planName
        holder.txtPlanAmount.text = "$" + currentItem.amount
        holder.txtPlanDuration.text = currentItem.duration.toString() + " Days"
    }

    override fun getItemCount(): Int {
      return plansList.size
    }

    inner class MyViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){

        val txtPlanName : TextView  = itemView.findViewById(R.id.txtPlanName)
        val txtPlanAmount: TextView = itemView.findViewById(R.id.txtPlanAmount)
        val txtPlanDuration: TextView = itemView.findViewById(R.id.txtPlanDuration)

        init {
            itemView.setOnClickListener(){
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onItemClick(position)
                }
            }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}