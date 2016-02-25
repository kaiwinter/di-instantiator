package com.github.kaiwinter.instantiator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;

import com.github.kaiwinter.instantiator.testmodel.customannotation.MyInjectionAnnotation;
import com.github.kaiwinter.instantiator.testmodel.customannotation.StartingServiceWithCustomAnnotation;
import com.github.kaiwinter.instantiator.testmodel.inject.ServiceBean;
import com.github.kaiwinter.instantiator.testmodel.inject.impl.StartingServiceAsInject;
import com.github.kaiwinter.instantiator.testmodel.mock.ServiceMockBean;
import com.github.kaiwinter.instantiator.testmodel.mock.impl.StartingServiceWithMock;
import com.github.kaiwinter.instantiator.testmodel.multilevelinterface.impl.MultiLevelInterfaceImplementation;
import com.github.kaiwinter.instantiator.testmodel.multilevelinterface.impl.MultiLevelInterfaceService;
import com.github.kaiwinter.instantiator.testmodel.noimpl.impl.StartingServiceWithInterfaceWithNoImplementation;
import com.github.kaiwinter.instantiator.testmodel.twoimpl.HaveTwoImplementationsBean;
import com.github.kaiwinter.instantiator.testmodel.twoimpl.impl.Implementation1;
import com.github.kaiwinter.instantiator.testmodel.twoimpl.impl.Implementation2;
import com.github.kaiwinter.instantiator.testmodel.twoimpl.impl.StartingServiceWithInterfaceWithTwoImplementations;

public class InjectionObjectFactoryTest {

    @Test
    public void testInject() {
        StartingServiceAsInject instance = new InjectionObjectFactory().getInstance(StartingServiceAsInject.class);

        // Test if objects in test object are set
        assertNotNull(instance.getServiceBeanClass());
        assertNotNull(instance.getServiceBeanInterface());

        // Test if objects of objects in test object are set
        assertNotNull(instance.getServiceBeanClass().getDaoClass());
        assertNotNull(instance.getServiceBeanInterface().getDaoInterface());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalledWithInterface() {
        new InjectionObjectFactory().getInstance(ServiceBean.class);
    }

    @Test
    public void testInterfaceWithNoImplementation() {
        StartingServiceWithInterfaceWithNoImplementation instance = new InjectionObjectFactory()
                .getInstance(StartingServiceWithInterfaceWithNoImplementation.class);
        assertNull(instance.getBean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInterfaceWithTwoImplementations() {
        StartingServiceWithInterfaceWithTwoImplementations instance = new InjectionObjectFactory()
                .getInstance(StartingServiceWithInterfaceWithTwoImplementations.class);
        assertNotNull(instance.getBean());
    }

    @Test
    public void testInterfaceWithTwoImplementationsSetImplementingClass() {
        InjectionObjectFactory factory = new InjectionObjectFactory();
        factory.setImplementingClassForInterface(HaveTwoImplementationsBean.class, Implementation1.class);

        StartingServiceWithInterfaceWithTwoImplementations instance = factory
                .getInstance(StartingServiceWithInterfaceWithTwoImplementations.class);
        assertNotNull(instance.getBean());
        assertTrue(instance.getBean() instanceof Implementation1);
    }

    /**
     * The first parameter is not an interface.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInterfaceWithTwoImplementationsSetWrongImplementingClass1() {
        InjectionObjectFactory factory = new InjectionObjectFactory();
        factory.setImplementingClassForInterface(Implementation1.class, HaveTwoImplementationsBean.class);
    }

    /**
     * The second parameter is not a class.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInterfaceWithTwoImplementationsSetWrongImplementingClass2() {
        InjectionObjectFactory factory = new InjectionObjectFactory();
        factory.setImplementingClassForInterface(HaveTwoImplementationsBean.class, HaveTwoImplementationsBean.class);
    }

    /**
     * The class (second parameter) doesn't implement the interface (first parameter).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInterfaceWithTwoImplementationsSetWrongImplementingClass3() {
        InjectionObjectFactory factory = new InjectionObjectFactory();
        factory.setImplementingClassForInterface(HaveTwoImplementationsBean.class,
                StartingServiceWithInterfaceWithTwoImplementations.class);
    }

    @Test
    public void testInterfaceWithTwoImplementationsSetObjectForInterface() {
        InjectionObjectFactory factory = new InjectionObjectFactory();
        Implementation2 implementation2 = new Implementation2();
        factory.setImplementationForClassOrInterface(HaveTwoImplementationsBean.class, implementation2);

        StartingServiceWithInterfaceWithTwoImplementations instance = factory
                .getInstance(StartingServiceWithInterfaceWithTwoImplementations.class);
        assertNotNull(instance.getBean());
        assertTrue(instance.getBean() instanceof Implementation2);
        assertTrue(instance.getBean() == implementation2); // Must be the same object instance
    }

    @Test
    public void testInterfaceWithTwoImplementationsInjectMock() {
        InjectionObjectFactory factory = new InjectionObjectFactory();

        ServiceMockBean mock = Mockito.mock(ServiceMockBean.class);
        Mockito.when(mock.getString()).thenReturn("Mock String");
        factory.setMock(ServiceMockBean.class, mock);

        StartingServiceWithMock instance = factory.getInstance(StartingServiceWithMock.class);
        assertEquals("Mock String", instance.getBean().getString());
    }

    /**
     * Bean in class <code>StartingServiceWithCustomAnnotation</code> is annotated with the custom annotation
     * <code>MyInjectionAnnotation</code>. Bean with custom annotation will be injected, bean with EJB annotation won't.
     */
    @Test
    public void testCustomInjectAnnotation() {
        InjectionObjectFactory factory = new InjectionObjectFactory(MyInjectionAnnotation.class);

        StartingServiceWithCustomAnnotation instance = factory.getInstance(StartingServiceWithCustomAnnotation.class);
        assertNotNull(instance.getBean());
        assertNull(instance.getWillNotBeInjected());
    }

    /**
     * There is an interface which gets extended by an interface. The second interface has an implementation.
     *
     * If an implementation for the first interface is looked up the first implementation must be used.
     */
    @Test
    public void testMultiLevelInterfaceInheritance() {
         InjectionObjectFactory factory = new InjectionObjectFactory();
         MultiLevelInterfaceService instance = factory.getInstance(MultiLevelInterfaceService.class);
         assertTrue(instance.firstInterface instanceof MultiLevelInterfaceImplementation);
    }
}
