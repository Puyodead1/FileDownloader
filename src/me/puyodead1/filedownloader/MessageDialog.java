package me.puyodead1.filedownloader;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MessageDialog extends Dialog {

	protected Object result;
	protected Shell shlMessage;
	private String message;
	private String title;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public MessageDialog(Shell parent, int style, String title, String message) {
		super(parent, style);
		this.title = title;
		this.message = message;
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlMessage.open();
		shlMessage.layout();
		Display display = getParent().getDisplay();
		while (!shlMessage.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlMessage = new Shell(getParent(), getStyle());
		shlMessage.setSize(450, 165);
		shlMessage.setText(title);

		Label lblMessage = new Label(shlMessage, SWT.WRAP | SWT.CENTER);
		lblMessage.setAlignment(SWT.CENTER);
		lblMessage.setBounds(10, 10, 424, 85);
		lblMessage.setText(message);

		Button btnClose = new Button(shlMessage, SWT.NONE);
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shlMessage.close();
			}
		});
		btnClose.setBounds(10, 101, 424, 25);
		btnClose.setText("Close");

	}
}
