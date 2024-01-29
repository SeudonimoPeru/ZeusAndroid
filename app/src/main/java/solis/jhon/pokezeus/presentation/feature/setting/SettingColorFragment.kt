package solis.jhon.pokezeus.presentation.feature.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import solis.jhon.pokezeus.R
import solis.jhon.pokezeus.databinding.FragmentSettingColorBinding

@AndroidEntryPoint
class SettingColorFragment : Fragment() {

    private var backgroundColorSelected = 0
    private var initialsColorSelected = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingColorBinding.inflate(inflater, container, false)
        setupViews(binding)
        return binding.root
    }

    private fun setupViews(binding: FragmentSettingColorBinding) {

        binding.apply {
            cpvBackgroundColor.setOnColorChangedListener { selectedColor ->
                backgroundColorSelected = selectedColor
                Log.i("Selected Color: ", "$selectedColor")
            }
            cpvInitialsColor.setOnColorChangedListener { selectedColor ->
                initialsColorSelected = selectedColor
                Log.i("Selected Color: ", "$selectedColor")
            }
            btnContinue.setOnClickListener {
                findNavController().navigate(R.id.actionSettingFragmentToPokemonListFragment, bundleOf("backgroundColor" to backgroundColorSelected, "initialsColor" to initialsColorSelected))
            }
        }
    }
}