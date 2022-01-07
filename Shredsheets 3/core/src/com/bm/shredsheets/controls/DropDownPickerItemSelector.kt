package com.bm.shredsheets.controls

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.bm.BuildSiteFonts
import com.bm.UserInterface
import com.bm.buildsite.controls.IDialogController
import com.bm.shredsheets.models.SessionModel
import com.kotcrab.vis.ui.util.adapter.ArrayAdapter
import com.kotcrab.vis.ui.widget.ListView
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable


class DropDownPickerItemSelector(var items: Map<Int, String>, var font: BuildSiteFonts, var controller: IDialogController, var clickListener: ListView.ItemClickListener<Pair<Int, String>>) : VisTable() {

    var itemListView: ListView<Pair<Int, String>>

    init {
        var itemArray = com.badlogic.gdx.utils.Array<Pair<Int, String>>()
        for (item in items) {
            itemArray.add(Pair(item.key, item.value))
        }

        var itemAdapter = ListViewMapAdapter(itemArray, font);

        itemListView = ListView(itemAdapter)
        itemListView.setItemClickListener(clickListener)

        itemListView.mainTable.width = Gdx.graphics.width * .33f
        itemListView.mainTable.height = Gdx.graphics.height * .66f

        add(itemListView.mainTable)

        //itemListView.scrollPane.setPosition(itemListView.mainTable.x + itemListView.mainTable.width, itemListView.mainTable.y)

        if (SessionModel.instance.debugMode) println("Making drop down item with width ${itemListView.mainTable.width} and height ${itemListView.mainTable.height}")

        this.width = itemListView.mainTable.width
        this.height = itemListView.mainTable.height
    }

}


class ListViewMapAdapter(var items: com.badlogic.gdx.utils.Array<Pair<Int, String>>, var font: BuildSiteFonts) : ArrayAdapter<Pair<Int, String>, VisTable>(items) {
    override fun createView(item: Pair<Int, String>): VisTable {

        var labelStyle = Label.LabelStyle(UserInterface.getFont(font, 36, Color.BLACK), Color.BLACK)

        val label = VisLabel(item.second, labelStyle)
        label.color = Color.BLACK // TODO: Map colors to model


        val table = VisTable()
        table.left()
        table.add(label).width(250f).height(50f)
        return table
    }


}