package ro.pub.cs.systems.eim.practicaltest04

import android.app.Service
import android.content.Intent
import android.os.IBinder

class PracticalTest04Service : Service() {
    private var processingThread: ProcessingThread? = null
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val traseu = intent?.getStringExtra(Constants.PATH_KEY)
        val nrClicks = intent?.getIntExtra(Constants.NR_CLICKS_KEY, 0)
        if(traseu!=null && nrClicks!=null){
            processingThread?.stopThread()
            processingThread = ProcessingThread(this,traseu,nrClicks)
            processingThread?.start()
        }
        return START_REDELIVER_INTENT
    }
    override fun onDestroy(){
        processingThread?.stopThread()
        super.onDestroy()
    }
}