package com.example.igear.devogellaandroidsqlitefirst.ui.activity


import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast

import com.example.igear.devogellaandroidsqlitefirst.R
import com.example.igear.devogellaandroidsqlitefirst.ui.dialog.AddItemDialog
import com.example.igear.devogellaandroidsqlitefirst.ui.fragment.CompletedListFragment
import com.example.igear.devogellaandroidsqlitefirst.ui.fragment.ToDoListFragment

import java.util.ArrayList


class TestDatabaseActivity : AppCompatActivity() {
    private val _toDoFragment = ToDoListFragment()
    private val _completedFragment = CompletedListFragment()
    private var toolbar: Toolbar? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var navigationView: NavigationView? = null
    private var drawerLayout: DrawerLayout? = null
    private var floatingActionButton: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_database)

        toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.app_name)

        navigationView = findViewById(R.id.navigation_view) as NavigationView?

        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        floatingActionButton = findViewById(R.id.fab_button) as FloatingActionButton?

        viewPager = findViewById(R.id.viewpager) as ViewPager?
        setupViewPager(viewPager as ViewPager)

        tabLayout = findViewById(R.id.tabs) as TabLayout?
        tabLayout!!.setupWithViewPager(viewPager)

        drawerLayout = findViewById(R.id.drawer) as DrawerLayout?
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.ma_nav_drawer_open, R.string.ma_nav_drawer_close) {

            override fun onDrawerClosed(drawerView: View?) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View?) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView)
            }
        }
        //Setting the actionbarToggle to drawer layout
        drawerLayout!!.addDrawerListener(actionBarDrawerToggle)

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState()

        navigationView!!.setNavigationItemSelectedListener { menuItem ->
            // This method will trigger on item Click of navigation menu
            //Checking if the item is in checked state or not, if not make it in checked state
            if (menuItem.isChecked)
                menuItem.isChecked = false
            else
                menuItem.isChecked = true

            //Closing drawer on item click
            drawerLayout!!.closeDrawers()

            //Check to see which item was being clicked and perform appropriate action
            when (menuItem.itemId) {


            //Replacing the main content with ContentFragment Which is our Inbox View;
                R.id.monday -> {
                    Toast.makeText(applicationContext, "Inbox Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.tuesday -> {
                    Toast.makeText(applicationContext, "Stared Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.wednesday -> {
                    Toast.makeText(applicationContext, "Send Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.thursday -> {
                    Toast.makeText(applicationContext, "Drafts Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.friday -> {
                    Toast.makeText(applicationContext, "All Mail Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.saturday -> {
                    Toast.makeText(applicationContext, "Trash Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.sunday -> {
                    Toast.makeText(applicationContext, "Spam Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    Toast.makeText(applicationContext, "Somethings Wrong", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }


        // FragmentManager fm = getSupportFragmentManager();
        //fm.beginTransaction().replace(R.id.am_content, _mainFragment).commit();
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(_toDoFragment, "To Do")
        adapter.addFragment(_completedFragment, "Completed")
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == adapter.mFragmentList.indexOf(_toDoFragment))
                    floatingActionButton!!.show()
                else
                    floatingActionButton!!.hide()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        viewPager.adapter = adapter
    }

    //Will be called via the onClick attribute
    //of the buttons in main.xml
    fun onClick(view: View) {
        when (view.id) {
            R.id.fab_button -> {
                val newFragment = AddItemDialog.getDialogInstance()
                newFragment.show(fragmentManager, "dialog")
            }
        }
    }

    fun doPositiveClick(taskDesc: String) {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!")
        _toDoFragment.addToDoItem(taskDesc)

    }

    fun doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!")
    }

    fun updateCompletedTasks() {
        _completedFragment.updateCompletedList()
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

}

