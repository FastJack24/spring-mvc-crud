package com.fastjack.scope;

import com.fastjack.config.TestScopeClass;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScopeController {

    private final TestScopeClass testScopeClass;
    private final ObjectFactory<TestScopeClass> objectFactory;
    private final ObjectProvider<TestScopeClass> objectProvider;

    public ScopeController(TestScopeClass testScopeClass,
                           ObjectFactory<TestScopeClass> objectFactory,
                           ObjectProvider<TestScopeClass> objectProvider) {
        this.testScopeClass = testScopeClass;
        this.objectFactory = objectFactory;
        this.objectProvider = objectProvider;
    }

    @GetMapping("/test-scope")
    public String testScope() {
        // Calls the same object, cause request scope ScopedProxyMode.TARGET_CLASS
        System.out.println(testScopeClass.getCounter());
        System.out.println(testScopeClass.getCounter());
        System.out.println(objectFactory.getObject().getCounter());
        System.out.println(objectFactory.getObject().getCounter()); // We are getting same object here
        System.out.println(objectProvider.getObject().getCounter());
        System.out.println(objectProvider.getObject().getCounter()); // We are getting same object here
        // Two types of injection points, second a little more robust, extends first
        return "test";
    }
}
