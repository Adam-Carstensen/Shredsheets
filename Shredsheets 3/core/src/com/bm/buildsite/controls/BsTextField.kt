package com.bm.buildsite.controls

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.bm.UserInterface
import com.kotcrab.vis.ui.widget.VisTextField

class BsTextField(defaultText: String, size: Int, style: VisTextField.VisTextFieldStyle = UserInterface.getTextFieldStyle(size)) : VisTextField(defaultText, style) {

    init {

        addListener(object : InputListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {}
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                if (text == defaultText)  {
                    text = ""
                    layout()
                }


                return true
            }
        })
    }






}