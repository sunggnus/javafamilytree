package tree.gui.search.line;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;

import main.Main;
import translator.Translator;
import tree.gui.search.AbstractOverview;
import tree.gui.window.EditNoteDialog;
import tree.model.Note;

public class NoteEditLine extends AbstractLine{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2945568843261441369L;
	
	JLabel contentOne;
	
	JButton edit;
	
	JButton delete;
	
	
	

	public NoteEditLine(final Note note,final AbstractOverview view){
		super();
		this.setMulti(7);
		List<String> comments = note.getComments();
		contentOne = new JLabel();
		if(!comments.isEmpty()){
			contentOne.setText(comments.get(0));
		}
		
		this.edit = new JButton(Main.getTranslator().getTranslation("editNote", Translator.OVERVIEW_JDIALOG));
		this.delete = new JButton(Main.getTranslator().getTranslation("removeNote", Translator.OVERVIEW_JDIALOG));
		
	
			edit.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					new EditNoteDialog(note);
					view.filterView();
				}
				
			});
			
			delete.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					Main.getMainNode().removeNote(note);
					Main.getMainFrame().getCanvas().generateDrawStuff();
					view.filterView();
				}
				
			});
				
			this.add(contentOne);
			this.normalAdd(Box.createHorizontalGlue());
			this.add(edit);
			this.add(delete);
				
	}
	
	
	public void setMainSize(int width){
		this.setMainSize(width,(int)this.edit.getPreferredSize().getHeight());
	}
	


}
