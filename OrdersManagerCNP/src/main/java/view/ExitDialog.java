package view;

import java.awt.Frame;

public class ExitDialog extends ConfirmationDialog {

    private static final long serialVersionUID = -2466707824157163858L;

    public ExitDialog(Frame frame, ReturnSwingView returnSwingView) {
        super(frame, returnSwingView, false);
    }

    @Override
    protected void onConfirm() {
        setVisible(false);
        returnSwingView.confirmedExit();
    }
}
