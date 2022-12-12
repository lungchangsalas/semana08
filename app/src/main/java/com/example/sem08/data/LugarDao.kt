package com.example.sem08.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sem08.model.Lugar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import org.w3c.dom.Document

class LugarDao {
    //firebase var
    private  var codigoUsuario : String
    private  var firestore:FirebaseFirestore

    init {
        val usuario = Firebase.auth.currentUser?.email
        codigoUsuario = "$usuario"
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun obtenerLugares() : MutableLiveData<List<Lugar>> {
        val listLugares = MutableLiveData<List<Lugar>>()

        firestore.collection("lugaresMiercoles")
            .document(codigoUsuario)
            .collection("misLugares")
            .addSnapshotListener{ snapshot, e ->
                if(e != null){
                    return@addSnapshotListener
                }

                if(snapshot != null){
                    val lista = ArrayList<Lugar>()
                    val lugares = snapshot.documents
                    lugares.forEach{
                        val lugar = it.toObject(Lugar::class.java)
                        if(lugar != null){
                            lista.add(lugar)
                        }
                    }
                    listLugares.value = lista
                }
            }

        return listLugares
    }

    //insert
     fun agregarLugar(lugar: Lugar){
        val document: DocumentReference
        if(lugar.id.isEmpty()){
            //agregar
            document = firestore.collection("lugaresMiercoles")
                .document(codigoUsuario).collection("misLugares").document()

            lugar.id = document.id
        }
        else{
            //modificar
            document = firestore.collection("lugaresMiercoles")
                .document(codigoUsuario).collection("misLugares").document(lugar.id)
        }

        document.set(lugar)
            .addOnCompleteListener{
                Log.d("saveLugar", "Guardado con exito")
            }
            .addOnCanceledListener {
                Log.e("SaveLugar", "Error al guardar")
            }
    }

    //update
     fun modificarLugar(lugar: Lugar){

    }

    //delete
     fun eliminarLugar(lugar: Lugar){
        if(lugar.id.isNotEmpty()){
            firestore.collection("lugaresMiercoles")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
                .delete()
                .addOnCompleteListener{
                    Log.d("saveLugar", "eliminado con exito")
                }
                .addOnCanceledListener {
                    Log.e("SaveLugar", "Error al eliminar")
                }
        }
    }
}