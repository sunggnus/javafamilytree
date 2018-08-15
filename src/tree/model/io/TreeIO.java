package tree.model.io;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.xml.stream.XMLStreamException;

import tree.gui.ExportImage;
import tree.model.AgeException;
import tree.model.LineageException;
import tree.model.MainNode;
import tree.model.Person;
import tree.model.Utils;
import tree.model.Person.Sex;

public class TreeIO {

	/**
	 * loads a tree
	 * 
	 * @param path
	 *            to the source file
	 * @return
	 * @throws IOException
	 */
	public MainNode loadTree(String path) throws IOException {
		return loadTree(new File(path));
	}
	
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

	/**
	 * loads a tree
	 * 
	 * @param file
	 *            the source file
	 * @return the loaded tree
	 * @throws IOException
	 */
	public MainNode loadTree(File file) throws IOException {
		
		if(getFileExtension(file).equals("sbt")){
		FileInputStream input = null;
		ObjectInputStream objIn = null;
		try {
			input = new FileInputStream(file);
			objIn = new ObjectInputStream(input);
			Object obj = objIn.readObject();
			if (!(obj instanceof MainNode)) {
				throw new IOException("Invalid data");
			}
			MainNode node = (MainNode) obj;

			return node;
		} catch (IOException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw new IOException("Invalid data");
		} finally {
			if (objIn != null) {
				objIn.close();
			}
			if (input != null) {
				input.close();
			}
		}
	}
		if(getFileExtension(file).equals("xml")){
			XmlReader reader = new XmlReader();
		
				MainNode node = reader.readXmlTree(file.getAbsolutePath());
				return node;
			
		}
		
		
		
			return null;
		
		
		

	}

	/**
	 * this method will write the tree to a file with the given path if the file
	 * does not exist
	 * 
	 * @param node
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public boolean writeTree(MainNode node, String path) throws IOException {
		return writeTree(node, new File(path), false);
	}

	/**
	 * this method will write the tree to a file
	 * 
	 * @param node
	 * @param path
	 * @param override
	 * @return
	 * @throws IOException
	 */
	public boolean writeTree(MainNode node, String path, boolean override)
			throws IOException {
		return writeTree(node, new File(path), override);
	}

	/**
	 * this method will write the tree to a file if the file does not exist
	 * 
	 * @param node
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public boolean writeTree(MainNode node, File file) throws IOException {
		return writeTree(node, file, false);
	}

	public boolean writeImageAs(String path, String mode, ExportImage canvas,
			boolean override) throws IOException {
		switch (mode) {
		case "jpeg":
			return this.writeImageAsJPEG(path, canvas, override);
		case "png":
			return this.writeImageAsPNG(path, canvas, override);
		default:
			return false;
		}
	}

	/**
	 * 
	 * @param node
	 * @param file
	 * @param override
	 * @return false if the file exists and the override option is not set
	 * @throws IOException
	 */
	public boolean writeTree(MainNode node, File file, boolean override)
			throws IOException {
		FileOutputStream stream = null;
		ObjectOutputStream objOut = null;
		if (file.exists() && !override) {
			return false;
		}
		node.normalizeIDs();
		
		if (file.exists()) {
			file.delete();
		}
		
		
		if(file.getAbsolutePath().endsWith(".sbt")){
		try {
			stream = new FileOutputStream(file);
			objOut = new ObjectOutputStream(stream);
			objOut.writeObject(node);
			


		} catch (IOException e) {
			throw e;
		}  finally {
			if (objOut != null) {
				objOut.flush();
				objOut.close();
			}
			if (stream != null) {
				stream.flush();
				stream.close();
			}
		}

		return true;
		}
		else{
			
			try {
				XmlBuilder.writeXMLTree(file.getAbsolutePath(), 
						node.getPersonsReference(), 
						node.getNotesReference());
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	}

	public boolean writeImageAsJPEG(String path, ExportImage canvas,
			boolean override) throws IOException {
		if (!path.endsWith(".jpg")) {
			String[] split = path.split("\\.");
			if (split.length > 1) {
				path = split[0] + ".jpg";
			} else {
				path += ".jpg";
			}

		}
		return this.writeImageAsJPEG(new File(path), canvas, override);
	}

	public boolean writeImageAsPNG(String path, ExportImage canvas,
			boolean override) throws IOException {
		if (!path.endsWith(".png")) {
			String[] split = path.split("\\.");
			if (split.length > 1) {
				path = split[0] + ".png";
			} else {
				path += ".png";
			}

		}
		return this.writeImageAsPNG(new File(path), canvas, override);
	}

	public boolean writeImageAsJPEG(File file, ExportImage canvas,
			boolean override) throws IOException {
		Iterator<ImageWriter> iter = ImageIO
				.getImageWritersByFormatName("jpeg");
		return this.writeImage(file, iter, canvas, override);
	}

	public boolean writeImageAsPNG(File file, ExportImage canvas,
			boolean override) throws IOException {

		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("png");
		return this.writeImage(file, iter, canvas, override);
	}

	public boolean writeImage(File file, Iterator<ImageWriter> iter,
			ExportImage canvas, boolean override) throws IOException {
		if ((file.exists() && !override) || canvas == null) {
			return false;
		}
		FileImageOutputStream output = new FileImageOutputStream(file);
		try {

			ImageWriter writer = (ImageWriter) iter.next();
			ImageWriteParam iwp = writer.getDefaultWriteParam();
			try {
				iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				iwp.setCompressionQuality(1.0f);
			} catch (UnsupportedOperationException e) {
				// iwp.setCompressionMode(ImageWriteParam.MODE_DEFAULT);
				System.out.println("Compression not supported.");
			}
			writer.setOutput(output);
			BufferedImage export = canvas.exportImage();
			if (export != null) {
				writer.write(export);
			} else {
				System.out.println("err export failed");
			}
			return true;
		} catch (IOException e) {
			throw e;
		} finally {
			output.close();

		}
	}

	public List<Person> loadGEDCOM5(String path) throws IOException,
			AgeException, LineageException {
		return loadGEDCOM5(new File(path));
	}

	/**
	 * reads a GEDCOM file with version 5.5.1
	 * 
	 * @version 5.5.1
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws LineageException
	 * @throws AgeException
	 */
	public List<Person> loadGEDCOM5(File file) throws IOException,
			AgeException, LineageException {

		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "ISO-8859-1"));

		LinkedList<Person> list = new LinkedList<Person>();
		// int listSize = 0;
		try {
			if (in.ready()) {
				String line = in.readLine().trim();

				while (in.ready()) {

					if (line.contains("HEAD")) {
						while (!line.trim().startsWith("0")) {
							line = in.readLine();
						}
					}
					if (line.trim().startsWith("0") && line.contains("INDI")
							&& line.contains("@")) {
						Person loadedPerson = new Person("not", "named",
								Sex.MALE);
						long id = this.extractID(line);
						loadedPerson.setID(id);
						if (in.ready()) {
							line = in.readLine();
						}
						boolean birth = false;
						boolean death = false;
						while (in.ready() && !line.trim().startsWith("0")) {

							// determine name
							if (line.contains("NAME")) {
								line = this.removeNumber(line);
								line = line.replace("NAME", "");
								if (line.contains("/")) {
									// "ISO-8859-1""UTF-8"

									// line = new String(line.getBytes("UTF-8"),
									// "ISO-8859-1");
									// line = new
									// String(line.getBytes("ISO-8859-1"),
									// "UTF-8");
									// System.out.println(line);
									String[] fullname = line.split("/");
									loadedPerson.setGivenName(fullname[0]
											.trim());
									loadedPerson.setFamilyName(fullname[1]
											.trim());
								} else {
									loadedPerson.setGivenName(line.trim());
									loadedPerson.setFamilyName("");
								}
							}
							// determine sex
							else if (line.contains("SEX")) {
								if (line.contains("F")) {
									loadedPerson.setSex(Sex.FEMALE);
								} else if (line.contains("M")) {
									loadedPerson.setSex(Sex.MALE);
								}
							}
							// determine birth date
							else if (line.contains("BIRT")) {
								birth = true;
								death = false;
							}
							// determine death date
							else if (line.contains("DEAT")) {
								birth = false;
								death = true;
							}
							// determine a date
							else if (line.contains("DATE")) {
								line = this.removeNumber(line);
								line = line.replace("DATE", "");
								GregorianCalendar date = Utils
										.stringToCalendar(line);
								if (birth) {
									loadedPerson.setBirthdate(date);
								} else if (death) {
									loadedPerson.setDeathdate(date);
								}
								birth = false;
								death = false;
							}

							line = in.readLine().trim();
						}
						list.add(loadedPerson);
						// System.out.println("Listsize=" + listSize++);
					} else if (line.startsWith("0") && line.contains("@")
							&& line.contains("FAM")) {
						while (!(line.contains("HUSB") || line.contains("WIFE") || line
								.contains("CHILD"))) {
							line = in.readLine();
						}
						long id = this.extractID(line);
						// System.out.println(line + " ID " +id );
						Person toConnect = this.getPersonByID(id, list);
						// System.out.println(line + " ID " +id + " Name " +
						// toConnect.toString());
						Person partner = null;
						while (in.ready() && !line.startsWith("0")) {
							line = in.readLine();
							if (line.contains("@I")) {
								id = this.extractID(line);
								if (line.contains("HUSB")
										|| line.contains("WIFE")) {
									partner = this.getPersonByID(id, list);
									// System.out.println("Partner ID " + id
									// +partner.toString());
									toConnect.addPartner(partner);

								} else if (line.contains("CHIL")) {
									// System.out.println("CHILD ID " + id );
									Person child = this.getPersonByID(id, list);
									// System.out.println("CHILD ID " + id
									// +child.toString());
									toConnect.addChild(child);
									if (partner != null) {
										partner.addChild(child);
									}
								}
							}
						}
					} else if (in.ready()) {
						line = in.readLine();
					}

				}

			}

			// do some basic x positioning
			int x = 2;
			for (Person person : list) {
				person.setXPosition(x, false);
				x += 2;
			}

			return list;
		} catch (NumberFormatException e) {
			throw new IOException("Invalid Data" + e.getMessage());
		} finally {
			in.close();
		}

	}

	private String removeNumber(String str) {
		for (int i = 0; i < 10; i++) {
			if (str.trim().startsWith((String.valueOf(i)))) {
				str = str.replaceFirst(String.valueOf(i), "");
			}
		}
		return str;
	}

	private long extractID(String line) {
		String[] strID = line.split("@");
		long id = Long.parseLong(strID[1].trim().replace("I", "").trim());
		return id;
	}

	private Person getPersonByID(long id, List<Person> persons) {
		for (Person person : persons) {
			if (person.getID() == id) {
				return person;
			}
		}
		return null;
	}

}
