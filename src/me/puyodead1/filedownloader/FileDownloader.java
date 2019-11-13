package me.puyodead1.filedownloader;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class FileDownloader {
	private static final List<String> allowedExts = Arrays.asList("mp4", "webm", "mp3", "wav", "iso", "rar", "zip", "java");

	private static Text txtUrl;
	private static Text txtSavePath;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(755, 434);
		shell.setText("SWT Application");

		txtUrl = new Text(shell, SWT.BORDER);
		txtUrl.setBounds(10, 106, 719, 21);

		Label lblUrl = new Label(shell, SWT.NONE);
		lblUrl.setAlignment(SWT.CENTER);
		lblUrl.setBounds(10, 85, 719, 15);
		lblUrl.setText("URL");

		Button btnDownload = new Button(shell, SWT.NONE);
		btnDownload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (!Objects.isNull(txtSavePath.getText()) && !txtSavePath.getText().isEmpty()) {
					final String url = txtUrl.getText();
					if (!Objects.isNull(url) && !url.isEmpty()) {
						final String[] fileNameSplit = url.split("/");
						if (fileNameSplit.length > 0) {
							final String fileName = fileNameSplit[fileNameSplit.length - 1];
							final String[] fileExtSplit = fileName.split("\\.");
							if (fileExtSplit.length > 0) {
								final String fileExt = fileExtSplit[fileExtSplit.length - 1];
								if (allowedExts.contains(fileExt)) {
									final String savePath = txtSavePath.getText();
									new DownloadDialog(shell, shell.getStyle(), url, fileName, savePath);
								} else {
									final MessageDialog dialog = new MessageDialog(shell, shell.getStyle(), "Error",
											"File extension not allowed!");
									dialog.open();
								}
							} else {
								System.out.println("no file ext");
							}
						} else {
							System.out.println("no file paths");
						}
					} else {
						System.out.println("url is empty or null");
					}
				} else {
					System.out.println("save path empty or null");
				}
			}
		});
		btnDownload.setBounds(10, 202, 719, 25);
		btnDownload.setText("Download");

		txtSavePath = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.CANCEL);
		txtSavePath.setEditable(false);
		txtSavePath.setBounds(128, 175, 601, 21);

		Button btnBrowse = new Button(shell, SWT.NONE);
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell);
				dialog.setMessage("Choose Save Location");
				String dir = dialog.open();
				if (dir != null) {
					txtSavePath.setText(dir);
				}
			}
		});
		btnBrowse.setBounds(10, 175, 112, 21);
		btnBrowse.setText("Browse");

		Label lblSaveTo = new Label(shell, SWT.NONE);
		lblSaveTo.setAlignment(SWT.CENTER);
		lblSaveTo.setBounds(10, 154, 719, 15);
		lblSaveTo.setText("Save To");

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
