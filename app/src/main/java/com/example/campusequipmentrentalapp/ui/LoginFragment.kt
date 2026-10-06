package com.example.campusequipmentrentalapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrentalapp.R
import com.example.campusequipmentrentalapp.data.UserRepository
import com.example.campusequipmentrentalapp.data.UserSession
import com.example.campusequipmentrentalapp.databinding.FragmentLoginBinding


class LoginFragment :
    Fragment(R.layout.fragment_login) {


    private var _binding:
            FragmentLoginBinding? = null


    private val binding:
            FragmentLoginBinding
        get() = _binding!!


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )


        _binding =   FragmentLoginBinding.bind(view)


        binding.btnLogin
            .setOnClickListener {


                // 입력한 ID
                val loginId =
                    binding.etLoginId
                        .text
                        .toString()
                        .trim()


                // 빈 값 검사
                if (loginId.isEmpty()) {

                    binding.etLoginId.error =
                        "사용자 ID를 입력하세요."

                    binding.etLoginId
                        .requestFocus()

                    return@setOnClickListener
                }


                // 사용자 검색
                val user = UserRepository().findByLoginId(loginId)


                // 등록되지 않은 사용자
                if (user == null) {

                    Toast.makeText(
                        requireContext(),
                        "등록되지 않은 사용자입니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }


                // 로그인 성공
                UserSession.login(
                    user
                )


                Toast.makeText(
                    requireContext(),
                    "${user.name}님 로그인 성공",
                    Toast.LENGTH_SHORT
                ).show()


                // Home으로 이동
                findNavController()
                    .navigate(
                        R.id
                            .action_loginFragment_to_homeFragment
                    )
            }
    }


    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null
    }
}