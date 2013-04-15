package tree.gui.field;

import java.awt.Component;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class TextAreaField extends AbstractField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4751078272198894857L;
	
	private static int DEFAULT_JTEXTAREA_LINE_HEIGHT = 18;
	/**
	 * 
	 * @param name
	 * @param labelWidth
	 * @param areaWidth
	 */
	public TextAreaField(String name, int labelWidth, int areaWidth) {

		super(name, labelWidth, new JTextArea());
		final JTextArea content = this.getField();
		content.setPreferredSize(new Dimension((int) areaWidth,
				(int) content.getPreferredSize().getHeight()));
		content.setLineWrap(true);
		final TextAreaField thisField = this;
		
		content.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				Dimension pref = content.getPreferredSize();
				int newHeight = (content.getLineCount()*DEFAULT_JTEXTAREA_LINE_HEIGHT)+4;
				content.setPreferredSize(new Dimension((int)pref.getWidth(),newHeight));
				
				thisField.setPreferredSize(new Dimension((int) thisField
						.getPreferredSize().getWidth(), newHeight
						+ AbstractField.Y_OFFSET_SIZE));
				thisField.setMaximumSize(thisField.getPreferredSize());
				
				if(thisField.getParent()!=null){
					int height = 0;
					for(Component comp : thisField.getParent().getComponents()){
						height += (int) comp.getPreferredSize().getHeight();
					}
					Dimension dim = thisField.getParent().getPreferredSize();
					Dimension newDimension = new Dimension((int)dim.getWidth(),height+20);
					thisField.getParent().setPreferredSize(newDimension);
					
				
				}
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				this.removeUpdate(arg0);

			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				this.removeUpdate(arg0);
			}
		});

	}

	private JTextArea getField() {
		return (JTextArea) super.getFieldComponent();
	}

	

	public List<String> getContent() {

		LinkedList<String> content = new LinkedList<String>();
		JTextArea area = this.getField();
		try {
			for (int i = 0; i < area.getLineCount(); i++) {
				int offset = area.getLineStartOffset(i);
				int len = area.getLineEndOffset(i) - offset;

				String line = area.getText(offset, len);
				content.add(line);
				System.out.println("first loop");

			}
		} catch (BadLocationException ble) {
	
		}
		for(String str : content){
			System.out.println(str);
		}
		
		return content;
	}
	
	public void setContent(List<String> content){
		JTextArea area = this.getField();
	
		for(String str : content){
			
				area.append(str);
			
		}
		
	}
}
