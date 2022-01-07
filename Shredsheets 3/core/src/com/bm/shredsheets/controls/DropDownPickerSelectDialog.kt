package com.bm.shredsheets.controls

import com.bm.BuildSiteFonts
import com.bm.buildsite.controls.IDialogController
import com.bm.dialogs.ModalDialogView
import com.kotcrab.vis.ui.widget.ListView

class DropDownPickerSelectDialog(controller: IDialogController, items: Map<Int, String>, font: BuildSiteFonts, var clickListener: ListView.ItemClickListener<Pair<Int, String>>) : ModalDialogView("Select an Item", DropDownPickerItemSelector(items, font, controller, clickListener), controller) {
}