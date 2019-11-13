package me.puyodead1.filedownloader;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ProgressBar;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

public class DownloadDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private String fileName;
	private String savePath;
	private URL url;
	private Downloader downloader;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DownloadDialog(Shell parent, int style, String url, String fileName, String savePath) {
		super(parent, style);
		this.fileName = fileName;
		this.savePath = savePath;
		setText("Downloading...");
		try {
			this.url = new URL(url);
			this.downloader = new Downloader(parent, this.url, savePath);
		} catch(MalformedURLException e) {
			final MessageDialog dialog = new MessageDialog(shell, getStyle(), "Error", e.getMessage());
			dialog.open();
		}
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
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
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 148);
		shell.setText(getText());
		
		ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setBounds(10, 52, 424, 25);
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setBounds(10, 83, 424, 25);
		btnCancel.setText("Cancel");
		
		Label lblFile = new Label(shell, SWT.WRAP | SWT.CENTER);
		lblFile.setAlignment(SWT.CENTER);
		lblFile.setText("Downloading file: \n" + fileName);
		lblFile.setBounds(10, 10, 424, 36);

	}
}
