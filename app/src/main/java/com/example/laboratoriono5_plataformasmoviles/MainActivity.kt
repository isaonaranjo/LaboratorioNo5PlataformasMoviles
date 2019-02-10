package com.example.laboratoriono5_plataformasmoviles


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {
    /*
    MainActivity, contiene lista de contactos y boton de agregar contactos
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Se establece el URL para la db y se inicia
        val URL = "content://com.example.miona.appcontactos.ContactProvider"
        val contact = Uri.parse(URL)
        val c = contentResolver.query(contact, null, null, null, "name")


        //Se crea el adapter y se asocia  a la lista de contactos creada
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, MyApplication.contacts)
        contactList.adapter = adapter

        //Se implementa el Click listener a la listView para seleccionar un elemento de la lista
        contactList.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent: Intent = Intent(this@MainActivity, ContactInformation::class.java)
                val itemToGet = position
                intent.putExtra("itemToGet", itemToGet)
                startActivity(intent)
            }
        }
        // Abrir la actividad de "Agregar contacto" al apachar el boton
        addContact.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }
        //Al hacer clikc largo en cualquier contacto se elimina
        contactList.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                contentResolver.delete(ContactProvider.CONTENT_URI, position.toString(), null)
                MyApplication.contacts.removeAt(position)
                adapter.notifyDataSetChanged()
                return true
            }
        }


    }

}