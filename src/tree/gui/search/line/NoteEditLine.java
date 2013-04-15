package tree.gui.search.line;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

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
	
	int multi;
	

	public NoteEditLine(final Note note,final AbstractOverview view){
		super();
		this.multi = 7;
		List<String> comments = note.getComments();
		contentOne = new JLabel();
		if(!comments.isEmpty()){
			contentOne.setText(comments.get(0));
		}
		
		this.edit = new JButton(Main.getTranslator().getTranlation("editNote", Translator.OVERVIEW_JDIALOG));
		this.delete = new JButton(Main.getTranslator().getTranlation("removeNote", Translator.OVERVIEW_JDIALOG));
		
				//layout stuff
				SpringLayout layout = new SpringLayout();
				//contentOne
				layout.putConstraint(SpringLayout.WEST, this.contentOne,
						5, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.NORTH, this.contentOne, 
						5, SpringLayout.NORTH, this);
				//edit button
				layout.putConstraint(SpringLayout.WEST, this.edit,
						5, SpringLayout.EAST, this.contentOne);
				layout.putConstraint(SpringLayout.NORTH, this.edit, 
						5, SpringLayout.NORTH, this);
				
				//delete button
				layout.putConstraint(SpringLayout.WEST, this.delete,
						5, SpringLayout.EAST, this.edit);
				layout.putConstraint(SpringLayout.NORTH, this.delete, 
						5, SpringLayout.NORTH, this);
				
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
					view.filterView();
				}
				
			});
				
			this.setLayout(layout);
			this.add(contentOne);
			this.add(edit);
			this.add(delete);
				
	}
	
	
	public void setMainSize(int width){
		this.setMainSize(width,(int)this.edit.getPreferredSize().getHeight());
	}
	
	public void setMainSize(int width,int height){
		Dimension pref = new Dimension((int)(1.5*width),height);
		this.contentOne.setPreferredSize(new Dimension(width*(multi-3),height));
		this.edit.setPreferredSize(pref);
		this.delete.setPreferredSize(pref);
		
		this.setPreferredSize(new Dimension(width*multi,height));
	}

}
