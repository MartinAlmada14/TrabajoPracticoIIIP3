/**
 * 
 */
/**
 * 
 */
module TrabajoPracticoIII {
	requires java.desktop;
	requires junit;
	requires java.sql;
	opens Model to gson;
	opens Persistence to gson;
}