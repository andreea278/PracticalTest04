package ro.pub.cs.systems.eim.practicaltest04

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest04MainActivity : AppCompatActivity() {
    private lateinit var traseuEditText: EditText
    private lateinit var nrClickEditText: EditText
    private var count = 0
    private var startedService = false
    private fun startServiceIfNeeded(path:String, nrClicks:Int){
        if(nrClicks>=Constants.THRESHOLD && !startedService){
            val intent = Intent(this@PracticalTest04MainActivity,PracticalTest04Service::class.java)
            intent.putExtra(Constants.PATH_KEY,path)
            intent.putExtra(Constants.NR_CLICKS_KEY,nrClicks)
            startService(intent)
            startedService = true
        }
    }

    private val messageBroadcastReceiver = MessageBroadcastReceiver()

    private inner class MessageBroadcastReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra(Constants.DATA_KEY)

            Log.d(Constants.TAG, "${intent?.action}: $message")

        }
    }

    private val intentFilter = IntentFilter().apply{
        addAction(Constants.ACTION_1)
        addAction(Constants.ACTION_2)
        addAction(Constants.ACTION_3)
    }

    private val secondActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result->
        when(result.resultCode){
            Activity.RESULT_OK ->{
                Toast.makeText(this,"Registered",Toast.LENGTH_LONG).show()
            }

            Activity.RESULT_CANCELED ->{
                Toast.makeText(this,"Canceled",Toast.LENGTH_LONG).show()
            }
        }
        traseuEditText.setText("")
        nrClickEditText.setText("0")
    }
    private val buttonClickListener = ButtonClickListener()

    private inner class ButtonClickListener: View.OnClickListener{
        override fun onClick(view: View){
            when(view.id){
                R.id.top_button ->{
                    if(traseuEditText.text.toString().isEmpty())
                        traseuEditText.append(getString(R.string.top))
                    else{
                        traseuEditText.append("," + getString(R.string.top))
                    }
                    count++
                    nrClickEditText.setText(count.toString())
                    startServiceIfNeeded(traseuEditText.text.toString(),nrClickEditText.text.toString().toInt())
                }
                R.id.bottom_button ->{
                    if(traseuEditText.text.toString().isEmpty())
                        traseuEditText.append(getString(R.string.bottom))
                    else{
                        traseuEditText.append("," + getString(R.string.bottom))
                    }
                    count++
                    nrClickEditText.setText(count.toString())
                    startServiceIfNeeded(traseuEditText.text.toString(),nrClickEditText.text.toString().toInt())
                }
                R.id.left_button ->{
                    if(traseuEditText.text.toString().isEmpty())
                        traseuEditText.append(getString(R.string.left))
                    else{
                        traseuEditText.append("," + getString(R.string.left))
                    }
                    count++
                    nrClickEditText.setText(count.toString())
                    startServiceIfNeeded(traseuEditText.text.toString(),nrClickEditText.text.toString().toInt())
                }
                R.id.right_button ->{
                    if(traseuEditText.text.toString().isEmpty())
                        traseuEditText.append(getString(R.string.right))
                    else{
                        traseuEditText.append("," + getString(R.string.right))
                    }
                    count++
                    nrClickEditText.setText(count.toString())
                    startServiceIfNeeded(traseuEditText.text.toString(),nrClickEditText.text.toString().toInt())
                }
                R.id.navigate_to_activity_button->{
                    val intent = Intent(
                        this@PracticalTest04MainActivity,
                        PracticalTest04SecondaryActivity::class.java
                    )
                    intent.setPackage("ro.pub.cs.systems.eim.practicaltest04")
                    intent.putExtra(Constants.TRASEU_KEY,traseuEditText.text.toString())
                    secondActivityLauncher.launch(intent)

                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_practical_test04_main)
        traseuEditText = findViewById(R.id.traseu_edit_text)
        nrClickEditText = findViewById(R.id.nr_click_edit_text)

        findViewById<View>(R.id.top_button).setOnClickListener(buttonClickListener)
        findViewById<View>(R.id.bottom_button).setOnClickListener(buttonClickListener)
        findViewById<View>(R.id.left_button).setOnClickListener(buttonClickListener)
        findViewById<View>(R.id.right_button).setOnClickListener(buttonClickListener)
        findViewById<View>(R.id.navigate_to_activity_button).setOnClickListener(buttonClickListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("traseu", traseuEditText.text.toString())
        outState.putInt("count",nrClickEditText.text.toString().toInt())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        traseuEditText.setText(savedInstanceState.getString("traseu"))
        nrClickEditText.setText(savedInstanceState.getInt("count"))
        nrClickEditText.setText(count.toString())
    }
    override fun onDestroy(){
        stopService(Intent(this, PracticalTest04Service::class.java))
        super.onDestroy()
    }

    override fun onResume(){
        super.onResume()
        ContextCompat.registerReceiver(this, messageBroadcastReceiver, intentFilter, ContextCompat.RECEIVER_EXPORTED)
    }

    override fun onPause(){
        unregisterReceiver(messageBroadcastReceiver)
        super.onPause()
    }
}