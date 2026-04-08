package com.example.questionbank

import android.content.Intent
import android.opengl.Matrix
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView

class MainActivity_Constraint : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_constraint)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toolbar = findViewById<MaterialToolbar>(R.id.materialToolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { item ->
            if (item.itemId==R.id.drawer_home){
                replaceFragment(HomeFragment())
            }
            else if (item.itemId==R.id.drawer_profile){
                replaceFragment(BookmarkFragment())
            }
            else if(item.itemId==R.id.drawer_bookmark){
                replaceFragment(ProfileFragment())
            }
            else if (item.itemId==R.id.drawer_logout){
                val builder = AlertDialog.Builder(this)

                val sharedPref = getSharedPreferences("userdata",MODE_PRIVATE)
                val editor = sharedPref.edit()

                builder.setTitle("Logout")

                builder.setMessage("Do you want to logout?")

                builder.setNegativeButton("Yes") { dialog, which ->
                    editor.apply{
                        putString("name","")
                        putString("email","")
                        putString("phone","")
                        putString("age","")
                        putString("state","")
                        apply()
                    }

                    val sharedPrefDbms1 = getSharedPreferences("bookmarkdbms1",MODE_PRIVATE)
                    val editor = sharedPrefDbms1.edit()
                    editor.remove("dbms1")
                    editor.apply()

                    val sharedPrefDbms2 = getSharedPreferences("bookmarkdbms2",MODE_PRIVATE)
                    val editor1 = sharedPrefDbms2.edit()
                    editor1.remove("dbms2")
                    editor1.apply()

                    val sharedPrefCttp1 = getSharedPreferences("bookmarkcttp1",MODE_PRIVATE)
                    val editor2 = sharedPrefCttp1.edit()
                    editor2.remove("cttp1")
                    editor2.apply()

                    val sharedPrefCttp2 = getSharedPreferences("bookmarkcttp2",MODE_PRIVATE)
                    val editor3 = sharedPrefCttp2.edit()
                    editor3.remove("cttp2")
                    editor3.apply()

                    val intent = Intent(this, Login_Page::class.java)
                    startActivity(intent)
                }
                builder.setPositiveButton("Yes"){dialog, which ->
                    dialog.dismiss()
                }
                builder.show()
            }
            drawerLayout.closeDrawers()
            true
        }

        val sharedPref = getSharedPreferences("userdata",MODE_PRIVATE)
        val fullName = sharedPref.getString("state",null) ?: ""

        val wlcmTxt = findViewById<TextView>(R.id.wlcmTxt)

        val logout = findViewById<MaterialButton>(R.id.logout)

        val firstName = fullName.substringBefore(" ")
        wlcmTxt.text = "Welcome\n$firstName"

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        replaceFragment(HomeFragment())

        bottomNav.setOnItemSelectedListener { menuItem ->

            if (menuItem.itemId == R.id.nav_home){
                replaceFragment(HomeFragment())
            }

            else if (menuItem.itemId == R.id.nav_profile){
                replaceFragment(BookmarkFragment())
            }

            else if (menuItem.itemId == R.id.nav_bookmark){
                replaceFragment(ProfileFragment())
            }

            true
        }

        val editor = sharedPref.edit()

        logout.setOnClickListener {

            val builder = AlertDialog.Builder(this)

            builder.setTitle("Logout")

            builder.setMessage("Do you want to logout?")

            builder.setNegativeButton("No") { dialog, which ->
                editor.apply{
                    putString("name","")
                    putString("email","")
                    putString("phone","")
                    putString("age","")
                    putString("state","")
                    apply()
                }

                val sharedPrefDbms1 = getSharedPreferences("bookmarkdbms1",MODE_PRIVATE)
                val editor = sharedPrefDbms1.edit()
                editor.remove("dbms1")
                editor.apply()

                val sharedPrefDbms2 = getSharedPreferences("bookmarkdbms2",MODE_PRIVATE)
                val editor1 = sharedPrefDbms2.edit()
                editor1.remove("dbms2")
                editor1.apply()

                val sharedPrefCttp1 = getSharedPreferences("bookmarkcttp1",MODE_PRIVATE)
                val editor2 = sharedPrefCttp1.edit()
                editor2.remove("cttp1")
                editor2.apply()

                val sharedPrefCttp2 = getSharedPreferences("bookmarkcttp2",MODE_PRIVATE)
                val editor3 = sharedPrefCttp2.edit()
                editor3.remove("cttp2")
                editor3.apply()

                val intent = Intent(this, Login_Page::class.java)
                startActivity(intent)
            }
            builder.setPositiveButton("Yes"){dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_profile){
            replaceFragment(BookmarkFragment())
        }
        else if(item.itemId == R.id.nav_bookmark){
            replaceFragment(ProfileFragment())
        }
        else if (item.itemId == R.id.nav_logout){

                val builder = AlertDialog.Builder(this)

                val sharedPref = getSharedPreferences("userdata",MODE_PRIVATE)
                val editor = sharedPref.edit()

                builder.setTitle("Logout")

                builder.setMessage("Do you want to logout?")

                builder.setNegativeButton("No") { dialog, which ->
                    editor.apply{
                        putString("name","")
                        putString("email","")
                        putString("phone","")
                        putString("age","")
                        putString("state","")
                        apply()
                    }

                    val sharedPrefDbms1 = getSharedPreferences("bookmarkdbms1",MODE_PRIVATE)
                    val editor = sharedPrefDbms1.edit()
                    editor.remove("dbms1")
                    editor.apply()

                    val sharedPrefDbms2 = getSharedPreferences("bookmarkdbms2",MODE_PRIVATE)
                    val editor1 = sharedPrefDbms2.edit()
                    editor1.remove("dbms2")
                    editor1.apply()

                    val sharedPrefCttp1 = getSharedPreferences("bookmarkcttp1",MODE_PRIVATE)
                    val editor2 = sharedPrefCttp1.edit()
                    editor2.remove("cttp1")
                    editor2.apply()

                    val sharedPrefCttp2 = getSharedPreferences("bookmarkcttp2",MODE_PRIVATE)
                    val editor3 = sharedPrefCttp2.edit()
                    editor3.remove("cttp2")
                    editor3.apply()

                    val intent = Intent(this, Login_Page::class.java)
                    startActivity(intent)
                }
                builder.setPositiveButton("Yes"){dialog, which ->
                    dialog.dismiss()
                }
                builder.show()
            }
        return true
    }

    private fun replaceFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container,fragment)
//        transaction.addToBackStack(null)

        transaction.commit()
    }
}