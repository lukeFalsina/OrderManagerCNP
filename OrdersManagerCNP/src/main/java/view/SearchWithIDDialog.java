package view;

import java.awt.Frame;

public class SearchWithIDDialog extends ConfirmationDialog {

    private static final long serialVersionUID = 3890504635579161349L;

    public SearchWithIDDialog(Frame frame, ReturnSwingView returnSwingView) {
        super(frame, returnSwingView, true);
    }

    @Override
    protected void onConfirm() {
        setVisible(false);
        returnSwingView.sendRequestForOrderByID(new Long(textField.getText()));
    }
}
