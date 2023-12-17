package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.utsdragan.databinding.FragmentAdmincrudEditorDestinasiBinding


class AdmincrudEditorDestinasiFragment : Fragment() {

    private lateinit var binding: FragmentAdmincrudEditorDestinasiBinding
    private var onEdit = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAdmincrudEditorDestinasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            val received = arguments?.getString("nama_destinasi")
            if(!received.isNullOrBlank()) {
                namadestinasiTXT.setText(received)
                onEdit = true
            }


            backtoadmincruddestinasifragment.setOnClickListener {
                findNavController().navigate(R.id.action_admincrudEditorDestinasiFragment_to_CRUDFragment)
            }
            btnsimpandestinasicrud.setOnClickListener {
                if(onEdit) {
                    MainActivity.getInstance().updateDestination(
                        Destinasi( id = arguments?.getString("id")!!,
                            nama = namadestinasiTXT.text.toString())
                    )
                } else
                {
                    MainActivity.getInstance().postDestination(
                        Destinasi(nama = namadestinasiTXT.text.toString())
                    )
                }

                findNavController().navigate(R.id.action_admincrudEditorDestinasiFragment_to_CRUDFragment)
            }
        }
    }

}