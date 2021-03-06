package de.hsmainz.cs.semgis.arqextension.aggregate;

import org.apache.jena.graph.Node;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.ExprList;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.expr.aggregate.Accumulator;
import org.apache.jena.sparql.expr.aggregate.AccumulatorExpr;
import org.apache.jena.sparql.expr.aggregate.Aggregator;
import org.apache.jena.sparql.expr.aggregate.AggregatorBase;
import org.apache.jena.sparql.function.FunctionEnv;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import io.github.galbiston.geosparql_jena.implementation.GeometryWrapper;

public class MaxY extends AggregatorBase {

	protected MaxY(String name, boolean isDistinct, Expr expr) {
		super("MAXY", true, expr);
		// TODO Auto-generated constructor stub
	}
	
	protected MaxY(Expr expr) {
		super("MAXY", false, expr);
	}

	@Override
	public Aggregator copy(ExprList exprs) {
		return new MaxY(exprs.get(0)) ;
	}

	@Override
	public boolean equals(Aggregator other, boolean bySyntax) {
		if ( other == null ) return false ;
        if ( this == other ) return true ; 
        if ( ! ( other instanceof MaxY ) )
            return false ;
        MaxY agg = (MaxY)other ;
        return exprList.equals(agg.exprList, bySyntax) ;
	}

	@Override
	public Accumulator createAccumulator() {
		return new AccMaxY(getExpr());
	}

	@Override
	public Node getValueEmpty() {
		return null;
	}

	@Override
	public int hashCode() {
		return getExpr().hashCode() ;
	}
	
	 private static class AccMaxY extends AccumulatorExpr
	    {
	        // Non-empty case but still can be nothing because the expression may be undefined.
	        private NodeValue maxSoFar = null ;

	        public AccMaxY(Expr expr) { super(expr, false) ; }

	        static final boolean DEBUG = false ;

	        @Override
	        public void accumulate(NodeValue nv, Binding binding, FunctionEnv functionEnv)
	        { 
	        	GeometryWrapper geometry = GeometryWrapper.extract(nv);
	            Geometry geo=geometry.getParsingGeometry();
	            Double localmaxY=0.;
	        	for(Coordinate coord:geo.getCoordinates()) {
	            	if(localmaxY<coord.getY()) {
	            		localmaxY=coord.getY();
	            	}
	            }
	        	NodeValue localMax=NodeValue.makeDouble(localmaxY);
	        	
	            if ( maxSoFar == null )
	            {
	                maxSoFar = localMax ;
	                if ( DEBUG ) System.out.println("max: init : "+localMax) ;
	                return ;
	            }

	            int x = NodeValue.compareAlways(maxSoFar, localMax) ;
	            if ( x < 0 )
	                maxSoFar = localMax ;

	            if ( DEBUG ) System.out.println("max: "+localMax+" ==> "+maxSoFar) ;
	        }

	        @Override
	        protected void accumulateError(Binding binding, FunctionEnv functionEnv)
	        {}

	        @Override
	        public NodeValue getAccValue()
	        { return maxSoFar ; }
	    }

}
