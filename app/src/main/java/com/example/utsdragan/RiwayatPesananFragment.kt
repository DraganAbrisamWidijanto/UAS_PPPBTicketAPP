package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utsdragan.databinding.FragmentRiwayatPesananBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class RiwayatPesananFragment : Fragment() {

    private lateinit var binding: FragmentRiwayatPesananBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentRiwayatPesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance()
        val list = mainActivity.getTicket(mainActivity.getSharedPreferences().getUsername())
        val pass = mutableListOf<Ticket>()

        list.observe(viewLifecycleOwner) {
            for(i in it) {
                if(isDatePassed(i.tanggal.toString())) {
                    pass.add(i)
                }
            }

            with(binding) {
                if(pass.isEmpty()) {
                    imageNoActiveTicket.visibility = View.VISIBLE
                    profileFragmentName.visibility = View.VISIBLE
                    textNoActiveTicketdesc.visibility = View.VISIBLE
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


        }
        with(binding) {
            backtoticketfragmen.setOnClickListener {
                findNavController().navigate(R.id.action_riwayatPesananFragment_to_ticketFragment2)
            }
        }
    }

    fun isDatePassed(dateString: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            // Parse the input date string
            val inputDate = dateFormat.parse(dateString)

            // Get the current date
            val currentDate = Date()

            // Compare the input date with the current date
            return inputDate?.before(currentDate) == true
        } catch (e: Exception) {
            // Handle parsing errors, e.g., if the input date is in an invalid format
            e.printStackTrace()
            return false
        }
    }

}