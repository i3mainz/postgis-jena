package de.hsmainz.cs.semgis.arqextension.test.geometry.transform;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.apache.jena.sparql.expr.NodeValue;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import de.hsmainz.cs.semgis.arqextension.geometry.transform.DelaunayTriangles;
import io.github.galbiston.geosparql_jena.implementation.GeometryWrapperFactory;
import io.github.galbiston.geosparql_jena.implementation.datatype.WKTDatatype;

public class DelaunayTrianglesTest {

	public static final String testGeometry="POINT(0 0)";
	
	@Test
	public void testDelaunayTriangles() throws ParseException {
        DelaunayTriangles instance=new DelaunayTriangles();
        List<Coordinate> coords=new LinkedList<Coordinate>();
        coords.add(new Coordinate(0.,0.)); 
        WKTReader reader=new WKTReader();
        Geometry geom=reader.read(testGeometry);
        NodeValue tolerance=NodeValue.makeDouble(0.);
        NodeValue flags=NodeValue.makeDouble(0.);
        NodeValue input=GeometryWrapperFactory.createGeometry(geom, WKTDatatype.URI).asNodeValue();
        NodeValue expResult = GeometryWrapperFactory.createPoint(coords.get(0), WKTDatatype.URI).asNodeValue();
        NodeValue result = instance.exec(input,tolerance,flags);
        assertEquals(expResult, result);
	}
	
}
