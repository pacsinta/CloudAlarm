package hu.bme.aut.android.cloudalarm.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var pageNumber by mutableStateOf(true)
    var register_msg by mutableStateOf("")
    var login_msg by mutableStateOf("")
}