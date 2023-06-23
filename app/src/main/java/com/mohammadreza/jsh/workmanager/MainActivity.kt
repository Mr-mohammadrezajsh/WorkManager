package com.mohammadreza.jsh.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*

import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonback.setOnClickListener {
            setOneTimeRequest()
            setPeriodicWorkRequest()
        }
    }
    fun setOneTimeRequest(){
        val constraint=Constraints
            .Builder()
           //.setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val BackupRequest =
            OneTimeWorkRequest
                .Builder(DBBackupScheduler::class.java)
                .setConstraints(constraint)
                .build()
        val shirinkRequest =
            OneTimeWorkRequest
                .Builder(ShirinkDBScheduler::class.java)
                .setConstraints(constraint)
                .build()
        val zipRequest =
            OneTimeWorkRequest
                .Builder(ZipDBDcheduler::class.java)
                .setConstraints(constraint)
                .build()
//        WorkManager.getInstance(applicationContext).enqueue(oneTimeWorkRequest)
        val workManager= WorkManager.getInstance(applicationContext)
        workManager
            .beginWith(shirinkRequest)
            .then(BackupRequest)
            .then(zipRequest)
            .enqueue()
        workManager.getWorkInfoByIdLiveData(BackupRequest.id)
            .observe(this, Observer {
                textView.text=textView.text.toString() + it.state.name +"\n "
            })
    }
    private fun setPeriodicWorkRequest(){
        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(DBBackupScheduler::class.java,15,TimeUnit.MINUTES).build()
        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)

    }
}