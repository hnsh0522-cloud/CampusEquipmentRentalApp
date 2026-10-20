package com.example.campusequipmentrentalapp.ui.rental

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.campusequipmentrentalapp.R
import com.example.campusequipmentrentalapp.data.EquipmentRepository
import com.example.campusequipmentrentalapp.data.UserSession
import com.example.campusequipmentrentalapp.data.local.AppDataBase
import com.example.campusequipmentrentalapp.data.local.RentalEntity
import com.example.campusequipmentrentalapp.databinding.FragmentRentalBinding
import com.jeiu.campusequipmentrental.util.NavKeys
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RentalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RentalFragment : Fragment(R.layout.fragment_rental) {

    private var _binding: FragmentRentalBinding? = null
    private val binding: FragmentRentalBinding
        get() = _binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRentalBinding.bind(view)

        val equipmentId = arguments?.getInt(NavKeys.EQUIPMENT_ID, -1) ?: -1
        val equipment = EquipmentRepository.findById(equipmentId)

        val currentUser = UserSession.currentUser
        if(currentUser == null){
            Toast.makeText(
                requireContext(),
                "로그인 사용자가 없습니다.",
                Toast.LENGTH_SHORT
                )
        }

        if (equipment == null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.equipment_not_found),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
            return
        }

        binding.tvRentalUser.text = """
                                        신청자: ${currentUser?.name}
                                        사용자 ID: ${currentUser?.userId}
                                        구분 : ${currentUser?.role}
                                        학과 : ${currentUser?.department}
        """.trimMargin()
        binding.tvSelectedEquipment.text = getString(
            R.string.selected_equipment_format,
            equipment.icon,
            equipment.name
        )
        binding.tvRentalRule.text = getString(
            R.string.rental_rule_format,
            equipment.maxRentalDays
        )

        val periodItems = (1..equipment.maxRentalDays).map { day ->
            getString(R.string.day_format, day)
        }
        binding.spinnerPeriod.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            periodItems
        )

        binding.btnSubmitRental.setOnClickListener {

            val applicantName = currentUser?.name
            val userId = currentUser?.userId
            val purpose = binding.etPurpose.text.toString().trim()

            if(applicantName == ""){
                binding.tvRentalUser.text = getString(R.string.enter_name)
                return@setOnClickListener
            }

            if (purpose.isEmpty()) {
                binding.etPurpose.error = getString(R.string.enter_purpose)
                binding.etPurpose.requestFocus()
                return@setOnClickListener
            }

            val rentalDays = binding.spinnerPeriod.selectedItemPosition + 1

            val database = AppDataBase.getDatabase(requireContext())
            val rentalDao = database.rentalDao()
            val rentalEntity = RentalEntity(
                equipmentId = equipment.id,
                userId = currentUser?.userId ?: "",
                rentalDays = rentalDays,
                purpose = purpose,
                status = "대여중"
            )

            viewLifecycleOwner
                .lifecycleScope
                .launch {

                    val resultNum =
                        rentalDao.insert(
                            rentalEntity
                        )

                    Toast.makeText(
                        requireContext(),
                        "DB 저장 완료 : 신청번호 $resultNum",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            val bundle = Bundle().apply {
                putInt(NavKeys.EQUIPMENT_ID, equipment.id)
                putString(NavKeys.APPLICANT_NAME, applicantName)
                putString(NavKeys.USER_LOGIN_ID, userId)
                putString(NavKeys.PURPOSE, purpose)
                putInt(NavKeys.RENTAL_DAYS, rentalDays)
            }

            findNavController().navigate(
                R.id.action_rentalFragment_to_completeFragment,
                bundle
            )
        }

        binding.btnBackRental.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
