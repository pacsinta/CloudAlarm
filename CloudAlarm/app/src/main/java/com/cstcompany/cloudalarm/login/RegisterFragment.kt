package hu.bme.aut.android.cloudalarm.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.cloudalarm.MainActivity
import hu.bme.aut.android.cloudalarm.databinding.FragmentLoginBinding

class RegisterFragment(viewModel: LoginViewModel) : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val _viewModel = viewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onResume() {
        super.onResume()
        _viewModel.pageNumber = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        //val view = binding.root
        binding.composeLoginView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                LoginUI(true, _viewModel.register_msg){ email, pwd ->
                    auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            //TODO
                            Log.d("LoginFragment", "Registration failed")
                        }
                    }
                }
            }
        }

        return binding.root
    }
}