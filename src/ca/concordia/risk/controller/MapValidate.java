package ca.concordia.risk.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ca.concordia.risk.model.Map;
import ca.concordia.risk.utilities.GameConstants;

/**
 * 
 * This class is used for map validation
 * @author dhruv
 */
public class MapValidate {

	/**
	 * This method validates map
	 * @param file the map file
	 * @return true if valid else false
	 * @throws FileNotFoundException handles exceptions
	 */
	public boolean validateFile(File file) throws FileNotFoundException {
		
		Scanner sc = new Scanner(file);
		boolean valid_flag = false;
		
		while (sc.hasNext()) 
		{
			String validateContinent = sc.nextLine();
			if (validateContinent.equals(GameConstants.CONTINENT_HEADER)) 
			{ 
				while (sc.hasNext()) {

					String validateCountry = sc.nextLine();
					if (validateCountry.equals(GameConstants.COUNTRIES_HEADER)) 
					{ 
					
						while (sc.hasNext())
						{
							String validateBorders = sc.nextLine();

							if (validateBorders.equals(GameConstants.BORDERS_HEADER)) 
							{ 
								valid_flag = true;
							}
						}
					}
				}
			} 
			else
			{
				valid_flag = false;
			}
		}

		sc.close();
		return valid_flag;
	}
	
	public boolean validateMap(Map map)
	{
		return true;
	}

}
