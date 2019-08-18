/*******************************************************************************
 * Copyright (c) 2019 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package org.eclipse.rdf4j.sail.shacl.AST;

import org.eclipse.rdf4j.sail.shacl.planNodes.PlanNode;

/**
 * Functional interface used to specify how a plan node should be wrapped. Used for pushing filters.
 *
 * @author Håvard Mikkelsen Ottestad
 */
public interface PlaneNodeWrapper {

	PlanNode wrap(PlanNode planNode);
}