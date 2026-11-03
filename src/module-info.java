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
	requires gson;
	opens model to gson;
	opens persistence to gson;
}