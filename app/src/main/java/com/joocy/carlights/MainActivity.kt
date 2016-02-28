package com.joocy.carlights

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.ToggleButton
import com.firebase.client.Firebase
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {

    var firebaseRef: Firebase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Firebase.setAndroidContext(this)
        firebaseRef = Firebase("https://burning-fire-2134.firebaseio.com/")
        setupGui()
    }

    private fun setupGui() {
        setupIndicator(find<ToggleButton>(R.id.toggleLeftIndicator), "left")
        setupIndicator(find<ToggleButton>(R.id.toggleRightIndicator), "right")
        setupBrakes(find<Button>(R.id.brakeLights))
    }

    private fun setupIndicator(indicatorToggle: ToggleButton, childName: String) {
        indicatorToggle.setOnCheckedChangeListener { button, isChecked ->
            firebaseRef?.child("indicator")?.child(childName)?.setValue(stateLabelFor(isChecked));
        }
    }

    private fun setupBrakes(brakeLightsButton: Button) {
        brakeLightsButton.setOnTouchListener { view, motionEvent ->
            firebaseRef?.child("brakelights")?.setValue(stateLabelFor(motionEvent.action === MotionEvent.ACTION_DOWN))
            false;
        }
    }

    private fun stateLabelFor(flag: Boolean): String = if (flag) "on" else "off"
}
