package com.example.utsdragan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utsdragan.databinding.FragmentTicketBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class TicketFragment : Fragment() {

    private lateinit var binding: FragmentTicketBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance()
        val data = mainActivity.getTicket(mainActivity.getSharedPreferences().getUsername())

        with(binding) {
            data.observe(viewLifecycleOwner) {
                val pass = mutableListOf<Ticket>()
                if(it.isNotEmpty()) {
                    for(i in it) {
                        if(isDateBefore(i.tanggal)) {
                            if(hasTimePassed(i.jam)) {
                                pass.add(i)
                            }
                        }
                    }
                }

                if(pass.isEmpty()) {
                    imageNoActiveTicket.visibility = View.VISIBLE
                    profileFragmentName.visibility = View.VISIBLE
                    textNoActiveTicketdesc.visibility = View.VISIBLE
                    btnPesanTiket2.visibility = View.VISIBLE
                    RVTiketAktif.visibility = View.GONE
                } else {
                    imageNoActiveTicket.visibility = View.GONE
                    profileFragmentName.visibility = View.GONE
                    textNoActiveTicketdesc.visibility = View.GONE
                    RVTiketAktif.visibility = View.VISIBLE
                    RVTiketAktif.apply {
                        adapter = TicketAdapter(pass)
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                }
            }
            btnPesanTiket2.setOnClickListener {
                findNavController().navigate(R.id.action_ticketFragment2_to_inputRencanaFragment)
            }

            btnTiketTidakAktif.setOnClickListener {
                findNavController().navigate(R.id.action_ticketFragment2_to_riwayatPesananFragment)
            }
        }

    }

    fun isDateBefore(dateString: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            // Parse the input date string
            val inputDate = dateFormat.parse(dateString)

            // Get the current date
            val currentDate = Date()

            // Compare the input date with the current date
            return inputDate?.after(currentDate) == true
        } catch (e: Exception) {
            // Handle parsing errors, e.g., if the input date is in an invalid format
            e.printStackTrace()
            return false
        }
    }
    fun hasTimePassed(inputTime: String): Boolean {
        // Specify the time format
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Get the current time
        val currentTime = Calendar.getInstance().time

        try {
            // Parse the input time
            val inputDate = timeFormat.parse(inputTime)

            // Compare the current time with the input time
            Log.d("time", inputDate.toString())
            return inputDate?.before(currentTime) == true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Return false if there's an exception or if parsing fails
        return false
    }


}