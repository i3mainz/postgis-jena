package de.hsmainz.cs.semgis.arqextension.test.envelope;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.apache.jena.sparql.expr.NodeValue;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.io.ParseException;

import de.hsmainz.cs.semgis.arqextension.point.attribute.YMax;
import io.github.galbiston.geosparql_jena.implementation.GeometryWrapperFactory;
import io.github.galbiston.geosparql_jena.implementation.datatype.WKTDatatype;

public class YMaxTest {

	
	public static final String testLineString="LINESTRING(1 3 4, 5 6 7)";
	
	@Test
	public void testYMax() throws ParseException {
        YMax instance=new YMax();
        List<Coordinate> coords=new LinkedList<Coordinate>();
        coords.add(new Coordinate(1,3,4)); 
        coords.add(new Coordinate(5,6,7)); 
        NodeValue input=GeometryWrapperFactory.createLineString(coords, WKTDatatype.URI).asNodeValue();
        NodeValue expResult = NodeValue.makeDouble(6);
        NodeValue result = instance.exec(input);
        assertEquals(expResult, result);
	}
	
}
