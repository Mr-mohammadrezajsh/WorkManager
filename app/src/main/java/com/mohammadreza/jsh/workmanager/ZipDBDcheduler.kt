package com.mohammadreza.jsh.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class ZipDBDcheduler(context :Context,wrkerParameters: WorkerParameters):Worker(context,wrkerParameters){
    override fun doWork(): Result {
        return try {
            for (i in 0..50000)
                Log.i("MYLOG","ZIP DATA #${i}")
            Result.success()
        }catch (e:Exception){
            Result.failure()
        }

    }
}