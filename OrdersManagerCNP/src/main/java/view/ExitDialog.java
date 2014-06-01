package view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitDialog extends ConfirmationDialog {

	private static final long serialVersionUID = -2466707824157163858L;
	
	public ExitDialog(Frame frame, ReturnSwingView returnSwingView) {
		super(frame, returnSwingView, false);
	}

	@Override
	protected void setOKButton() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				returnSwingView.confirmedExit();
			}
		});
		
	}

}
