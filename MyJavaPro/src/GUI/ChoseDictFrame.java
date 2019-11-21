package GUI;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

import Constant.Constant;

public class ChoseDictFrame {

	private JFrame chooseFrame;

	public ChoseDictFrame(JFrame frame) {
		chooseFrame = new JFrame("选择路径");
		chooseFrame.setLocation(frame.getLocation());
		chooseFrame.setVisible(false);
		chooseFrame.setIconImage(Constant.iconFrame);

	}
	
	public File createAndShowGUI() {
		File file = createFileChooser(chooseFrame);
		return file;
	}
	
	private File createFileChooser(JFrame f) {
		File file = new File("");
		JFileChooser fileChooser  = new JFileChooser();
		fileChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.setDialogTitle("设置下载路径");
		fileChooser.setApproveButtonText("确定");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = fileChooser.showOpenDialog(f);
		if (JFileChooser.APPROVE_OPTION == result) {
			file = fileChooser.getSelectedFile();
		}
		chooseFrame.dispose();
		chooseFrame = null;
		return file;
	}
	
}
