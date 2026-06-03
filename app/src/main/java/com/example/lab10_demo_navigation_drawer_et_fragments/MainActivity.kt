package com.example.lab10_demo_navigation_drawer_et_fragments

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.lab10_demo_navigation_drawer_et_fragments.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ma touche : Support d'une Toolbar personnalisée pour un contrôle total
        setSupportActionBar(binding.toolbar)

        // Configuration du DrawerToggle (le bouton "hamburger")
        val toggle = ActionBarDrawerToggle(
            this, 
            binding.drawerLayout, 
            binding.toolbar,
            R.string.navigation_drawer_open, 
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        // Ma touche : Gestion moderne et fluide du bouton retour
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        // Chargement du fragment par défaut au démarrage
        if (savedInstanceState == null) {
            replaceFragment(BlankFragment(), getString(R.string.title_fragment1))
            binding.navView.setCheckedItem(R.id.nav_fragment1)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_fragment1 -> replaceFragment(BlankFragment(), getString(R.string.title_fragment1))
            R.id.nav_fragment2 -> replaceFragment(BlankFragment2(), getString(R.string.title_fragment2))
            R.id.nav_list -> replaceFragment(FragmentList(), getString(R.string.title_list))
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * Ma touche : Fonction utilitaire pour changer de fragment avec animation
     */
    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.contenu, fragment)
            .commit()
        
        // Mise à jour dynamique du titre dans la Toolbar
        supportActionBar?.title = title
    }
}