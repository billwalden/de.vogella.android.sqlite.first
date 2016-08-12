package com.example.igear.devogellaandroidsqlitefirst.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.example.igear.devogellaandroidsqlitefirst.R;
import com.example.igear.devogellaandroidsqlitefirst.ui.activity.TestDatabaseActivity;

/**
 * Created by IGear on 8/11/2016.
 */
public class AddItemDialog extends DialogFragment {

    private EditText taskField;

    public static AddItemDialog getDialogInstance(){
        AddItemDialog dialog = new AddItemDialog();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        taskField = new EditText(getActivity());
        setCancelable(false);
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.alert_dialog_title)
                .setPositiveButton(R.string.alert_dialog_add,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((TestDatabaseActivity)getActivity()).doPositiveClick(taskField.getText().toString());

                            }
                        })
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((TestDatabaseActivity)getActivity()).doNegativeClick();
                            }
                        })
                .setView(taskField)
                .create();
    }
}
