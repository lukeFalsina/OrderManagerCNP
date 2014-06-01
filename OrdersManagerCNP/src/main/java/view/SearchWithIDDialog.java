package view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchWithIDDialog extends ConfirmationDialog {

	private static final long serialVersionUID = 3890504635579161349L;

	public SearchWithIDDialog(Frame frame, ReturnSwingView returnSwingView) {
		super(frame, returnSwingView, true);
	}

	@Override
	protected void setOKButton() {
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				returnSwingView.sendRequestForOrderByID(new Long(textField.getText()));
			}
		});
	}

}
