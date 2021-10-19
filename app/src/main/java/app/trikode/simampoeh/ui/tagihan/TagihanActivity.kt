package app.trikode.simampoeh.ui.tagihan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.trikode.simampoeh.databinding.ActivityTagihanBinding

class TagihanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTagihanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTagihanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}