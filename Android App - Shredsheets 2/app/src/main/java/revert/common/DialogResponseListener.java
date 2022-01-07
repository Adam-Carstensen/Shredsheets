package revert.common;

public interface DialogResponseListener <Dialog, T> {
    void onItemClicked(Dialog sender, T item);
}
