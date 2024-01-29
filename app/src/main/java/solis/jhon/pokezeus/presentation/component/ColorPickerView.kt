package solis.jhon.pokezeus.presentation.component

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.SeekBar
import solis.jhon.pokezeus.databinding.ViewColorPickerBinding

class ColorPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var onColorChangedListener: ((Int) -> Unit)? = null
    private val binding: ViewColorPickerBinding =
        ViewColorPickerBinding.inflate(LayoutInflater.from(context), this)

    init {
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            seekBarRed.setOnSeekBarChangeListener(createSeekBarChangeListener())
            seekBarGreen.setOnSeekBarChangeListener(createSeekBarChangeListener())
            seekBarBlue.setOnSeekBarChangeListener(createSeekBarChangeListener())

            btnApplyColor.setOnClickListener {
                seekBarRed.isEnabled = false
                seekBarGreen.isEnabled = false
                seekBarBlue.isEnabled = false
                onColorChangedListener?.invoke(getSelectedColor())
            }
        }
    }

    private fun createSeekBarChangeListener(): SeekBar.OnSeekBarChangeListener {
        return object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                applyColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
    }

    private fun applyColor() {
        binding.apply {
            val buttonBackground = btnApplyColor.background.mutate()
            buttonBackground.setTint(getSelectedColor())
            btnApplyColor.background = buttonBackground
        }
    }

    private fun getSelectedColor() : Int {
        binding.apply {
            val red = seekBarRed.progress
            val green = seekBarGreen.progress
            val blue = seekBarBlue.progress

            return Color.rgb(red, green, blue)

        }
    }

    fun setOnColorChangedListener(listener: (Int) -> Unit) {
        this.onColorChangedListener = listener
    }
}