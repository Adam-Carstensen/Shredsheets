package revert.common;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import revert.shredsheets.R;
import revert.shredsheets.models.SessionModel;

public class DialogModule {

    public static Dialog GetListSelectionDialog(Context context, LayoutInflater inflater, String title, String[] items,  AdapterView.OnItemClickListener clickListener)
    {
        View convertView = inflater.inflate(R.layout.custom_list_layout, null);
        final Dialog dialog = new AlertDialog.Builder(context).setTitle(title).setView(convertView).create();

        ListView lv = convertView.findViewById(R.id.customListLayoutListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1, items);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(clickListener);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lv.setLayoutParams(new LinearLayout.LayoutParams(lp));

        dialog.getWindow().setAttributes(lp);
        return dialog;
    }


    public static <T> void BeginGetDialogSelection(Context context, LayoutInflater inflater, String title, T[] items, DialogResponseListener<Dialog, T> listener)
    {
        String[] titles = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            T item = items[i];
            titles[i] = item.toString();
        }
        BeginGetDialogSelection(context, inflater, title, items, titles, listener);
    }
//
//    public static <T> void BeginGetDialogSelection(Context context, LayoutInflater inflater, String title, T[] items, final String[] titles, final String[] descriptions, DialogResponseListener<Dialog, T> listener)
//    {
//        View dialogView = inflater.inflate(R.layout.custom_list_layout, null);
//
//        final Dialog dialog = new AlertDialog.Builder(context).setTitle(title).setView(dialogView).create();
//        final Set<T> selectedItem = new HashSet<>();
//        final T[] finalItems = items;
//        final DialogResponseListener<Dialog, T> finalListener = listener;
//
//        ListView lv = dialogView.findViewById(R.id.customListLayoutListView);
//
//        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_2, items) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView text1 = view.findViewById(android.R.id.text1);
//                TextView text2 = view.findViewById(android.R.id.text2);
//
//                text1.setText(titles[position]);
//                text2.setText(descriptions[position]);
//                return view;
//            }
//        };
//
//        lv.setAdapter(adapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                selectedItem.add(finalItems[position]);
//                dialog.dismiss();
//                finalListener.onItemClicked(dialog, (T)selectedItem.toArray()[0]);
//            }
//        });
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        lv.setLayoutParams(new LinearLayout.LayoutParams(lp));
//        dialog.getWindow().setAttributes(lp);
//        dialog.show();
//    }


    public static <T> void BeginGetDialogSelection(Context context, LayoutInflater inflater, String title, T[] items, String[] titles, DialogResponseListener<Dialog, T> listener)
    {
        View convertView = inflater.inflate(R.layout.custom_list_layout, null);
        final Dialog dialog = new AlertDialog.Builder(context).setTitle(title).setView(convertView).create();
        final Set<T> selectedItem = new HashSet<>();
        final T[] finalItems = items;
        final DialogResponseListener<Dialog, T> finalListener = listener;

        ListView lv = convertView.findViewById(R.id.customListLayoutListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1, titles);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedItem.add(finalItems[position]);
                dialog.dismiss();
                finalListener.onItemClicked(dialog, (T)selectedItem.toArray()[0]);
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lv.setLayoutParams(new LinearLayout.LayoutParams(lp));
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T> void ShowDialog(Context context, String title, View view, Boolean fullScreen)
    {
        if (!fullScreen) {
            ShowDialog(context, title, view);
            return;
        }

        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                SessionModel.getInstance().invalidateViews();
            }
        });
        dialog.setTitle(title);

        Window dialogWindow = dialog.getWindow();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogWindow.getAttributes());
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        view.setLayoutParams(new LinearLayout.LayoutParams(lp));
        dialog.addContentView(view, lp);
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T> void ShowDialog(Context context, String title, View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth).setTitle(title).setView(view);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                SessionModel.getInstance().invalidateViews();
            }
        });

        final Dialog dialog = builder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(new LinearLayout.LayoutParams(lp));
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
}