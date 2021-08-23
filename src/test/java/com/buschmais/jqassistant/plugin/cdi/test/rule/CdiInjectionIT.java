package com.buschmais.jqassistant.plugin.cdi.test.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.buschmais.jqassistant.core.report.api.model.Result;
import com.buschmais.jqassistant.core.rule.api.model.Constraint;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.inject.BeanWithConstructorInjection;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.inject.BeanWithFieldInjection;
import com.buschmais.jqassistant.plugin.cdi.test.set.beans.inject.BeanWithSetterInjection;
import com.buschmais.jqassistant.plugin.java.test.AbstractJavaPluginIT;

import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.core.analysis.test.matcher.ConstraintMatcher.constraint;
import static com.buschmais.jqassistant.core.analysis.test.matcher.ResultMatcher.result;
import static com.buschmais.jqassistant.core.report.api.model.Result.Status.FAILURE;
import static com.buschmais.jqassistant.core.report.api.model.Result.Status.SUCCESS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Tests for CDI injection constraints.
 *
 * @author Aparna Chaudhary
 */
class CdiInjectionIT extends AbstractJavaPluginIT {

    /**
     * Verifies the constraint "cdi:BeansMustUseConstructorInjection".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void constructorInjection() throws Exception {
        scanClasses(BeanWithFieldInjection.class);
        String ruleName = "cdi:BeansMustUseConstructorInjection";
        assertThat(validateConstraint(ruleName).getStatus(), equalTo(FAILURE));

        store.beginTransaction();

        List<Result<Constraint>> constraintViolations = new ArrayList<>(reportPlugin.getConstraintResults().values());
        assertThat("Unexpected number of violated constraints", constraintViolations.size(), equalTo(1));

        Result<Constraint> result = constraintViolations.get(0);
        assertThat("Expected constraint " + ruleName, result, result(constraint(ruleName)));

        List<Map<String, Object>> violatedBeans = result.getRows();
        assertThat("Unexpected number of violations", violatedBeans.size(), equalTo(1));
        assertThat("Unexpected bean name", BeanWithFieldInjection.class.getName(), equalTo(violatedBeans.get(0).get("invalidBean")));

        store.commitTransaction();
    }

    /**
     * Verifies the constraint "cdi:BeansMustUseConstructorInjection" results in
     * no violations when applied to valid beans.
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void constructorInjection_No_Violation() throws Exception {
        scanClasses(BeanWithConstructorInjection.class);
        String ruleName = "cdi:BeansMustUseConstructorInjection";
        assertThat(validateConstraint(ruleName).getStatus(), equalTo(SUCCESS));
        store.beginTransaction();

        List<Result<Constraint>> constraintViolations = new ArrayList<>(reportPlugin.getConstraintResults().values());
        assertThat("Unexpected number of violated constraints", constraintViolations.size(), equalTo(1));
        Result<Constraint> result = constraintViolations.get(0);
        assertThat("Expected constraint " + ruleName, result, result(constraint(ruleName)));
        List<Map<String, Object>> violatedBeans = result.getRows();
        assertThat("Unexpected number of violations", violatedBeans.size(), equalTo(0));

        store.commitTransaction();
    }

    /**
     * Verifies the constraint "cdi:BeansMustNotUseFieldInjection" results in no
     * violations when applied to beans with setter or constructor injection.
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void fieldInjection_No_Violation() throws Exception {
        scanClasses(BeanWithConstructorInjection.class);
        scanClasses(BeanWithSetterInjection.class);
        String ruleName = "cdi:BeansMustNotUseFieldInjection";
        assertThat(validateConstraint(ruleName).getStatus(), equalTo(SUCCESS));
        store.beginTransaction();

        List<Result<Constraint>> constraintViolations = new ArrayList<>(reportPlugin.getConstraintResults().values());
        assertThat("Unexpected number of violated constraints", constraintViolations.size(), equalTo(1));
        Result<Constraint> result = constraintViolations.get(0);
        assertThat("Expected constraint " + ruleName, result, result(constraint(ruleName)));
        List<Map<String, Object>> violations = result.getRows();
        assertThat("Unexpected number of violations", violations.size(), equalTo(0));

        store.commitTransaction();
    }

    /**
     * Verifies the constraint "cdi:BeansMustNotUseFieldInjection".
     *
     * @throws java.io.IOException
     *             If the test fails.
     */
    @Test
    void beanInjection() throws Exception {
        scanClasses(BeanWithFieldInjection.class);
        String ruleName = "cdi:BeansMustNotUseFieldInjection";
        assertThat(validateConstraint(ruleName).getStatus(), equalTo(FAILURE));
        store.beginTransaction();

        List<Result<Constraint>> constraintViolations = new ArrayList<>(reportPlugin.getConstraintResults().values());
        assertThat("Unexpected number of violated constraints", constraintViolations.size(), equalTo(1));
        Result<Constraint> result = constraintViolations.get(0);
        assertThat("Expected constraint " + ruleName, result, result(constraint(ruleName)));

        List<Map<String, Object>> violations = result.getRows();
        assertThat("Unexpected number of violations", violations, hasSize(1));
        assertThat("Unexpected bean name", BeanWithFieldInjection.class.getName(), equalTo(violations.get(0).get("invalidBean")));

        store.commitTransaction();
    }

}
