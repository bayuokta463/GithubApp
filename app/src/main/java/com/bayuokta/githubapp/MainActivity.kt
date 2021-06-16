package com.bayuokta.githubapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayuokta.githubapp.adapter.UserAdapter
import com.bayuokta.githubapp.api.ApiResponse

import com.bayuokta.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ilEdtSearch.setEndIconOnClickListener {
            hideKeyboard(it)
            binding.edtSearch.setText("")
            binding.llMainFindUser.root.visibility = View.VISIBLE
            binding.llMainUserNotFound.root.visibility = View.GONE
            binding.llMainInternetError.root.visibility = View.GONE
            binding.rvUser.visibility = View.GONE
        }

        binding.btnSearch.setOnClickListener {
            hideKeyboard(it)
            val user = binding.edtSearch.text.toString().trim()
            if (user.isNotBlank()){
                binding.progressCircular.visibility = View.VISIBLE
                binding.llMainFindUser.root.visibility = View.GONE
                binding.llMainUserNotFound.root.visibility = View.GONE
                binding.llMainInternetError.root.visibility = View.GONE

                mainViewModel.getUser(user).observe(this,{ data ->
                    when(data){
                        ApiResponse.Empty -> {
                            binding.progressCircular.visibility = View.GONE
                            binding.rvUser.visibility = View.GONE
                            binding.llMainFindUser.root.visibility = View.GONE
                            binding.llMainInternetError.root.visibility = View.GONE
                            binding.llMainUserNotFound.root.visibility = View.VISIBLE
                        }
                        is ApiResponse.Error -> {
                            binding.progressCircular.visibility = View.GONE
                            binding.rvUser.visibility = View.GONE
                            binding.llMainFindUser.root.visibility = View.GONE
                            binding.llMainUserNotFound.root.visibility = View.GONE
                            binding.llMainInternetError.root.visibility = View.VISIBLE
                        }
                        is ApiResponse.Success -> {
                            binding.progressCircular.visibility = View.GONE
                            binding.llMainInternetError.root.visibility = View.GONE
                            binding.llMainUserNotFound.root.visibility = View.GONE
                            binding.rvUser.visibility = View.VISIBLE
                            val adapter = UserAdapter()
                            data.data?.let { userData ->
                                adapter.setData(userData)
                            }
                            val layoutManager = LinearLayoutManager(this)
                            layoutManager.orientation = LinearLayoutManager.VERTICAL
                            binding.rvUser.layoutManager = layoutManager
                            binding.rvUser.adapter = adapter
                        }
                    }
                })
            }else{
                Toast.makeText(this, R.string.text_error_username_empty, Toast.LENGTH_SHORT).show()
            }
            
        }

    }

    private fun hideKeyboard(view: View){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}