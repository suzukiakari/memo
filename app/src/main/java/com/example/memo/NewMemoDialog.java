package com.example.memo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class NewMemoDialog extends DialogFragment {
    EditText editText;
    public NewMemoDialog(EditText editText) {
        this.editText = editText;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if((editText.getText().toString()).equals("")) {
            builder.setMessage(R.string.dialog_empty);
        } else {
            builder.setMessage(R.string.dialog_new_memo);
        }
        builder.setPositiveButton(R.string.dialog_new_memo_ok, new DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
    @Override
    public void onClick(DialogInterface dialog, int witch) {
        //ダイアログのボタンは「OK」だけなので何もしない
        }
    }
}
