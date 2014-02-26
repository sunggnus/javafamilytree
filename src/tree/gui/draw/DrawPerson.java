package tree.gui.draw;

import java.awt.Graphics2D;

import main.Config;
import main.Main;
import main.OptionList;
import translator.Translator;
import tree.model.Person;
import tree.model.Utils;

public class DrawPerson extends AbstractDraw {

	private Person person;

	private int xMarging;
	private int yMarging;

	static private int OFFSET = 20;

	public DrawPerson(int xMarging, int yMarging, Person person) {
		super();
		this.xMarging = xMarging;
		this.yMarging = yMarging;
		this.person = person;

	}

	public void calculateBounds(int widthUnit, int heightUnit, double scaling) {
		// the most right painted point won't have a x-coordinate bigger than
		// maxX
		this.setMaxX((int) ((this.person.getHalfXPosition() + 1) * scaling
				* widthUnit * 1.1 + (DEFAULT_MARGING + OFFSET) * scaling));
		// the most down painted point won't have a y-coordinate bigger than
		// maxY
		this.setMaxY((int) ((this.person.getGeneration()) * scaling
				* heightUnit * 1.3 + 2 * DEFAULT_MARGING * scaling + heightUnit
				* scaling));

		this.setMinX((int) ((this.person.getHalfXPosition()) * scaling
				* widthUnit * 1.1));
		// the most down painted point won't have a y-coordinate bigger than
		// maxY
		this.setMinY((int) ((this.person.getGeneration()) * scaling
				* heightUnit * 1.3));
	}

	/**
	 * constructs the graphical person field
	 * 
	 * @param g
	 * @param widthUnit
	 * @param heightUnit
	 * @param scaling
	 * @param drawXPosition
	 */
	@Override
	public void draw(Graphics2D g, int widthUnit, int heightUnit,
			double scaling, boolean drawXPosition) {
		
		if(!this.person.isVisible()){
			return; //the person will not be drawn if she is not visible
		}

		this.setScaling(scaling);
		scaling = 1.0; // by now resizing is done with the graphics only thing
						// to do ist
		// to calculate the positions for the mouse listener

		int x = (int) (this.person.getHalfXPosition() * scaling * widthUnit * 1.1);
		int y = (int) (this.person.getGeneration() * scaling * heightUnit * 1.3 + DEFAULT_MARGING);

		// clear draw area

		g.clearRect(x, y, widthUnit, heightUnit);

		this.calculateBounds(widthUnit, heightUnit, this.getScaling());

		int fatherx = 0;
		int motherx = 0;
		int parentx = 0;
		double half = 0.5;
		
		
		
		if (this.person.getFather() != null && this.person.getFather().isVisible() ) {
			fatherx = (int) (this.person.getFather().getHalfXPosition()
					* scaling * widthUnit * 1.1);
		}
		if (this.person.getMother() != null && this.person.getMother().isVisible()) {
			motherx = (int) (this.person.getMother().getHalfXPosition()
					* scaling * widthUnit * 1.1);
		}
		if (fatherx > 0 && motherx > 0) {
			parentx = (int) Math
					.ceil((fatherx + motherx + scaling * widthUnit) / 2.0);
		} else {
			parentx = (int) (fatherx + motherx + scaling * widthUnit * 0.5);
		}

		int lowYmarging = y;
		// draw married rectangle
		if (this.person.hasOrHadPartner()) {
			lowYmarging -= (int) Math.ceil(DEFAULT_MARGING * scaling);
			// draw rect
			// first determine edge x-positions
			int ownX = (int) (this.person.getHalfXPosition() * scaling
					* widthUnit * 1.1);
			int maxX = ownX;
			int minX = ownX;
			for (Person partner : this.person.getPartners()) {
				
				if(!partner.isVisible()){
					continue; //only visible partners are relevant for the calculation
				}
				
				int personX = (int) (partner.getHalfXPosition() * scaling
						* widthUnit * 1.1);
				if (personX > maxX) {
					maxX = personX;
				}
				if (personX < minX) {
					minX = personX;
				}
			}

			// then draw rectangle
			g.drawRect(
					(int) (minX - scaling * DEFAULT_MARGING),
					(int) (y - scaling * DEFAULT_MARGING),
					(int) (scaling * ((maxX - minX) + widthUnit * 1.1)),
					(int) (scaling * heightUnit + 2 * scaling * DEFAULT_MARGING));

		}

		// determine line end
		if ((this.person.getFather() != null && this.person.getFather().isVisible()) || //not null and visible!
				(this.person.getMother() != null && this.person.getMother().isVisible())) {
			int parenty = 0;
			int parentGeneration = 0;
			
			if(this.person.getFather() != null)
				parentGeneration = person.getFather().getGeneration() + 1;
			else
				parentGeneration =person.getMother().getGeneration()+1;
			
			
			
			
			parenty =(int) (parentGeneration * scaling * heightUnit * 1.3 )+ DEFAULT_MARGING;
			
			int upperYmarging = (int) (parenty - scaling * heightUnit * 0.3);
			if (this.person.hasTwoParents()
					|| (this.person.getMother() != null && this.person
							.getMother().hasOrHadPartner())
					|| (this.person.getFather() != null && this.person
							.getFather().hasOrHadPartner())) {
				upperYmarging += (int) Math.ceil(DEFAULT_MARGING * scaling);
			}
			
			
			if(Config.TREE_ORDERING_MODE == OptionList.TREE_ORDERING_YOUNGEST_ON_TOP){
				
				lowYmarging = (int) (y + scaling * heightUnit );
				upperYmarging =(int) (parenty - scaling * heightUnit * 1.3);
				if(this.person.hasOrHadPartner())
					lowYmarging += (int) Math.ceil(DEFAULT_MARGING * scaling);
				
				
				if (this.person.hasTwoParents()
						|| (this.person.getMother() != null && this.person
								.getMother().hasOrHadPartner())
						|| (this.person.getFather() != null && this.person
								.getFather().hasOrHadPartner())) {
				
				upperYmarging -= (int) Math.ceil(DEFAULT_MARGING * scaling);
				}
			}
			
			//draw connections
				
			
			if (Config.CONNECTION_MODE.equals(OptionList.DIAGONAL_CONNECTION)) {
				g.drawLine(parentx, upperYmarging, (int) (x + scaling
						* widthUnit * half), lowYmarging);
			} else if (Config.CONNECTION_MODE
					.equals(OptionList.RECTANGLE_CONNECTION)) {
				// vertical
				g.drawLine(parentx, upperYmarging, parentx, upperYmarging
						+ STRING_HEIGHT);
				// horizontal

				g.drawLine(parentx, upperYmarging + STRING_HEIGHT,
						(int) (x + scaling * widthUnit * half), upperYmarging
								+ STRING_HEIGHT);
				// vertical
				g.drawLine((int) (x + scaling * widthUnit * half),
						upperYmarging + STRING_HEIGHT, (int) (x + scaling
								* widthUnit * half), lowYmarging);
			}
		}

		// the other stuff should not change its color
		this.startDraw(g);

		int innerX = x + (int) Math.ceil(xMarging * scaling);

		g.drawRect(x, y, (int) (scaling * widthUnit),
				(int) (scaling * heightUnit));

		this.setRow(y + (int) Math.ceil(yMarging * scaling));

		// draws the image in the upper half if a IMAGE_NORTH DisplayMode is set
		if (Config.ORIENTATION_MODE.equals(OptionList.IMAGE_NORTH_TEXT_NORTH)
				|| Config.ORIENTATION_MODE
						.equals(OptionList.IMAGE_NORTH_TEXT_SOUTH)) {
			this.drawImage(g, heightUnit, widthUnit, scaling, innerX);

		}
		// sets the writing position in the lower part of a node if a TEXT_SOUTH
		// DisplayMode is set or a picture was created
		if ((this.person.getPicture() != null && Config.ORIENTATION_MODE
				.equals(OptionList.IMAGE_NORTH_TEXT_NORTH))
				|| Config.ORIENTATION_MODE
						.equals(OptionList.IMAGE_NORTH_TEXT_SOUTH)) {
			this.setRow(this.getRow() + (int) (0.6 * heightUnit * scaling));
		} else {
			this.nextRow();
		}
		if (Config.LINE_BREAK_MODE.equals(OptionList.NAME_NO_LINE_BREAK)) { //depending on mode draws given name
			this.drawString(												//and family name in one line or
					this.person.getGivenName() + " "
							+ this.person.getFamilyName(), g, innerX);
		} else if (Config.LINE_BREAK_MODE.equals(OptionList.NAME_LINE_BREAK)) {	//in two lines
			this.drawString(this.person.getGivenName(), g, innerX);
			this.drawString(this.person.getFamilyName(), g, innerX);
		}
		
		if (this.person.getBirthName() != null
				&& !this.person.getBirthName().isEmpty()) {
			g.drawString(
					Main.getTranslator().getTranslation("birth",
							Translator.EDIT_PERSON_JDIALOG)
							+ this.person.getBirthName(), innerX,
					this.nextRow());
		}else if(Config.DATA_POSITIONING_MODE.equals(OptionList.FIXED_PERSON_DATA_POSITIONS)){
			this.nextRow(); // if the data positions are fixed draw empty line if no data exists
		}

		this.drawString(this.person.getCommentOne(), g, innerX);

		if (this.person.getBirthdate() != null) {
			g.drawString(
					"* "
							+ Utils.calendarToSimpleString(this.person
									.getBirthdate()), innerX, this.nextRow());
		}else if(Config.DATA_POSITIONING_MODE.equals(OptionList.FIXED_PERSON_DATA_POSITIONS)){
			this.nextRow(); // if the data positions are fixed draw empty line if no data exists
		}
		if (this.person.getDeathdate() != null) {
			g.drawString(
					"\u2020  "
							+ Utils.calendarToSimpleString(this.person
									.getDeathdate()), innerX, this.nextRow());
		}else if(Config.DATA_POSITIONING_MODE.equals(OptionList.FIXED_PERSON_DATA_POSITIONS)){
			this.nextRow(); // if the data positions are fixed draw empty line if no data exists
		}

		if (drawXPosition) {
			g.drawString("X: " + this.person.getXPosition(), innerX,
					this.nextRow());
			g.drawString("Y: " + this.person.getGeneration(), innerX,
					this.nextRow());
		}

		// draws the picture in the lower half if the mode is set
		if (Config.ORIENTATION_MODE.equals(OptionList.IMAGE_SOUTH_TEXT_NORTH)) {
			this.drawImage(g, heightUnit, widthUnit, scaling, innerX);
		}

		this.finishDraw(g);

	}

	private void drawString(String str, Graphics2D g, int innerX) {
		if (str != null && !str.isEmpty()) {
			g.drawString(str, innerX, this.nextRow());
		}else if(Config.DATA_POSITIONING_MODE.equals(OptionList.FIXED_PERSON_DATA_POSITIONS)){
			this.nextRow(); // if the data positions are fixed draw empty line if no data exists
		}
	}

	public Person getDrawPerson() {
		return this.person;
	}

	@Override
	public void setNewContentXCoordinate(int x) {
		this.person.setXPosition(x, false);

	}

	@Override
	public void addDistanceToXCoordinate(int dist) {
		this.person.setXPosition(dist + this.person.getXPosition(), false);

	}

	@Override
	public int getRelativeXCoordinate() {

		return this.person.getXPosition();
	}

	@Override
	public int getRelativeYCoordinate() {

		return person.getGeneration();
	}

	@Override
	public void setRelativeYCoordinate(int y) {
		if(Config.Y_POSITIONING_MODE==OptionList.Y_MANUAL_POSITIONING)
			person.setGeneration(y);
	}

	/**
	 * draws a corresponding image
	 * 
	 * @param g
	 * @param heightUnit
	 * @param widthUnit
	 * @param scaling
	 * @param innerX
	 */
	private void drawImage(Graphics2D g, int heightUnit, int widthUnit,
			double scaling, int innerX) {
		if (this.person.getPicture() != null) {
			DrawImage image = new DrawImage((int) (scaling * widthUnit * 0.9),
					(int) (0.5 * heightUnit * scaling));
			image.setImage(this.person.getPicture());
			image.paintPicture(g, innerX, this.getRow());

		} else {
			// TODO draw default image
		}
	}
	
	public boolean isVisible(){
		return this.person.isVisible();
	}
	
	public void setVisible(boolean visible){
		this.person.setVisible(visible);
	}

}
