package com.example.bfaa_submission_1

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bfaa_submission_1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Git User"

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.listLayout.setHasFixedSize(true)
        showList()
        mainViewModel.getUserList().observe(this, {gitUsers ->
            if (gitUsers != null) {
                adapter.setData(gitUsers)
                showLoading(false)
            }
        })
    }

    private fun showList() {
        binding.listLayout.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.listLayout.adapter = adapter
        showLoading(false)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedList(data)
            }
        })
    }

    private fun showSelectedList(data: User) {
        val mIntent = Intent(this@MainActivity, ProfileActivity::class.java)
        mIntent.putExtra(ProfileActivity.EXTRA_USER, data)
        startActivity(mIntent)
        profileViewModel = ProfileViewModel()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    showLoading(false)
                } else {
                    showLoading(true)
                    mainViewModel.setUserList(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    showLoading(false)
                } else {
                    showLoading(true)
                    mainViewModel.setUserList(newText)
                }
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting) {
            val localeIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(localeIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}