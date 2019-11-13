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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class DownloadDialog extends Dialog {

	protected Object result;
	protected Shell shell, parent;
	protected String fileName, urlString, savePath;
	protected URL url;
	protected ProgressBar progressBar;
	protected Label lblProgress;
	protected Downloader downloader;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DownloadDialog(Shell parent, int style, String url, String fileName, String savePath) {
		super(parent, style);
		this.fileName = fileName;
		this.savePath = savePath;
		this.urlString = url;
		this.parent = parent;
		setText("Downloading...");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		try {
			this.url = new URL(urlString);
			downloader = new Downloader(this, parent, shell, this.url, savePath);
		} catch(MalformedURLException e) {
			final MessageDialog dialog = new MessageDialog(shell, getStyle(), "Error", e.getMessage());
			dialog.open();
		}
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
		shell.setSize(450, 168);
		shell.setText(getText());
		
		progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setBounds(10, 70, 424, 25);
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				downloader.setStatus(Downloader.CANCELLED);
				System.out.println("Download cancelled: " + downloader.getStatus());
			}
		});
		btnCancel.setBounds(10, 101, 424, 25);
		btnCancel.setText("Cancel");
		
		Label lblFile = new Label(shell, SWT.WRAP | SWT.CENTER);
		lblFile.setAlignment(SWT.CENTER);
		lblFile.setText("Downloading file: \n" + fileName);
		lblFile.setBounds(10, 10, 424, 36);
		
		lblProgress = new Label(shell, SWT.NONE);
		lblProgress.setAlignment(SWT.CENTER);
		lblProgress.setBounds(10, 52, 424, 15);
	}
}
