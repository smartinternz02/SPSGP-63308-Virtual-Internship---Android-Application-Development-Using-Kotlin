package com.kdb.grocer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.color.MaterialColors
import com.kdb.grocer.R
import com.kdb.grocer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Toolbar as the Action Bar
        setSupportActionBar(binding.toolbar)

        // Setup Toolbar with Navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        // Change nav icon to Close when in a full-screen dialog
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.addItemFragment) {
                with (binding) {
                    val tint = MaterialColors.getColor(
                        toolbar,
                        com.google.android.material.R.attr.colorControlNormal
                    )
                    toolbar.setNavigationIconTint(tint)
                    toolbar.setNavigationIcon(R.drawable.ic_close)
                }
            }
        }
    }
}