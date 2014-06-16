package view;

import java.awt.Frame;

public class CleanDialog extends ConfirmationDialog {

    private static final long serialVersionUID = -2611507120655449512L;

    public CleanDialog(Frame frame, ReturnSwingView returnSwingView) {
        super(frame, returnSwingView, false);
    }

    @Override
    protected void onConfirm() {
        setVisible(false);
        returnSwingView.confirmedClearOrder();
    }
}
