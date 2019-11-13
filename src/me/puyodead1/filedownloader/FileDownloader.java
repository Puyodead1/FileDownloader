package me.puyodead1.filedownloader;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.wb.swt.SWTResourceManager;

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
		Shell shlNoBsFile = new Shell();
		shlNoBsFile.setSize(755, 222);
		shlNoBsFile.setText("No BS File Downloader");

		txtUrl = new Text(shlNoBsFile, SWT.BORDER);
		txtUrl.setBounds(10, 31, 719, 21);

		Label lblUrl = new Label(shlNoBsFile, SWT.NONE);
		lblUrl.setAlignment(SWT.CENTER);
		lblUrl.setBounds(10, 10, 719, 15);
		lblUrl.setText("URL");

		Button btnDownload = new Button(shlNoBsFile, SWT.NONE);
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
									final DownloadDialog dialog = new DownloadDialog(shlNoBsFile, shlNoBsFile.getStyle(), url, fileName, savePath + File.separator + fileName);
									dialog.open();
								} else {
									final MessageDialog dialog = new MessageDialog(shlNoBsFile, shlNoBsFile.getStyle(), "Error",
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
		btnDownload.setBounds(10, 127, 719, 25);
		btnDownload.setText("Download");

		txtSavePath = new Text(shlNoBsFile, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.CANCEL);
		txtSavePath.setEditable(false);
		txtSavePath.setBounds(128, 100, 601, 21);

		Button btnBrowse = new Button(shlNoBsFile, SWT.NONE);
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shlNoBsFile);
				dialog.setMessage("Choose Save Location");
				String dir = dialog.open();
				if (dir != null) {
					txtSavePath.setText(dir);
				}
			}
		});
		btnBrowse.setBounds(10, 100, 112, 21);
		btnBrowse.setText("Browse");

		Label lblSaveTo = new Label(shlNoBsFile, SWT.NONE);
		lblSaveTo.setAlignment(SWT.CENTER);
		lblSaveTo.setBounds(10, 79, 719, 15);
		lblSaveTo.setText("Save To");
		
		Label lblCopyright = new Label(shlNoBsFile, SWT.NONE);
		lblCopyright.setFont(SWTResourceManager.getFont("Segoe UI", 6, SWT.ITALIC));
		lblCopyright.setBounds(10, 168, 152, 15);
		lblCopyright.setText("Copyright 2019 Puyodead1");

		shlNoBsFile.open();
		shlNoBsFile.layout();
		while (!shlNoBsFile.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
