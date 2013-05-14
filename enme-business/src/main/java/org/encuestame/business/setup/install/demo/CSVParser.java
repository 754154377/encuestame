package org.encuestame.business.setup.install.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CSVParser {

	/**
	 * @throws IOException 
	 * 
	 */
	void executeCSVDemoInstall(Integer tpvotes, Integer pollvotes, Integer surveyVotes) throws IOException;
}
