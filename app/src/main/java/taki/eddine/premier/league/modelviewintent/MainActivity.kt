package taki.eddine.premier.league.modelviewintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import taki.eddine.premier.league.modelviewintent.ApiHelper.ApiHelperImp
import taki.eddine.premier.league.modelviewintent.ApiHelper.RetrofitBuilder
import taki.eddine.premier.league.modelviewintent.MvvmPackage.MainIntent
import taki.eddine.premier.league.modelviewintent.MvvmPackage.MainState
import taki.eddine.premier.league.modelviewintent.MvvmPackage.MainViewModel
import taki.eddine.premier.league.modelviewintent.MvvmPackage.ViewModelFactory
import taki.eddine.premier.league.modelviewintent.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            setupUI()
            setupViewModel()
            observeViewModel()
            setupClicks()

    }
        private fun setupClicks() {
            buttonFetchUser.setOnClickListener {
                lifecycleScope.launch {
                    mainViewModel.userIntent.send(MainIntent.FetchUser)
                }
            }
        }


        private fun setupUI() {
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.run {
                addItemDecoration(
                    DividerItemDecoration(
                        recyclerView.context,
                        (recyclerView.layoutManager as LinearLayoutManager).orientation
                    )
                )
            }
            recyclerView.adapter = adapter
        }


        private fun setupViewModel() {
            mainViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(
                    ApiHelperImp(
                        RetrofitBuilder.apiService
                    )
                )
            ).get(MainViewModel::class.java)
        }

        private fun observeViewModel() {
            lifecycleScope.launch {
                mainViewModel.state.collect {
                    when (it) {
                        is MainState.Idle -> {

                        }
                        is MainState.Loading -> {
                            buttonFetchUser.visibility = View.GONE
                            progressBar.visibility = View.VISIBLE
                        }

                        is MainState.Users -> {
                            progressBar.visibility = View.GONE
                            buttonFetchUser.visibility = View.GONE
                            renderList(it.user)
                        }
                        is MainState.Error -> {
                            progressBar.visibility = View.GONE
                            buttonFetchUser.visibility = View.VISIBLE
                            Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        private fun renderList(users: List<User>) {
            recyclerView.visibility = View.VISIBLE
            users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
            adapter.notifyDataSetChanged()
        }
    }