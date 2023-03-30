package org.phone.pe.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.phone.pe.databinding.ActivityScratchBinding
import org.phone.pe.utils.PrefUtil
import android.util.Log
import android.view.View
import com.anupkumarpanwar.scratchview.ScratchView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

class ScratchActivity : AppCompatActivity() {
    private val binding by lazy { ActivityScratchBinding.inflate(layoutInflater) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val min = PrefUtil.getMin(this)
        val max = PrefUtil.getMax(this)
        binding.prize.text = "₹${(min..max).random()}"

        val party = Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            position = Position.Relative(0.5, 0.3),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100)
        )

        binding.idScratchCardIv.setRevealListener(object : ScratchView.IRevealListener {
            override fun onRevealed(scratchView: ScratchView?) {
                binding.next.visibility = View.VISIBLE
                binding.prize.visibility = View.VISIBLE
                binding.konfettiView.start(party)
            }

            override fun onRevealPercentChangedListener(scratchView: ScratchView?, percent: Float) {
                Log.e("DATA", "$percent")
            }

        })

        binding.next.setOnClickListener {
            Intent(this, CardActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }
}