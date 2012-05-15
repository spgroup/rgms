package rgms
import org.codehaus.groovy.grails.io.support.GrailsResourceUtils;
public class DefaultValueManager {
	
	private static final filePath = GrailsResourceUtils.GRAILS_APP_DIR+"/conf/DefaultValues.properties"
        
	
	public static final Univeristy = "University"
	public static final City = "City"
	public static final Country = "Country"
	public static final Domain = "Domain"
	
	private Properties properties = null;

	private static DefaultValueManager instance = null;
	
	public DefaultValueManager(){
		properties = new Properties()
		properties.load(new FileInputStream(filePath))
//		properties.keySet()

		println(properties.get("Domain"))
		println(properties)
	}
	
	public static DefaultValueManager getInstance(){
		println()
		println("Instance=*"+instance)
		println(instance?.getProperty("University"))
		if(!instance)
			instance = new DefaultValueManager()
		
		return instance
	}
	
	public String getPropertyValue(name){
		return properties.get(name)
	}
	
	

}
