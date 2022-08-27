package com.android.developer.sequis_test

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.developer.sequis_test.databinding.ActivityPicturesBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PicturesActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { false }

        val binding = ActivityPicturesBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)
            setSupportActionBar(topAppBar)

        }
        setupNavController(binding.topAppBar)
    }

    private fun setupNavController(topAppBar: MaterialToolbar) {

        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        val navController = navHostFragment.navController


        with(navController) {
            appBarConfiguration = AppBarConfiguration(graph)
            setupActionBarWithNavController(this, appBarConfiguration)
        }

        topAppBar.observeNavElements(navController)
    }

    private fun MaterialToolbar.observeNavElements(
        navController: NavController,
    ) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailFragment) {
                setNavigationIcon(R.drawable.ic_back)
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem) = item.onNavDestinationSelected(
        findNavController(R.id.nav_host_fragment)
    ) || super.onOptionsItemSelected(item)

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment)
        .navigateUp(appBarConfiguration)
}