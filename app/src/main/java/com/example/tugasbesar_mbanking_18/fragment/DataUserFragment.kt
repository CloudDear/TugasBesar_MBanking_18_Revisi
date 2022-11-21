package com.example.tugasbesar_mbanking_18.fragment

import android.os.Bundle
import android.service.autofill.UserData
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugasbesar_mbanking_18.R
import com.example.tugasbesar_mbanking_18.databinding.FragmentDataUserBinding

@Suppress ("UNREACHABLE_CODE")
class DataUserFragment : Fragment() {

    private var _binding: FragmentDataUserBinding? = null
    private val binding get() = _binding!!
    private val listUser = ArrayList<UserData>()


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentDataUserBinding.inflate(inflater, container, false)
//        return binding.root
//        getDataUser()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        getDataUser()
//    }
//
}