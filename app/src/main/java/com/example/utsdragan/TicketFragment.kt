package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.utsdragan.databinding.FragmentTicketBinding


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
                if(it.isEmpty()) {
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
                        adapter = TicketAdapter(it)
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

}