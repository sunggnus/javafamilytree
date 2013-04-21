package tree.gui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;

import main.Config;
import tree.gui.draw.DrawImage;

public class ImageLoaderDialog implements ActionListener {

	private DrawImage draw;

	public ImageLoaderDialog(DrawImage draw) {
		this.draw = draw;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser(Config.DEFAULT_PATH);
		int val = chooser.showOpenDialog(chooser);
		if (val == JFileChooser.APPROVE_OPTION) {
			try {
				String filePath = chooser.getSelectedFile().getAbsolutePath();
				String lowerCase = filePath.toLowerCase();
				if (!(lowerCase.endsWith(".jpg") || 
						lowerCase.endsWith(".jpeg") ||
						lowerCase.endsWith(".png") ||
						lowerCase.endsWith(".gif"))) {
					draw = null;
					throw new IOException("Keine gueltige Bilddatei!");
				}
				draw.setImage(filePath);

			} catch (IOException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			}

		}

	}
	
	public DrawImage getDraw(){
		return this.draw;
	}
}
