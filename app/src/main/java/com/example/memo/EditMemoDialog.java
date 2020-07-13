package com.example.memo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditMemoDialog extends DialogFragment {
    private boolean editSql;
    Intent intent;
    private String text;
    public EditMemoDialog(boolean editSql, Intent intent, String text) {
        this.editSql = editSql;
        this.intent = intent;
        this.text = text;
    }
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceStatement) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(editSql == true && !text.equals("")) {
            builder.setMessage(R.string.edit_dialog);
        } else {
            if(text.equals("")) {
                builder.setMessage(R.string.dialog_empty);
            } else {
                builder.setMessage(R.string.ng_dialog);
            }
        }
        builder.setPositiveButton(R.string.dialog_memo_ok, new DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(editSql == true && !text.equals("")) {
                startActivity(intent);
            }
        }
    }
}
