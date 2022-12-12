package com.example.sem08.ui.home

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.sem08.R
import com.example.sem08.databinding.FragmentUpdateLugarBinding
import com.example.sem08.model.Lugar
import com.example.sem08.viewmodel.HomeViewModel


class UpdateLugarFragment : Fragment() {

    //Recupera argumentos
    private val args by navArgs<UpdateLugarFragmentArgs>()

    private var _binding: FragmentUpdateLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel :: class.java)
        _binding = FragmentUpdateLugarBinding.inflate(inflater, container, false)

        //cargar los valores de editar
        binding.etNombre.setText(args.lugar.nombre)
        binding.etTelefono.setText(args.lugar.telefono)
        binding.etEmail.setText(args.lugar.correo)
        binding.etWeb.setText(args.lugar.web)

        binding.btUpdateLugar.setOnClickListener{updateLugar()}

        if(args.lugar.rutaAudio?.isNotEmpty() == true){
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(args.lugar.rutaAudio)
            mediaPlayer.prepare()
            binding.btPlay.isEnabled = true
        }
        else{
            binding.btPlay.isEnabled = false
        }

        binding.btPlay.setOnClickListener { mediaPlayer.start() }

        if(args.lugar.rutaImagen?.isNotEmpty() == true){
            Glide.with(requireContext())
                .load(args.lugar.rutaImagen)
                .into(binding.imagen)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun updateLugar(){
        val nombre = binding.etNombre.text.toString()
        val correo = binding.etEmail.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val web = binding.etWeb.text.toString()

        if(nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || web.isEmpty()){
            Toast.makeText(requireContext(), getString(R.string.msg_data), Toast.LENGTH_LONG)
        }
        else{
            val lugar = Lugar(args.lugar.id, nombre, correo, telefono,web, args.lugar.rutaAudio, args.lugar.rutaImagen)
            homeViewModel.saveLugar(lugar)
            Toast.makeText(requireContext(), "Lugar modificado", Toast.LENGTH_LONG)
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_home)
        }
    }
}