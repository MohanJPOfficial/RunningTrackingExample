package com.example.runningtracker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.runningtracker.R
import com.example.runningtracker.databinding.ActivityMainBinding
import com.example.runningtracker.db.RunDAO
import com.example.runningtracker.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var navHostFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //setup bottom navigation with navigation component
        supportFragmentManager.findFragmentById(R.id.navHostFragment)
            ?.let { navHostFragment = it }

        navigateToTrackingFragmentIfNeeded(intent)

        navHostFragment?.findNavController()
            ?.let { binding.bottomNavigationView.setupWithNavController(it) }

        supportFragmentManager.findFragmentById(R.id.navHostFragment)?.findNavController()
            ?.addOnDestinationChangedListener { _, destination, _ ->

                when(destination.id) {

                    R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment -> {
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    }
                    else -> binding.bottomNavigationView.visibility = View.GONE
                }
            }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        if(intent?.action == ACTION_SHOW_TRACKING_FRAGMENT)
            navHostFragment?.findNavController()?.navigate(R.id.action_global_trackingFragment)
    }
}