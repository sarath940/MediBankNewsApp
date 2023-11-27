package com.example.medibanknewsapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.medibanknewsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        initDestinationListener(navController)
    }

    private fun initDestinationListener(navController:NavController) {
        navController.addOnDestinationChangedListener { controller: NavController?, destination: NavDestination, bundle: Bundle? ->
            when (destination.id) {
                R.id.articleFragment -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
            }
        }
}
