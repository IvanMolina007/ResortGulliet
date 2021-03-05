package com.example.resortgulliet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.resortgulliet.fragmentos.ExplorarFragment
import com.example.resortgulliet.fragmentos.InicioFragment
import com.example.resortgulliet.fragmentos.UsuarioFragment
import com.example.resortgulliet.inicioCreacion.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.fragment_usuario.*

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val inicioFragment = InicioFragment()
        val explorarFragment = ExplorarFragment()
        val usuarioFragment = UsuarioFragment()

        makeCurrentFragment(inicioFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_home -> makeCurrentFragment(inicioFragment)
                R.id.ic_wifi -> makeCurrentFragment(explorarFragment)
                R.id.ic_user -> makeCurrentFragment(usuarioFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}