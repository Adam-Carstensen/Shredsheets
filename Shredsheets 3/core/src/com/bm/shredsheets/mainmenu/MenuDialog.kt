package com.bm.shredsheets.mainmenu

import com.bm.buildsite.controls.IDialogController
import com.bm.dialogs.ModalDialogView

class MenuDialog(controller: IDialogController) : ModalDialogView("Shredsheets Menu", MainMenuView(controller), controller) {
}