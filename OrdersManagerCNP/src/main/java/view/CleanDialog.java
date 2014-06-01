package view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CleanDialog extends ConfirmationDialog {

	private static final long serialVersionUID = -2611507120655449512L;

	public CleanDialog(Frame frame, ReturnSwingView returnSwingView) {
		super(frame, returnSwingView, false);
	}

	@Override
	protected void setOKButton() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				returnSwingView.confirmedClearOrder();
			}
		});
		
	}

}
