/** *****************************************************************************
 * Copyright (c) 2017 Timo Homburg, i3Mainz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD License
 * which accompanies this distribution, and is available at
 * https://directory.fsf.org/wiki/License:BSD_4Clause
 *
 * This project extends work by Ian Simmons who developed the Parliament Triple Store.
 * http://parliament.semwebcentral.org and published his work und BSD License as well.
 *
 *
 ****************************************************************************** */
package de.hsmainz.cs.semgis.arqextension.geometry.transform;

import io.github.galbiston.geosparql_jena.implementation.GeometryWrapper; import io.github.galbiston.geosparql_jena.implementation.GeometryWrapperFactory;
import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase3;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.simplify.DouglasPeuckerSimplifier;

/**
 * Returns a "simplified" version of the given geometry using the Douglas-Peucker algorithm.
 *
 */
public class Simplify extends FunctionBase3 {

    @Override
    public NodeValue exec(NodeValue arg0, NodeValue arg1, NodeValue arg2) {

        try {
            GeometryWrapper geometry = GeometryWrapper.extract(arg0);
            Geometry geom = geometry.getXYGeometry();
            double tolerance = arg1.getDouble();
            boolean preserveCollapsed = arg2.getBoolean();

            //Simplify
            DouglasPeuckerSimplifier simplifier = new DouglasPeuckerSimplifier(geom);
            simplifier.setDistanceTolerance(tolerance);
            simplifier.setEnsureValid(preserveCollapsed);

            Geometry simpleGeom = simplifier.getResultGeometry();
            GeometryWrapper simpleWrapper = GeometryWrapperFactory.createGeometry(simpleGeom, geometry.getSrsURI(), geometry.getGeometryDatatypeURI());
            return simpleWrapper.asNodeValue();

        } catch (DatatypeFormatException ex) {
            throw new ExprEvalException(ex.getMessage(), ex);
        }
    }

}
