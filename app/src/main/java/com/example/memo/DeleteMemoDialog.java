package com.example.memo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DeleteMemoDialog extends DialogFragment {
    Intent intent;
    public DeleteMemoDialog(Intent intent) {
        this.intent = intent;
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_dialog);
        builder.setPositiveButton(R.string.dialog_memo_ok, new DialogButtonClickListener());
        AlertDialog dialog = builder.create();
        return dialog;
    }
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int witch) {
           //memoアクテビティへインテント
            startActivity(intent);
        }
    }
}
