package ro.pub.cs.systems.eim.practicaltest04

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat.enableEdgeToEdge

class PracticalTest04SecondaryActivity: AppCompatActivity() {

    private lateinit var traseuResultTextView: TextView

    private val buttonClickListener = ButtonClickListener()

    private inner class ButtonClickListener: View.OnClickListener{
        override fun onClick(view: View){
            when(view.id){
                R.id.register_button ->{

                    setResult(RESULT_OK)
                }
                R.id.cancel_button ->{
                    setResult(RESULT_CANCELED)
                }

            }
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test04_seond)
        traseuResultTextView = findViewById(R.id.traseu_result_text_view)
        val traseu = intent?.getStringExtra(Constants.TRASEU_KEY)
        traseuResultTextView.setText(traseu)
        findViewById<View>(R.id.register_button).setOnClickListener(buttonClickListener)
        findViewById<View>(R.id.cancel_button).setOnClickListener(buttonClickListener)
    }
}