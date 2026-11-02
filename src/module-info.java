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
	requires jgraphx;
	opens Model to gson;
	opens Persistence to gson;
}