# di-instantiator
[![Build Status](https://api.travis-ci.org/kaiwinter/di-instantiator.svg)](https://travis-ci.org/kaiwinter/di-instantiator)
[![Coverage Status](https://coveralls.io/repos/kaiwinter/di-instantiator/badge.svg?branch=master&service=github)](https://coveralls.io/github/kaiwinter/di-instantiator?branch=master)

_Inspired by [needle4j](https://github.com/needle4j/needle4j/) which creates an instance of a bean initialized with mocks._

In contrast to needle4j this library is not injecting mock objects but instances of the real objects. For interface types an implementation is looked up from the source and gets injected.
As a result you´ll get a completely initialized object graph with real objects.
You can even override the automatic implementation lookup and provide specific implementations (or mocks if you like).

The library handles all fields which are annotated with ```@Inject```. This can be extended to handle ```@EJB``` fields as well for example.

## Example

### The application code
Use-case class:

```java
public final class UnitTestUsecaseImpl implements UnitTestUsecase {

	@Inject
	private UnitTestDao dao;

	@Override
	public UnitTestEntity loadEntity(int id) {
		return dao.loadEntity(id);
	}

	@Override
	public void saveEntity(UnitTestEntity entity) {
		dao.saveEntity(entity);
	}
}
```

DAO class:
```java
public final class UnitTestDaoImpl implements UnitTestDao {

	@PersistenceContext(unitName = "PU")
	private EntityManager entityManager;

	@Override
	public UnitTestEntity loadEntity(int id) {
		return entityManager.find(UnitTestEntity.class, id);
	}

	@Override
	public void saveEntity(UnitTestEntity entity) {
		entityManager.persist(entity);
	}
}
```

### The test
For demonstration purpose we are saving an entity by calling the use case method ```saveEntity()```.
Afterwards it is loaded by its ID.
The test uses a real database which also could be a mock.
This might not be a real world example but demonstrates an advanced use of the library.
````java
public void test() {
	// Create factory which sets fields annotated by @Inject and @PersistenceContext
	InjectionObjectFactory factory = new InjectionObjectFactory(Inject.class, PersistenceContext.class);
	
	// Create entity manager for test database and let factory use it
	EntityManager entityManager = Persistence.createEntityManagerFactory("TestPU", null).createEntityManager();
	factory.setImplementationForClassOrInterface(EntityManager.class, entityManager);
	entityManager.getTransaction().begin();

	// Obtain fully initialized object from factory, this is what will happen:
	// 1. Instantiate UnitTestUsecaseImpl
	// 2. Lookup implementation of UnitTestDao
	// 3. Instantiate UnitTestDaoImpl 
	// 4. Set provided EntityManager in UnitTestDaoImpl
	// 5. Set UnitTestDaoImpl in UnitTestUsecaseImpl
	UnitTestUsecaseImpl usecase = factory.getInstance(UnitTestUsecaseImpl.class);

	// Save entity and test the result of a reload
	usecase.saveEntity(new UnitTestEntity(1, "test name"));
	
	UnitTestEntity loaded = usecase.loadEntity(1);
	Assert.assertEquals("test name", loaded.getName());
}
```

### FAQ
#### What happens if two implementations are found for one interface?
If there is more than one implementation found an Exception is thrown. To provide a specific implementation use 
```setImplementingClassForInterface(Class<?> interfaceClass, Class<?> implementationClass)``` or ```factory.setImplementationForClassOrInterface(Class<? extends T> classOrInterface, T object)```.

#### Which annotations are processed?
By default the factory handles all fields which are annotated with ```@Inject```. This can be changed by passing additional annotations in the constructor:
```InjectionObjectFactory factory = new InjectionObjectFactory(Inject.class, EJB.class, PersistenceContext.class);```

## Maven
di-instantiator is not on Maven Central. Find it on [JitPack.io](https://jitpack.io/#kaiwinter/di-instantiator/v1.0.0)
```xml
<repository>
	 <id>jitpack.io</id>
	 <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.kaiwinter</groupId>
    <artifactId>di-instantiator</artifactId>
    <version>v1.0.0</version>
</dependency>
```

## License
     Copyright 2015 Kai Winter
     
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
     
         http://www.apache.org/licenses/LICENSE-2.0
     
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
