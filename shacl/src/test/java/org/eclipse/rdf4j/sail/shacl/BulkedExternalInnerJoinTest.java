package org.eclipse.rdf4j.sail.shacl;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.DCAT;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.repository.sail.SailRepositoryConnection;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.sail.shacl.mock.MockConsumePlanNode;
import org.eclipse.rdf4j.sail.shacl.mock.MockInputPlanNode;
import org.eclipse.rdf4j.sail.shacl.planNodes.BulkedExternalInnerJoin;
import org.eclipse.rdf4j.sail.shacl.planNodes.PlanNode;
import org.eclipse.rdf4j.sail.shacl.planNodes.Tuple;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BulkedExternalInnerJoinTest {

	@Test
	public void gapInResultsFromQueryTest(){

		SimpleValueFactory vf = SimpleValueFactory.getInstance();
		IRI a = vf.createIRI("http://a");
		IRI b = vf.createIRI("http://b");
		IRI c = vf.createIRI("http://c");
		IRI d = vf.createIRI("http://d");


		PlanNode left = new MockInputPlanNode(Arrays.asList(
			new Tuple(Collections.singletonList(a)),
			new Tuple(Collections.singletonList(b)),
			new Tuple(Collections.singletonList(c)),
			new Tuple(Collections.singletonList(d))
		));

		SailRepository sailRepository = new SailRepository(new MemoryStore());
		sailRepository.init();

		try (SailRepositoryConnection connection = sailRepository.getConnection()) {
			connection.add(b, DCAT.ACCESS_URL,RDFS.RESOURCE);
			connection.add(d, DCAT.ACCESS_URL,RDFS.SUBPROPERTYOF);
		}

		BulkedExternalInnerJoin bulkedExternalInnerJoin = new BulkedExternalInnerJoin(left, sailRepository, "?a <http://www.w3.org/ns/dcat#accessURL> ?c. ");

		List<Tuple> tuples = new MockConsumePlanNode(bulkedExternalInnerJoin).asList();

		tuples.forEach(System.out::println);

		assertEquals("[http://b, http://www.w3.org/2000/01/rdf-schema#Resource]", Arrays.toString(tuples.get(0).line.toArray()));
		assertEquals("[http://d, http://www.w3.org/2000/01/rdf-schema#subPropertyOf]", Arrays.toString(tuples.get(1).line.toArray()));


	}

}