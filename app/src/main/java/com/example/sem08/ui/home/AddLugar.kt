package com.example.sem08.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sem08.R
import com.example.sem08.databinding.FragmentAddLugarBinding
import com.example.sem08.model.Lugar
import com.example.sem08.viewmodel.HomeViewModel


class AddLugar : Fragment() {

    private var _binding: FragmentAddLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(homeViewModel::class.java)
        _binding = FragmentAddLugarBinding.inflate(inflater, container, false)

        binding.btAddLugar.setOnClickListener{agregarLugar()}
        // Inflate the layout for this fragment
        return binding.root
    }

   private fun agregarLugar(){
       val nombre = binding.etNombre.text.toString()
       val correo = binding.etEmail.text.toString()
       val telefono = binding.etTelefono.text.toString()
       val web = binding.etWeb.text.toString()

       if(nombre.isNotEmpty()){
            val lugar = Lugar(0, nombre, correo, telefono, web)
           //Proceso de agregar bd
            homeViewModel.saveLugar(lugar)
           Toast.makeText(requireContext(), "Exito", Toast.LENGTH_LONG).show()
       }
       else{
           Toast.makeText(requireContext(), getString(R.string.msg_error), Toast.LENGTH_LONG).show()
       }
   }
}