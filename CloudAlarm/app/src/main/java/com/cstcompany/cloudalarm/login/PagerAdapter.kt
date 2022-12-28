package hu.bme.aut.android.cloudalarm.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

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