package up.com.carparking.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.cardview_item_parking.view.*
import up.com.carparking.R
import up.com.carparking.repository.domain.ParkingLot
import up.com.carparking.repository.domain.ParkingStatus

/**
 * Created by Franklyn Vieira 22/02/2019
 * @author Franklyn Vieira
 * @since 22/02/2019
 */
class ParkingStatusAdapter(
        val status: ParkingStatus,
        val onClick: (ParkingLot) -> Unit) :
        RecyclerView.Adapter<ParkingStatusAdapter.ParkingStatusViewHolder>() {

    override fun getItemCount(): Int {
        return this.status.parkingLots?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingStatusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_item_parking,
            parent, false
        )
        return ParkingStatusViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParkingStatusViewHolder, position: Int) {
        val status = status.parkingLots?.get(position)
        val view = holder.itemView
        with(view) {
            tParkingId.text = status?.id ?: ""

            val color: Int
            if (status?.status.equals("FREE"))
                color = Color.GREEN
            else
                color = Color.RED

            setBackgroundColor(color)

            setOnClickListener { status?.let { it1 -> onClick(it1) } }
        }

    }

    // ViewHolder fica vazio pois usamos o import do Android Kotlin Extensions
    class ParkingStatusViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

