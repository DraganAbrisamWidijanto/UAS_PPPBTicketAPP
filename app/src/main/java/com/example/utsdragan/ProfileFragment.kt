package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.utsdragan.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance()
        val sharePreferences = mainActivity.getSharedPreferences()

        with(binding) {
            profileFragmentName.text = sharePreferences.getUsername()
            profileFragmentEmail.text = sharePreferences.getEmail()
            profileFragmentNIM.text = sharePreferences.getNim()
            btnLogout.setOnClickListener {
                sharePreferences.clear()
                activity?.finish()
            }
        }

    }


}