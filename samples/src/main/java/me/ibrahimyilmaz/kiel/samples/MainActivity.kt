package me.ibrahimyilmaz.kiel.samples

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            navController.graph,
            findViewById<DrawerLayout>(R.id.drawer_layout)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun findNavController(
        @Suppress("SameParameterValue") @IdRes id: Int
    ) = (supportFragmentManager.findFragmentById(id) as NavHostFragment).navController

    override fun onOptionsItemSelected(item: MenuItem) =
        item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}
