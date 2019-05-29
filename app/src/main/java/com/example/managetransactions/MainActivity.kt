package com.example.managetransactions

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.service.autofill.FieldClassification
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {


    var list2:ArrayList<String> = arrayListOf()
    var list3:ArrayList<String> = arrayListOf()
    var creditAmounts:ArrayList<String> = arrayListOf()
    var debitAmounts:ArrayList<String> = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        list2 = arrayListOf()
        list3 = arrayListOf()
        creditAmounts = arrayListOf()
        debitAmounts = arrayListOf()


        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.READ_SMS),1)
        }else {


            val cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)

            if (cursor!!.moveToFirst()) { // must check the result to prevent exception
                do {
                    var msgAddress = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                    var msgData = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                    var msgDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                    list3.add(msgData+"\n"+msgDate)
                } while (cursor.moveToNext())
            } else {
                // empty box, no SMS
            }



            for (it in list3) {
                if(  it.contains("debited") and it.contains("Bal") ){
                    var p: Pattern = Pattern.compile("Bal : INR (.*?) For")
                    var m: Matcher = p.matcher(it)
                    var s = ""
                    while (m.find()) {
                        s=m.group(1)
                    }
                    avalBal.text = "Availabe Balance " +s
                    break
                }
            }


            list3.forEach {
                if (it.contains("debited")) {
                    var p: Pattern = Pattern.compile("by INR (.*?) towards")
                    var m: Matcher = p.matcher(it)
                    while (m.find()) {
                        debitAmounts.add(m.group(1))
                    }
                }
            }



            debitAmounts.forEach {
                list2.add("Debited by" + " Rs " + it)
            }




            //For Centeral Bank
            /*

            list3.forEach {
                if (it.contains("credited")) {
                    var p: Pattern = Pattern.compile("Rs.(.*?) credited")
                    var m: Matcher = p.matcher(it)
                    while (m.find()) {
                        creditAmounts.add(m.group(1))
                    }
                }
            }


            list3.forEach {
                if (it.contains("credited")) {
                    var p: Pattern = Pattern.compile("credited by Rs. (.*?) ")
                    var m: Matcher = p.matcher(it)
                    while (m.find()) {
                        creditAmounts.add(m.group(1))
                    }
                }
            }wrap_content


            list3.forEach {
                if (it.contains("debited")) {
                    var p: Pattern = Pattern.compile("debited by Rs. (.*?) ")
                    var m: Matcher = p.matcher(it)
                    while (m.find()) {
                        debitAmounts.add(m.group(1))
                    }
                }
            }


            creditAmounts.forEach {
                list2.add("Credited by" + " Rs " + it)
            }


            debitAmounts.forEach {
                list2.add("Debited by" + " Rs " + it)
            }
            */
            listt.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.READ_SMS),1)

            list2 = arrayListOf()
            list3 = arrayListOf()
            creditAmounts = arrayListOf()
            debitAmounts = arrayListOf()

            val cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)

            if (cursor!!.moveToFirst()) { // must check the result to prevent exception
                do {
                    var msgAddress = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                    var msgData = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                    var msgDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                    list3.add(msgData+"\n"+msgDate)
                } while (cursor.moveToNext())
            } else {
                // empty box, no SMS
            }



            for (it in list3) {
                if(  it.contains("debited") and it.contains("Bal:") ){
                    var p: Pattern = Pattern.compile("Bal : INR (.*?) For")
                    var m: Matcher = p.matcher(it)
                    var s = ""
                    while (m.find()) {
                        s=m.group(1)
                    }
                    avalBal.text = "Availabe Balance " +s
                    break
                }
            }


            list3.forEach {
                if (it.contains("debited")) {
                    var p: Pattern = Pattern.compile("by INR (.*?) towards")
                    var m: Matcher = p.matcher(it)
                    while (m.find()) {
                        debitAmounts.add(m.group(1))
                    }
                }
            }



            debitAmounts.forEach {
                list2.add("Debited by" + " Rs " + it)
            }




            //For Centeral Bank
            /*

            list3.forEach {
                if (it.contains("credited")) {
                    var p: Pattern = Pattern.compile("Rs.(.*?) credited")
                    var m: Matcher = p.matcher(it)
                    while (m.find()) {
                        creditAmounts.add(m.group(1))
                    }
                }
            }


            list3.forEach {
                if (it.contains("credited")) {
                    var p: Pattern = Pattern.compile("credited by Rs. (.*?) ")
                    var m: Matcher = p.matcher(it)
                    while (m.find()) {
                        creditAmounts.add(m.group(1))
                    }
                }
            }wrap_content


            list3.forEach {
                if (it.contains("debited")) {
                    var p: Pattern = Pattern.compile("debited by Rs. (.*?) ")
                    var m: Matcher = p.matcher(it)
                    while (m.find()) {
                        debitAmounts.add(m.group(1))
                    }
                }
            }


            creditAmounts.forEach {
                list2.add("Credited by" + " Rs " + it)
            }


            debitAmounts.forEach {
                list2.add("Debited by" + " Rs " + it)
            }
            */
            listt.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list2)

        }

    }
}
