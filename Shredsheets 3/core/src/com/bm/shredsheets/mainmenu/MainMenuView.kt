package com.bm.shredsheets.mainmenu

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.bm.BuildSiteFonts
import com.bm.UserInterface
import com.bm.buildsite.controls.IDialogController
import com.bm.buildsite.controls.BsToggleButton
import com.bm.shredsheets.controls.DropDownPicker
import com.bm.shredsheets.enums.MusicKeys
import com.bm.shredsheets.models.SessionModel
import com.bm.shredsheets.models.scales.Scales
import com.kotcrab.vis.ui.widget.ListView
import com.kotcrab.vis.ui.widget.VisTable

class MainMenuView(var dialogController: IDialogController) : VisTable(true) {


    private lateinit var keyPicker: DropDownPicker
    private lateinit var scalePicker: DropDownPicker
    private lateinit var modePicker: DropDownPicker

    var instrumentButtons = ArrayList<BsToggleButton>()

    var sixStringGuitarButton: BsToggleButton

    var menuWidth = 500f
    var menuHeight = 500f

    var selectedKey: MusicKeys = SessionModel.instance.key

    init {

        add(Label("Main Menu", UserInterface.getLabelStyle(24))).height(25f).align(Align.center)

        row()
        var restoreDefaultsButton = TextButton("Restore Defaults", UserInterface.getTextButtonStyle(36))
        restoreDefaultsButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                super.clicked(event, x, y)
                SessionModel.instance.setDefaults()
                dialogController.closeAllDialogs()
            }
        })
        add(restoreDefaultsButton).width(menuWidth).height(50f)

        row()

        var keyLayout = getKeyLayout()
        add(keyLayout).height(50f).width(menuWidth)

        row()

        var scaleLayout = getScaleLayout()
        add(scaleLayout).height(50f).width(menuWidth)

        row()

        var modeLayout = getModeLayout()
        add(modeLayout).height(50f).width(menuWidth)

        row()

        add(Label("Guitar", UserInterface.getLabelStyle(24))).height(24f).align(Align.left)

        row()

        var guitarStringsLayout = VisTable()

        sixStringGuitarButton = BsToggleButton(Label("6 string", UserInterface.getLabelStyle(28)), "StringCountAndInstrument")
        guitarStringsLayout.add(sixStringGuitarButton).width(menuWidth / 3f).height(50f).align(Align.center)
        if (SessionModel.instance.stringCount == 6) sixStringGuitarButton.isChecked = true
        sixStringGuitarButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                if (sixStringGuitarButton.isChecked) {
                    SessionModel.instance.stringCount = 6
                }
            }
        })

        var sevenStringGuitarButton = BsToggleButton(Label("7 string", UserInterface.getLabelStyle(28)), "StringCountAndInstrument")
        guitarStringsLayout.add(sevenStringGuitarButton).width(menuWidth / 3f).height(50f).align(Align.center)
        if (SessionModel.instance.stringCount == 7) sevenStringGuitarButton.isChecked = true
        sevenStringGuitarButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                if (sevenStringGuitarButton.isChecked) {
                    SessionModel.instance.stringCount = 7
                }
            }
        })

        var eightStringGuitarButton = BsToggleButton(Label("8 string", UserInterface.getLabelStyle(28)), "StringCountAndInstrument")
        guitarStringsLayout.add(eightStringGuitarButton).width(menuWidth / 3f).height(50f).align(Align.center)
        if (SessionModel.instance.stringCount == 8) eightStringGuitarButton.isChecked = true
        eightStringGuitarButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                if (eightStringGuitarButton.isChecked) {
                    SessionModel.instance.stringCount = 8
                }
            }
        })

        add(guitarStringsLayout).width(menuWidth).height(50f).align(Align.left)

        row()

        add(Label("Bass", UserInterface.getLabelStyle(24))).height(24f).align(Align.left)

        row()

        var bassStringsLayout = VisTable()

        var fourStringBassButton = BsToggleButton(Label("4 string", UserInterface.getLabelStyle(28)), "StringCountAndInstrument")
        bassStringsLayout.add(fourStringBassButton).width(menuWidth / 3f).height(50f).align(Align.center)
        if (SessionModel.instance.stringCount == 4) fourStringBassButton.isChecked = true
        fourStringBassButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                if (fourStringBassButton.isChecked) {
                    SessionModel.instance.stringCount = 4
                }
            }
        })

        var fiveStringBassButton = BsToggleButton(Label("5 string", UserInterface.getLabelStyle(28)), "StringCountAndInstrument")
        bassStringsLayout.add(fiveStringBassButton).width(menuWidth / 3f).height(50f).align(Align.center)
        if (SessionModel.instance.stringCount == 5) fiveStringBassButton.isChecked = true
        fiveStringBassButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                if (fiveStringBassButton.isChecked) {
                    SessionModel.instance.stringCount = 5
                }
            }
        })

        add(bassStringsLayout).width(menuWidth).height(50f).align(Align.left)
    }

    val labelPadding = 10f

    private fun getKeyLayout(): VisTable {
        var keyLayout = VisTable()

        var keyLabel = Label("Key", UserInterface.getLabelStyle(36))
        keyLabel.setAlignment(Align.right)
        keyLayout.add(keyLabel).width(menuWidth * .34f).height(50f).colspan(1).padRight(10f)

        var keyMap = HashMap<Int, String>()
        for (keyItem in MusicKeys.OrderedKeys) {
            keyMap[keyItem.index] = keyItem.cleanName
        }

        var clickListener = ListView.ItemClickListener<Pair<Int, String>> {
            println("Getting key for id: ${it.first} - ${it.second}")
            SessionModel.instance.key = MusicKeys.fromInt(it.first)
            dialogController.closeAllDialogs()
        }

        keyPicker = DropDownPicker(keyMap, selectedKey.index, BuildSiteFonts.FreeSans_Unicode, SessionModel.instance.theme.degreeColors[0], dialogController, clickListener)
        keyLayout.add(keyPicker).height(50f).width(menuWidth * .66f).colspan(2)

        return keyLayout
    }

    private fun getScaleLayout(): VisTable {
        var scaleLayout = VisTable()
        var scaleLabel = Label("Scale", UserInterface.getLabelStyle(36))
        scaleLabel.setAlignment(Align.right)
        scaleLayout.add(scaleLabel).width(menuWidth * .34f).height(50f).colspan(1).align(Align.right).padRight(10f)

        var keyMap = HashMap<Int, String>()
        for (keyItem in Scales.values()) {
            keyMap[keyItem.value] = keyItem.name
        }

        var clickListener = ListView.ItemClickListener<Pair<Int, String>> {
            SessionModel.instance.scale = Scales.fromInt(it.first)
            dialogController.closeAllDialogs()
        }

        scalePicker = DropDownPicker(keyMap, SessionModel.instance.scale.getEnum.value, BuildSiteFonts.FreeSans_Unicode, SessionModel.instance.theme.degreeColors[0], dialogController, clickListener)
        scaleLayout.add(scalePicker).height(50f).colspan(2).width(menuWidth * .66f)

        return scaleLayout
    }

    private fun getModeLayout(): VisTable {
        var modeLayout = VisTable()
        var modeLabel = Label("Mode", UserInterface.getLabelStyle(36))
        modeLabel.setAlignment(Align.right)
        modeLayout.add(modeLabel).width(menuWidth * .34f).height(50f).colspan(1).align(Align.right).padRight(10f)

        var keyMap = HashMap<Int, String>()

        var currentScale = SessionModel.instance.scale

        for ((i, mode) in currentScale.getModeNames().withIndex()) {
            keyMap[i] = mode
        }

        var clickListener = ListView.ItemClickListener<Pair<Int, String>> {
            SessionModel.instance.scale.mode = it.first
            dialogController.closeAllDialogs()
        }

        modePicker = DropDownPicker(keyMap, SessionModel.instance.scale.mode, BuildSiteFonts.FreeSans_Unicode, SessionModel.instance.theme.degreeColors[SessionModel.instance.scale.mode], dialogController, clickListener)
        modeLayout.add(modePicker).height(50f).colspan(2).width(menuWidth * .66f)

        return modeLayout
    }


    var checkedButton: BsToggleButton? = null


    override fun act(delta: Float) {
        //maintains button group for instruments
//        var anyChecked = false
//        for (button in instrumentButtons)
//            if (button.isChecked) {
//                anyChecked = true
//                break
//            }
//
//        if (!anyChecked) sixStringGuitarButton.isChecked = true

        if (SessionModel.instance.key.noteValue != selectedKey.noteValue) {
            selectedKey = SessionModel.instance.key
            keyPicker.selectedKey = SessionModel.instance.key.noteValue

        }


        //TODO: Make these sizes dynamic
        this.width = menuWidth
        this.height = menuHeight
        super.act(delta)
    }

}