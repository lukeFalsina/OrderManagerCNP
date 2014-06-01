package view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinalizeDialog extends ConfirmationDialog {

	private static final long serialVersionUID = -3215893639842856492L;

	public FinalizeDialog(Frame frame, ReturnSwingView returnSwingView) {
		super(frame, returnSwingView, false);
	}

	@Override
	protected void setOKButton() {
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				returnSwingView.sendFinalizedOrder();
			}
		});
		
	}

}
