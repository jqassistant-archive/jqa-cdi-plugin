package com.buschmais.jqassistant.plugin.cdi.test;

import java.io.IOException;
import java.util.List;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.alternative.AlternativeBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.decorator.DecoratorBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.inject.DefaultBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.inject.NewBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.qualifier.CustomQualifier;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.qualifier.NamedBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.scope.*;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.specializes.SpecializesBean;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.stereotype.CustomStereotype;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.plugin.java.test.matcher.FieldDescriptorMatcher.fieldDescriptor;
import static com.buschmais.jqassistant.plugin.java.test.matcher.MethodDescriptorMatcher.methodDescriptor;
import static com.buschmais.jqassistant.plugin.java.test.matcher.TypeDescriptorMatcher.typeDescriptor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Tests for the CDI concepts.
 */
class CdiIT extends AbstractJavaPluginIT {

    /**
     * Verifies the concept "cdi:Dependent".
     *
     * @throws IOException
     *             If the test fails.
     */
    @Test
    void dependent() throws Exception {
        scanClasses(DependentBean.class);
        assertThat(applyConcept("cdi:Dependent").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:Dependent) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(DependentBean.class)));
        assertThat(column, hasItem(methodDescriptor(DependentBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(DependentBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:RequestScoped".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void requestScoped() throws Exception {
        scanClasses(RequestScopedBean.class);
        assertThat(applyConcept("cdi:RequestScoped").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:RequestScoped) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(RequestScopedBean.class)));
        assertThat(column, hasItem(methodDescriptor(RequestScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(RequestScopedBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:SessionScoped".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void sessionScoped() throws Exception {
        scanClasses(SessionScopedBean.class);
        assertThat(applyConcept("cdi:SessionScoped").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:SessionScoped) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(SessionScopedBean.class)));
        assertThat(column, hasItem(methodDescriptor(SessionScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(SessionScopedBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:ConversationScoped".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void conversationScoped() throws Exception {
        scanClasses(ConversationScopedBean.class);
        assertThat(applyConcept("cdi:ConversationScoped").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:ConversationScoped) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(ConversationScopedBean.class)));
        assertThat(column, hasItem(methodDescriptor(ConversationScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(ConversationScopedBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:ApplicationScoped".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void applicationScoped() throws Exception {
        scanClasses(ApplicationScopedBean.class);
        assertThat(applyConcept("cdi:ApplicationScoped").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:ApplicationScoped) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(ApplicationScopedBean.class)));
        assertThat(column, hasItem(methodDescriptor(ApplicationScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(ApplicationScopedBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:SingletonScoped".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void singletonScoped() throws Exception {
        scanClasses(SingletonScopedBean.class);
        assertThat(applyConcept("cdi:SingletonScoped").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:SingletonScoped) RETURN e").getColumn("e");
        assertThat("Expected SingletonScoped bean", column, hasItem(typeDescriptor(SingletonScopedBean.class)));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Stereotype".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void stereotype() throws Exception {
        scanClasses(CustomStereotype.class);
        assertThat(applyConcept("cdi:Stereotype").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (s:Cdi:Stereotype) RETURN s").getColumn("s");
        assertThat(column, hasItem(typeDescriptor(CustomStereotype.class)));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Alternative".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void alternative() throws Exception {
        scanClasses(AlternativeBean.class);
        assertThat(applyConcept("cdi:Alternative").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (a:Cdi:Alternative) RETURN a").getColumn("a");
        assertThat(column, hasItem(typeDescriptor(AlternativeBean.class)));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Specializes".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void specializes() throws Exception {
        scanClasses(SpecializesBean.class);
        assertThat(applyConcept("cdi:Specializes").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:Specializes) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(SpecializesBean.class)));
        assertThat(column, hasItem(methodDescriptor(SpecializesBean.class, "doSomething")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Qualifier".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void qualifier() throws Exception {
        scanClasses(CustomQualifier.class);
        assertThat(applyConcept("cdi:Qualifier").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        assertThat(query("MATCH (e:Type:Cdi:Qualifier) RETURN e").getColumn("e"), hasItem(typeDescriptor(CustomQualifier.class)));
        assertThat(query("MATCH (q:Qualifier)-[:DECLARES]->(a:Cdi:Method:Nonbinding) RETURN a").getColumn("a"),
                hasItem(methodDescriptor(CustomQualifier.class, "nonBindingValue")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Produces".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void produces() throws Exception {
        scanClasses(ApplicationScopedBean.class, ConversationScopedBean.class, DependentBean.class, RequestScopedBean.class, SessionScopedBean.class);
        assertThat(applyConcept("cdi:Produces").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (p)-[:PRODUCES]->({fqn:'java.lang.String'}) RETURN p").getColumn("p");
        assertThat(column, hasItem(methodDescriptor(ApplicationScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(ApplicationScopedBean.class, "producerField")));
        assertThat(column, hasItem(methodDescriptor(ConversationScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(ConversationScopedBean.class, "producerField")));
        assertThat(column, hasItem(methodDescriptor(DependentBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(DependentBean.class, "producerField")));
        assertThat(column, hasItem(methodDescriptor(RequestScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(RequestScopedBean.class, "producerField")));
        assertThat(column, hasItem(methodDescriptor(SessionScopedBean.class, "producerMethod")));
        assertThat(column, hasItem(fieldDescriptor(SessionScopedBean.class, "producerField")));
        store.commitTransaction();
    }

    /**
     * Verifies the uniqueness of concept "cdi:Produces" with keeping existing
     * properties.
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void producesUnique() throws Exception {
        scanClasses(ApplicationScopedBean.class);
        store.beginTransaction();
        // create existing relations with and without properties
        assertThat(query("MATCH (m:Method {name: 'producerMethod'}), (t {fqn:'java.lang.String'}) MERGE (m)-[r:PRODUCES {prop: 'value'}]->(t) RETURN r")
                .getColumn("r").size(), equalTo(1));
        assertThat(query("MATCH (f:Field {name: 'producerField'}), (t {fqn:'java.lang.String'}) MERGE (f)-[r:PRODUCES]->(t) RETURN r").getColumn("r").size(),
                equalTo(1));
        verifyUniqueRelation("PRODUCES", 2);
        store.commitTransaction();
        assertThat(applyConcept("cdi:Produces").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        verifyUniqueRelation("PRODUCES", 2);
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Disposes".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void disposes() throws Exception {
        scanClasses(DisposesBean.class);
        assertThat(applyConcept("cdi:Disposes").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        assertThat(query("MATCH (p:Parameter)-[:DISPOSES]->(disposedType:Type) RETURN disposedType").getColumn("disposedType"),
                hasItem(typeDescriptor(String.class)));
        store.commitTransaction();
    }

    /**
     * Verifies the uniqueness of concept "cdi:Disposes" with keeping existing
     * properties.
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void disposesUnique() throws Exception {
        scanClasses(DisposesBean.class);
        store.beginTransaction();
        // create existing relation with property
        assertThat(query("MATCH (p:Parameter), (t {fqn:'java.lang.String'}) MERGE (p)-[r:DISPOSES {prop: 'value'}]->(t) RETURN r").getColumn("r").size(),
                equalTo(1));
        verifyUniqueRelation("DISPOSES", 1);
        store.commitTransaction();
        assertThat(applyConcept("cdi:Disposes").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        verifyUniqueRelation("DISPOSES", 1);
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Named".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void named() throws Exception {
        scanClasses(NamedBean.class);
        assertThat(applyConcept("cdi:Named").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:Named) RETURN e").getColumn("e");
        assertThat(column, hasItem(typeDescriptor(NamedBean.class)));
        assertThat(column, hasItem(methodDescriptor(NamedBean.class, "getValue")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Any".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void any() throws Exception {
        scanClasses(DecoratorBean.class);
        assertThat(applyConcept("cdi:Any").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:Any) RETURN e").getColumn("e");
        assertThat(column, hasItem(fieldDescriptor(DecoratorBean.class, "delegate")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:New".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void newQualifier() throws Exception {
        scanClasses(NewBean.class);
        assertThat(applyConcept("cdi:New").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:New) RETURN e").getColumn("e");
        assertThat(column, hasItem(fieldDescriptor(NewBean.class, "bean")));
        store.commitTransaction();
    }

    /**
     * Verifies the concept "cdi:Default".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void defaultQualifier() throws Exception {
        scanClasses(DefaultBean.class);
        assertThat(applyConcept("cdi:Default").getStatus(), equalTo(Result.Status.SUCCESS));
        store.beginTransaction();
        List<Object> column = query("MATCH (e:Cdi:Default) RETURN e").getColumn("e");
        assertThat(column, hasItem(fieldDescriptor(DefaultBean.class, "bean")));
        store.commitTransaction();
    }

    /**
     * Verifies a unique relation with property. An existing transaction is
     * assumed.
     *
     * @param relationName
     *            The name of the relation.
     * @param total
     *            The total of relations with the given name.
     */
    private void verifyUniqueRelation(String relationName, int total) {
        assertThat(query("MATCH ()-[r:" + relationName + " {prop: 'value'}]->() RETURN r").getColumn("r").size(), equalTo(1));
        assertThat(query("MATCH ()-[r:" + relationName + "]->() RETURN r").getColumn("r").size(), equalTo(total));
    }
}
