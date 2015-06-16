//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.util.List;

import play.*;
import play.jobs.*;
import play.test.Fixtures;
import models.*;

@OnApplicationStart
public class Bootstrap extends Job 
{ 
	public void doJob() 
	{
		Fixtures.deleteDatabase();	
		Fixtures.loadModels("data.yml");


	}
}


