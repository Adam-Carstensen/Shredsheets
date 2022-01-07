package com.bm.dialogs

class DialogResult(var selectedOption: DialogOption) {
    var cancelled: Boolean = false
}

abstract class DialogOption {
    abstract fun getDisplayText() :String
}

object OkayDialogOption : DialogOption() {
    override fun getDisplayText(): String {
        return "Okay"
    }
}

object CancelDialogOption : DialogOption() {
    override fun getDisplayText(): String {
        return "Cancel"
    }

}