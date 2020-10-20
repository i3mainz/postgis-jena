package de.hsmainz.cs.semgis.arqextension.raster.editor;

import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase1;
import org.apache.sis.coverage.grid.GridCoverage;
import org.geotoolkit.image.ImageWorker;

import de.hsmainz.cs.semgis.arqextension.util.LiteralUtils;
import de.hsmainz.cs.semgis.arqextension.util.Wrapper;
import io.github.galbiston.geosparql_jena.implementation.datatype.raster.CoverageWrapper;

public class Crop extends FunctionBase1 {

	@Override
	public NodeValue exec(NodeValue v) {
		Wrapper wrapper1=LiteralUtils.rasterOrVector(v);
		GridCoverage raster=((CoverageWrapper)wrapper1).getXYGeometry();
	    ImageWorker worker=new ImageWorker(raster.render(null));
		return NodeValue.makeBoolean(worker.isTranslucent());
	}

}