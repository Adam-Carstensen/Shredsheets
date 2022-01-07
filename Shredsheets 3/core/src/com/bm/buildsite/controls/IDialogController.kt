package com.bm.buildsite.controls

import com.bm.dialogs.ModalDialogView
import java.util.*

interface IDialogController {
    var activeDialogs: Stack<ModalDialogView>

    ///close uppermost dialog
    fun closeDialog()
    fun closeDialog(dialog: ModalDialogView)
    fun showDialog(dialog: ModalDialogView)

    fun closeAllDialogs()

}