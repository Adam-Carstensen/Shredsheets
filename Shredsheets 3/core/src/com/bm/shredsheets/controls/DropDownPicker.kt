package com.bm.shredsheets.controls

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.Align
import com.bm.BuildSiteFonts
import com.bm.buildsite.controls.IDialogController
import com.bm.resources.Textures
import com.bm.shredsheets.TextModule
import com.kotcrab.vis.ui.widget.ListView
import space.earlygrey.shapedrawer.ShapeDrawer
import kotlin.math.min

class DropDownPicker(var items: Map<Int, String>, var selectedKey: Int, var font: BuildSiteFonts, color: Color, var dialogController: IDialogController, var clickListener: ListView.ItemClickListener<Pair<Int, String>>) : Actor() {

    var shapeDrawer: ShapeDrawer? = null
    var touchDown = false
    init {
        this.color = color

        this.addListener(object : InputListener() {
            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                if (touchDown) {

                    println("Launching Drop Down Picker Dialog")

                    var dropDownPickerSelectDialog = DropDownPickerSelectDialog(dialogController, items, font, clickListener)
                    dialogController.showDialog(dropDownPickerSelectDialog)

                    touchDown = false
                }
            }

            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                touchDown = true
                return true
            }
        })

    }

    var dropDownTriangleWidthPercentage = .2f
    var dropDownMargin = 5f

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (batch == null) return
        if (shapeDrawer == null || shapeDrawer!!.batch != batch) shapeDrawer = ShapeDrawer(batch, Textures.getWhiteTexture())

        var textRectangle = Rectangle(this.x, this.y, (this.width * (1f - dropDownTriangleWidthPercentage)) - dropDownMargin, this.height)

        var itemString = items[selectedKey] ?: "KEY $selectedKey NOT FOUND"

        TextModule.DrawText(batch, textRectangle, itemString, font, Align.left, this.color)

        var dropDownRectangle = Rectangle(this.x + this.width * (1f - dropDownTriangleWidthPercentage), this.y, this.width * dropDownTriangleWidthPercentage, this.height)

        //DEBUG
        //shapeDrawer!!.rectangle(textRectangle)

        var smallestDropDownDimension = min(dropDownRectangle.height, dropDownRectangle.width)
        var midPoint = Vector2(dropDownRectangle.x + dropDownRectangle.width * .5f, dropDownRectangle.y + dropDownRectangle.height * .5f)

        var dropDownVec1 = Vector2(midPoint.x - smallestDropDownDimension * .5f, midPoint.y + smallestDropDownDimension * .5f)
        var dropDownVec2 = Vector2(midPoint.x + smallestDropDownDimension * .5f, midPoint.y + smallestDropDownDimension * .5f)
        var dropDownVec3 = Vector2(midPoint.x, midPoint.y - smallestDropDownDimension * .5f)

        var dropDownColor = Color(.3f, .6f, .9f, 1f)

        shapeDrawer!!.filledTriangle(dropDownVec1, dropDownVec2, dropDownVec3, dropDownColor)


    }



}