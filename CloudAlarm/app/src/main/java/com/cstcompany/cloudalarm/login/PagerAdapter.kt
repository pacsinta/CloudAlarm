package com.cstcompany.cloudalarm.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cstcompany.cloudalarm.login.LoginFragment
import com.cstcompany.cloudalarm.login.LoginViewModel
import com.cstcompany.cloudalarm.login.RegisterFragment

class LoginPagerAdapter(fa: FragmentActivity, viewModel: LoginViewModel): FragmentStateAdapter(fa){
    override fun getItemCount(): Int = 2
    private val _viewModel = viewModel
    override fun createFragment(position: Int): Fragment{
        return when(position){
            0 -> {
                LoginFragment(_viewModel)
            }
            else -> {
                RegisterFragment(_viewModel)
            }
        }
    }
}