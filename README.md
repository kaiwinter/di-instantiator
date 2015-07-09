# di-instantiator
_Inspired by [needle4j](https://github.com/needle4j/needle4j/) which creates an instance of a bean which gets initialized with mocks._

In contrast to needle4j this library is not injecting mock objects but instances of the real objects. For interface types an implementation is looked up from the source and gets injected. As a result you get a completely initialized object graph with real objects.
