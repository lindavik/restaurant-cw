import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class MenuScanner {
	private MenuItemMap menuEntries;
	//String array containing all the valid MenuItem categories.
	private static final String [] categories = new String[]{"Starter","Main", "Side", "Dessert", "Drink"};
	

    public MenuScanner()  {
    	//Initialises empty Treemap of menu items.
        menuEntries = new MenuItemMap();
        
        BufferedReader buff = null;
        String data [] = new String[4];
        try {
        	File file = new File("src/MenuInput.txt");
        	buff = new BufferedReader(new FileReader(file));
	    	String inputLine = buff.readLine();  //Reads first line.
	    	int line_count=0; //Keeps track of lines for errors.
	    	while(inputLine != null){  
	    		line_count++;
	    		try {
	    			//Splits line into parts.
	    			data  = inputLine.split(";");
	    			for (int i=0; i<4; i++){
	    				data[i] = data[i].trim();
	    			}
	    			
	    			//If price is not a number, the error will be caught.
	    			double price = Double.parseDouble(data[1]);
	    			//Checks, if the category name is valid, i.e. if it is in the String array categories.
	    			boolean valid_category = Arrays.asList(categories).contains(data[2]);
	    			
	    			if (!valid_category){
	    				//If the category, is not valid, an error message is shown.
	    					String error = "Could not add " + data[0] + ". '" + data[2] + "' is not a valid menu item category.";
	    					System.out.println(error);
	    			
	    			//Checks, if the value to be parsed is a boolean.
	    			} else if (data[3].toLowerCase().equals("true")||data[3].toLowerCase().equals("false")){
	    				boolean is_veg = Boolean.parseBoolean(data[3]);
	    				//Creates a MenuItem object.
	    				MenuItem m = new MenuItem(data[0], price, data[2], is_veg);
	    				//Adds the newly created MenuItem to the MenuItemMap menuEntries.
	    				menuEntries.addItem(m);
	    				
	    			} else {
	    			System.out.println("Invalid boolean data format in line " + line_count + ".");
	    			} 
	    			//Reads the next line.
	    			inputLine = buff.readLine();
	    		
	    		}catch (NumberFormatException nfe) {
	    			System.out.println("Error adding '" + data[0] + "'. Price '" +data[1]+ "' is not a number.");
	    			inputLine = buff.readLine();
	    		} catch (ArrayIndexOutOfBoundsException aoe) {
	    			System.out.println("Not enough information on line " + line_count + " to add data.");
	    			inputLine = buff.readLine();
	    		}
	    	}
        } catch(FileNotFoundException e) {
        	System.out.println(e.getMessage());
	        System.exit(1);
	         }
	         catch (IOException e) {
	         	e.printStackTrace();
	             System.exit(1);        	
	         }	    		
        finally  {
        	try{
        		buff.close();
        	}
        	catch (IOException ioe) {
        		ioe.printStackTrace();
        	}
        }
    }
    
    /**
     * Returns the MenuItemMap created from the scanned menu input file.
     * @return MenuItemMap containing all the scanned MenuItem-s.
     */
    public MenuItemMap getMenuEntries(){
    	return menuEntries;
    }
}

