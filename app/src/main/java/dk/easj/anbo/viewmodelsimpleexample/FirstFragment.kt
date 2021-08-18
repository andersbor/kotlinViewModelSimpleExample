package dk.easj.anbo.viewmodelsimpleexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dk.easj.anbo.viewmodelsimpleexample.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null

    // https://developer.android.com/topic/libraries/architecture/viewmodel
    private val viewModel: MyViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGreet.setOnClickListener {
            val name = binding.editTextName.text.trim().toString()
            if (name.isEmpty()) {
                binding.editTextName.error = "No name"
                return@setOnClickListener
            }
            val ageStr = binding.editTextAge.text.trim().toString()
            if (ageStr.isEmpty()) {
                binding.editTextAge.error = "No age"
                return@setOnClickListener
            }
            viewModel.name.value = name
            viewModel.age.value = ageStr.toInt()
        }

        viewModel.name.observe(viewLifecycleOwner, { name ->
            binding.textviewHello.text = "Hello $name"
        })

        viewModel.age.observe(viewLifecycleOwner, { age ->
            binding.textviewHello.text = "Age $age"
        })

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}