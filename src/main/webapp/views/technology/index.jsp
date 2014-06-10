<div id="accordion-resizer" class="ui-widget-content">
<div id="accordion">

<h3>Core Java</h3>

<div><p>

what is Unicode<BR/>
Hierarchy of Error<BR/>
What is Preverification and Obfuscation? Which one is used first? (Obfuscation first)<BR/>
How to make an unreachable Object rechable in java<BR/>
Making an array as volatile does not make entries in the array volatile. true<BR/>
Why enum can extend interface but not class?<BR/>
What shall be the data type to store currency?<BR/>
Abstract method must be in Abstract class (true or false) -> false<BR/>
What are weak, strong, soft, and phantom references?<BR/>
Shallow copy vs Deep copy<BR/>
Copy method or clone method inside Cloneable or Object?<BR/>
StringBuffer vs StringBuilder<BR/>
Hashcode and equals methods contract<BR/>
what is JMX<BR/>
How to solve memory leaks problem in java<BR/>
How to solve out of memory error<BR/>
Java object are passed by value or reference?<BR/>
What is constructor chaining<BR/>
What is exception chaining<BR/>
What is the purpose of Marker Interface?<BR/>
How to fix OutOfMemory error<BR/>
How to optimize code and performance?<BR/>
What are the advantages of generics?<BR/>
How to create immutable class<BR/>
different ways of creating objects<BR/>
java.util: scanner, console, and formatter<BR/>
How to create checked and unchecked exception<BR/>
How to decide the exception type for custom exception<BR/>
CGlib vs byte proxy<BR/>
heap and stack<BR/>
different security approach (authentication, authorisation)<BR/>
in C++, class A extends B, C... how will u do the same in java<BR/>
Does GC in java happens in Perm gen of Heap? (JVM dependent)<BR/>
What is LDAP<BR/>
Circular reference<BR/>
How to find out all the interfaces implemented by a class (reflection)<BR/>
Advantages of enum, generics<BR/>
What is jndi and how to implement it? If there is Naming.rebind then why we use it?<BR/>
(You have to lookup for a compnent to specify name)<BR/>
What are artifacts in Maven<BR/>
Maven vs gradle vs Ant<BR/>
How to configure log4j to output WARN and INFO messages in different files<BR/>
Log4j vs Logback<BR/>
how to find most frequently used word in the Big file efficiently. (read file, tokenize into words and sort)<BR/>
Cyclic redundancy. is it ready for GC?<BR/>
Why is String class declared final?<BR/>
BIG O Notation<BR/>
What is the use of cause method in Exception<BR/>
How to reverse and then copy a file to other file without opening it?<BR/>
What is scripting in UNIX<BR/>
Whether code works:<BR/>
class B {final static m()} class D extends B { m()}
Whether code works:
class B {show()} interface I { show()}
class D extends B implements I {show()}<BR/>
What will happen in the below code:
System.out = System.in; // compile time error
System.out.println("test");<BR/>
How to write multiplication of two very large nos. without overflow <BR/>
Can we write private constructor inside abstract class? (Yes)<BR/>
How to read file<BR/>
How to get value of com in www.yahoo.com<BR/>
How many objects will be created:
String s = "a" + "b"; s = s + c;<BR/>
Inner class can be final, static, and private (true/false)<BR/>
Why we use private constructor?<BR/>
What are default methods in reflection.<BR/>
How to set and get fields in reflection?<BR/>
What shall (Math.NAN != Math.NAN) returns?<BR/>
Classloader hierarchy. Different types of class loaders.<BR/>
Java Objects' are passed by value or reference?<BR/>
what is dangling pointers<BR/>
Comparable vs Comparator<BR/>
how to create Annotation types<BR/>
Attack aim to prevent legitimate access to service by user (Denied of Service)<BR/>
xsc:whitespace (no normalisation)<BR/>
what is parameterised concept in SLF4J<BR/>
is log4j thread-safe<BR/>
why can't have 2 or more public classes<BR/>
why debug logging inside if (isDebugEnabled)<BR/>
what is jar sealing<BR/>
what is the use of Admin Server and Managed server in Weblogic<BR/>
how to implement generic stack<BR/>
why enum is type safe?<BR/>
how do you get lost modified object in collection<BR/>
swapped two int in a method and swapped values are available outside the method (use array)<BR/>
how to prevent overflow when calculating hashcode value.<BR/>
What things decides class Identity<BR/>
Why annotations can have only some specific data used like String, enum<BR/>
java bean activation framework<BR/>
Explain Jaas (Java Authentication & Authorization Services)<BR/>

</p></div>

<h3>Threads</h3>

<div><p>

Why wait should be called inside a loop?<BR/>
CountDownlatch vs CyclicBarrier<BR/>
How can multiple threads access synchronized block (Semaphores)<BR/>
What is Counting Semaphore and Binary Semaphore<BR/>
What are Atomic classes<BR/>
What is ThreadLocal<BR/>
Why wait should be called inside a loop?<BR/>
CountDownlatch vs CyclicBarrier<BR/>
How can multiple threads access synchronized block (Semaphores)<BR/>
What is Counting Semaphore and Binary Semaphore<BR/>
What are Atomic classes<BR/>
What is ThreadLocal<BR/>
What is Fork/Join in Java 7<BR/>
process can communicate through (sockets, signal handlers, shared memory, semaphores, files)<BR/>
What is deadlock in multithreading and how to fix it?<BR/>
How volatile and static variables work differently in multithreaded environment?<BR/>
notify vs notifyAll<BR/>
Why wait, notify always inside synchronized block?<BR/>
What happens when start is called on an already running Thread<BR/>
How to implement Thread Pooling in Java<BR/>
How immutable objects help in writing concurrent application<BR/>
How Executor framework is better than creating and managing thread by application<BR/>
Executor vs Executors<BR/>
What is race condition in multithreading and how to fix it?<BR/>
how to prevent starvation?<BR/>
what is livelock<BR/>
How to synchronize constructor (of no use)<BR/>
How to stop a thread. (Thread.stop deprecated)<BR/>
Advantages of Lock interface<BR/>
Who call run() of Thread<BR/>
Synchronized can be use at 3 levels. Which is most optimized one?<BR/>
How to do inter-thread communication<BR/>
Thread One writer and multiple read synchronization<BR/>
When we synchronized a class. Lock is assigned to whom? (obtains a lock on the class)<BR/>
What is ThreadScheduler<BR/>
What is Exchanger<BR/>
shall we synchronize both setter and getter methods<BR/>
volatile vs Atomic<BR/>
what are native and green thread<BR/>
can we set priority of  daemon thread<BR/>
how to make thread safe of an array element<BR/>

</p></div>

<h3>Collection Framework</h3>

<div><p>

Best practices related to Java Collection Frameworks<BR/>
how to implement LinkedList with next node and a random next node?<BR/>
sorting in collections (quick : for primitive, merge : for objects)<BR/>
What is unmodifiable collections<BR/>
which list traverse approach is better: index based or iterator based<BR/>
ArrayList vs LinkedList<BR/>
TreeMap vs BinaryTree<BR/>
TreeMap vs Sorted Map<BR/>
HashSet - how it checks for duplicacy<BR/>
Arrays.sort vs Collections.sort which one is faster<BR/>
Do you require hashcode for element in ArrayList? (only Map and Set)<BR/>
ArrayList implementation<BR/>
How Hashmap can be work in sequence order<BR/>
What is load factor in Hashmap (0.75)<BR/>
How hashing implemented in HashMap<BR/>
How to implement your own HashMap<BR/>
How to iterate alternate element in ArrayList<BR/>
Which collection you will use for unique and ordered collection<BR/>
What will be the size of ArrayList if we add an element in the loop. Will it throw exception? (Yes, size will increase)<BR/>
HashTable and Vector since 1.0 and Collection APIs since 1.2<BR/>
For sorting in desc order of Name, which collection you will use?<BR/>
How to maintain insertion order for HashMap<BR/>
What is ListIterator<BR/>
What is Queue, BlockingQueue, DeQueue, and BlockingDeque<BR/>
What are fail fast and fail safe properties in Iterator<BR/>
What is Big-O notation<BR/>
What are Lock, ReadWriteLock and Condition interfaces<BR/>
What are ReentrantLock, ReentrantReadWriteLock<BR/>
Why Lock should be inside try block<BR/>
When to use CopyOnWriteArrayList<BR/>
What methods you should override for the key class in HashMap<BR/>
Which one to use: synchronizedMap, Hashtable, and ConcurrentHashmap<BR/>
Which one's performance is better contains method of List or binarySearch method?<BR/>
Advantages of EnumSet<BR/>
How to sort a list, set, and HashSet<BR/>
Why prior to Java 5, we cannot add primitive values into ArrayList and other Collections<BR/>
which collection is used for fast access? map, set<BR/>
how to compare 2 list contents<BR/>
Default arg in generic map<BR/>

</p></div>

<h3>Singleton</h3>

<div><p>

When to use singleton class?<BR/>
Singleton in clustered environment or in multiple class loader<BR/>
when singleton can break? (reflection, serialization, lazy initialization and not synchronized, multiple class loaders)<BR/>
what is double checked locking in Singleton?<BR/>
Is using enum for Singleton a good practice?<BR/>
how to stop creating more than one instance of singleton when it serialized or deserialized<BR/>

</p></div>

<h3>Serialization</h3>

<div><p>

Serialization vs Externalization<BR/>
How would you exclude a field from Serialization<BR/>
what is UUID in serialization<BR/>
what if sub-class is Serializable but super class is not<BR/>
constructors are not called at de-serialization but only non-serializable class constructor is invoked<BR/>
versioning issue in serialization<BR/>
why all java classes are not serializable? (runtime specific things should not be serialized e.g. streams, threads, runtime and gui classes)<BR/>
writeObject, readObject, where they are defined<BR/>
protected Object readResolve<BR/>

</p></div>

<h3>Testing framework</h3>

<div><p>

Why to go for junit instead of main method?<BR/>
junit for private methods<BR/>
How to test a protected method<BR/>
what is code coverage<BR/>
what is mockito<BR/>
Explain Test Driven Development<BR/>
How to launch a debugger when a test fails<BR/>
How to test a method that does not return anything<BR/>
What is a JUnit test fixture<BR/>
Under what conditions shall you test set() and get() methods<BR/>
Why not to use System.out.println() for Unit Testing<BR/>
Why not to use Debugger for Unit Testing<BR/>
what is @SuiteClasses annotation<BR/>
Why to import org.junit.Assert statically<BR/>

</p></div>

<h3>Xml, JSON, XSL, Web Services</h3>

<div><p>

XML vs JSON vs JSONP<BR/>
how to modify xml using xslt?<BR/>
DOM vs SAX vs STAX parsers<BR/>
Jaxp vs JaxB. Why JaxB, it consumes too much memory<BR/>
SOAP vs REST<BR/>
what is endpoint in web-service<BR/>
What is top-down and bottom-up approaches in web-services<BR/>
What are CDATA and PDATA in xml (Character and Parse)<BR/>
What is the input for web-service. what tool you have used?<BR/>
Which parser will u use to cache xml data<BR/>
Life cycle of JSON object<BR/>
what is atomic variables in xml / xsl<BR/>
java API for xml registers<BR/>
SOAP with attachments API for java<BR/>

</p></div>

<h3>Jdbc</h3>

<div><p>

how to set null values for the Java primitives (PreparedStatement.setNull)<BR/>
how to decide Isolation level<BR/>
jdbc 4.0 new features (automatic loading of drivers)<BR/>
What is running out of cursor problem in jdbc<BR/>
local vs Distributed transaction<BR/>
After dynamic loading of database driver. How it is automatically registered to driver manager class?<BR/>
Data Source vs DriverManager<BR/>
Where ResultSet data is stored as it's an interface?<BR/>
UpdateableResultSet<BR/>
Connected vs Disconnected RowSet<BR/>
What will happen if we do not close the Connection, Statement or ResultSet<BR/>
Methods for getting isolation level<BR/>
Write CallableStatement<BR/>
How to cache in jdbc code?<BR/>
How to handle transaction?<BR/>
How PreparedStatement is efficient. What is pre-compiled<BR/>
Where pre-compiled files gets store<BR/>
what is 2-phase commit<BR/>
What are in-memory databases<BR/>
if 2 or more driver class are called in Class.forName and then we con from Connection Manager. which one con is returned (based on url)<BR/>
how to search a word in blob in efficient way<BR/>

</p></div>

<h3>JMS</h3>

<div><p>

In MOM, how can we force a message to be deliver to a particular receiver?<BR/>
JMS acknowledgement sent to whom?<BR/>
how to handle transaction in jms?<BR/>

</p></div>

<h3>Servlet</h3>

<div><p>

Difference between ServletConfig and ServletContext<BR/>
If isThreadSafe is true then servlet class implements SingleThreadModel and performance degrade. False<BR/>
How to do inter-selvlet communication<BR/>
What happen if we call destroy method from init.<BR/>
Can we have private constructor in Servlet class?<BR/>
how to delete cookie<BR/>
How to implement servlet chaining<BR/>
can we put servlet outside WEB-INF and access it after defining mapping in web.xml<BR/>
how container knows that servlet instance already has been created.<BR/>

</p></div>

<h3>Jsp</h3>

<div><p>

Difference between RequestDispatcher and Forward<BR/>
Custom tags in jsp?<BR/>
Which implicit object can get any implicit object?<BR/>
redirect vs forward<BR/>
advantages of using custom tags over scriptlet and java code in jsp<BR/>
Default value of scope in jsp.<BR/>
how a very large jsp page will be divided to load it successfully<BR/>

</p></div>

<h3>EJB</h3>

<div><p><BR/>

What is initial context<BR/>
Does initial context is shared between 2 or more EJBs<BR/>
How to call session bean from client<BR/>
What are Transaction attributes<BR/>
How to figure out whether code is stateless or stateful session bean<BR/>
how client state is maintained in staetful session bean<BR/>
Can we use HttpSession inside Stateless Session bean<BR/>
What happen when session bean throws exception<BR/>
how user defined exception can be send from EJB server to client<BR/>
On server side, how to determine request is coming from which browser?<BR/>
How Apache is configured (Module)<BR/>
</p></div>

<h3>UML and OOPS</h3>

<div><p>

99.9% Availability. What does it mean from the architect point of view.<BR/>
How to do project estimation<BR/>
What is SCM<BR/>
Reliability vs Performance trade off<BR/>
Difference between Class and Object diagram<BR/>
Date and Time design issue prior to Java 8<BR/>
Good practices to avoid null pointer exceptions<BR/>
Sequence diagram<BR/>
Activity diagram<BR/>
Component diagram<BR/>
Example of Encapsulation<BR/>
What is Open Closed Principle<BR/>
Dependency Inversion Principle<BR/>
Interface Segregation Principle<BR/>
Single Responsibility Principle<BR/>
Liskov's Substitution principle<BR/>
Difference between interface and abstract class<BR/>
statically typed vs dynamic language<BR/>
Design for reading bulk data (Chunk oriented processing)<BR/>
Why java don't have default argument feature like C<BR/>
which java class uses abstract factory<BR/>
how you will decide when to use abstract factory class<BR/>
Throw an exception early and catch an exception late. Why?<BR/>
Procedure vs Function<BR/>
Composition vs Aggregation<BR/>
disadvantages of dependency injection<BR/>
Cohesion vs Coupling<BR/>
Inheritance vs Composition. which one is better?<BR/>
Use Case, Activity, and Sequence diagram<BR/>
Draw concrete class, abstract class, and interface in UML<BR/>
How to draw line between Actor and Use Case<BR/>
How to draw line between Use Case and other Use Case that will be call from it<BR/>
How to draw line between Use Case and parent Use Case<BR/>
Difference between Team Lead, Tech Lead, and Architect<BR/>
Vertical vs Horizontal scaling<BR/>
Scalability (Clustering, load balances)<BR/>
What are Creational. Structural, and Behavioural design patterns<BR/>
Template and Service Locator design patterns<BR/>
Business Delegate design pattern<BR/>
Decorator design pattern<BR/>
Factory, Abstract Factory pattern example<BR/>
Strategy vs State pattern<BR/>
Observer, Mediator design pattern<BR/>
Visitor design pattern<BR/>
What is the need to go the need to go through double dispatch in Visitor pattern<BR/>
MVC vs MVP<BR/>
Synchronizer token pattern (multiple submit problem)<BR/>
XML vs Annotations<BR/>
It's better to have an Apache server handling all the http requests and route it to App server to handle dynamic pages. true<BR/>
Contractor and Employee. How you will associate these 2 classes object. Contractor is not an Employee.<BR/>
Does architects decides till deployment, maintenance, bug fixing strategies.<BR/>
Does architects decides team resources<BR/>
How to get last update person from Map (Observer pattern)<BR/>
Configuration Management (Git vs SVN vs CVS vs VSS vs Rational Clear Case)<BR/>

</p></div>

<h3>Spring Framework</h3>

<div><p>

Spring configaration using xml vs annotation vs javaconfig<BR/>
Which class Spring use to implement different Collections (LinkedHashmap, ArrayList, LinkedHashSet)<BR/>
Why it implemented using these classes (to preserve order)<BR/>
How to change default concrete Collection classes<BR/>
replicate method in Spring<BR/>
Spring OpenSessionInViewFilter<BR/>
Spring design pattern<BR/>
Advantages of Spring framework<BR/>
what is OSGI<BR/>
What is DI and it's advantages and disadvantages<BR/>
Use of Spring property editor and Type conversion<BR/>
Spring AOP: dynamic proxies vs cglib proxies<BR/>
Shall we use DI to create each and every Object in class<BR/>
Xml Config vs Annotation vs JavaConfig<BR/>
What is DataAccessException<BR/>
How is singleton bean scope is different from Singleton class<BR/>
Benefits of Spring framework transaction management<BR/>
What is bean wiring<BR/>
What are different types of Autowire types<BR/>
What are the different types of AutoProxying<BR/>
What are the different advice types in spring<BR/>
What is an Introduction in AOP<BR/>
What is a Pointcut<BR/>
ApplicationContext vs BeanFactory<BR/>
How will you call getInstance() of Singleton from Spring configuration file?<BR/>
Spring and Hibernate integration<BR/>
how to configure struts in Spring<BR/>
Difference in Spring and EJB transaction<BR/>
Can we use PUT, DELETE etc in Spring RequestMapping<BR/>
How to implement inheritance in bean defination (parent property)<BR/>
How to control bean instantiation process (init-method, implement Initializing Bean)<BR/>
default (singleton and eager initialization)<BR/>
Eager or Lazy loading. Which one is by default for BeanFactory and ApplicationContext<BR/>
how web module loads beans application.xml in spring<BR/>
Validation framework<BR/>
What is weaving<BR/>
What are the different points where weaving can be applied (compile time, class load, runtime)<BR/>
What are inner beans<BR/>
What are the important beans lifecycle methods<BR/>
View Resolvers<BR/>
Transaction propagation in Spring<BR/>
Nested Transaction in Spring<BR/>
How to create bean using factory-method with parameter<BR/>
How to implements 5 modes of wiring.<BR/>
Which extra parameter is required with session scope<BR/>
Limitations of CGlib<BR/>
How multiple submit problem is handled in Spring MVC<BR/>
How many objects will be created below:<BR/>
<bean id="emp1" class="Emp" scope="singleton" /><BR/>
<bean id="emp2" class="Emp" scope="singleton" /><BR/>
what is method injection<BR/>
In Spring, how multiple request is handled by Singleton object. Isn't it the problem of multi-threading<BR/>
@Service Service Locator pattern is used. How will you cache ServiceLocator in Spring<BR/>
How to handle browser refresh in Spring (RedirectView, PRG)<BR/>
If form in struts have 200 fields then which data-structure you will use? (Map)<BR/>
how to maintain order in AOP (@Order)<BR/>
Spring team recommend: only annotate concrete class witg @Transactional, as opposed to interfces. true<BR/>
(annotations are not inherited. annotation at interface will only work in case of interface based proxies)<BR/>
how container knows about the properties defined in diff resource bundle in struts<BR/>

</p></div>

<h3>Hibernate Framework</h3>

<div><p>

What are the ORM level (pure, light, medium, full)<BR/>
What are the general considerations or best practices for defining your Hibernate persistent classes<BR/>
how to improve hibernate query performance<BR/>
How Hibernate does inheritance of domain classes<BR/>
types of inheritance model<BR/>
Open View Session Filter and it's use<BR/>
Hibernate Object life-cycle or state<BR/>
merge, save, saveOrUpdate, update in Hibernate<BR/>
sessio.update vs session.lock<BR/>
How would you reatach detached objects to a session when the same object has already been loaded into the session (merge)<BR/>
How does Hibernate distinguish between transient and detached objects (version property)<BR/>
What are the pros and cons of detached objects<BR/>
Batch update in hibernate<BR/>
When to use bag in hibernate<BR/>
different Hibernate generators<BR/>
Criteria vs HQL<BR/>
session.load vs session.get<BR/>
session.evict(object) will it detach object<BR/>
What is transaction write behind<BR/>
Second level cache<BR/>
Statistics<BR/>
What are fetching strategies in Hibernate (Select, Sub-select, Join)<BR/>
Hibernate many-to-many implementations<BR/>
diff update and lock in hibernate<BR/>
Can we make Hibernate Entity class final?<BR/>
Why no argument constructor in Hibernate Entity class?<BR/>
What is the difference between sorted and ordered collection in hibernate<BR/>
What is component mapping in Hibernate<BR/>
What are derived properties<BR/>
If you want to see the Hibernate generated SQL statements on console, what should we do?<BR/>
How do you switch between relational databases without code changes<BR/>
What are the benefits does HibernateTemplate provide<BR/>
N+1 problem in hibernate<BR/>
When to use Hibernate filters<BR/>
Hibernate singleton<BR/>
When to use lazy=false<BR/>
What is inversse=true attribute in association<BR/>
dbcp vs c3p0 database connection pooling<BR/>
Session vs SessionFactory<BR/>
How to optimize hibernate<BR/>
Aggregate function using Criteria in Hibernate (Hint: projection)<BR/>
Examples of Lazy loading and Eager fetching<BR/>
hibernate.max_fetch_depth (set a max depth for outer join fetch tree for single ended association)<BR/>
connection pool in hibernate. hibernate.connection.pool_size<BR/>
2 DB in hibernate (using 2 SessionFactory)<BR/>
When to use JDBC instead of Hibernate<BR/>
Hibernate or Storeed Procedure. Which to use when.<BR/>
Ejb transaction attributes. What is by default? In Requires New, if outer method fails?<BR/>
Entity vs Value Objects<BR/>
How to implement different levels of caching in Hibernate?<BR/>
What are hibernate properties?<BR/>
In hibernate, session.close is done, but transaction.commit is not done. then data won't save<BR/>
how to call stored procedure in Hibernate<BR/>
What is named sql query<BR/>
Define cascade and inverse in one-to-many relationship<BR/>
how to define sequence generated primary key in hibernate<BR/>
Cache data is not always update. So, if another user update then how to sync (Version attribute)<BR/>
Does HibernateDaoSupport used in HibernateTemplate<BR/>
HibernateTemplate is based on which design pattern<BR/>
How to do Union using HQL or Criteria API? (Can't do UNION or INTERSECT. Use view or native sql)<BR/>
How to do right outer join in Hibernate<BR/>
How to prevent SQL injection in hibernate<BR/>
Hibernate custom user type (UserType, CustomUserType, nullSafeGet(), nullSafeSet())<BR/>
savePoint in hibernate<BR/>
What are Callback interfaces<BR/>
What should SessionFactory be placed so that it can be easily accessed<BR/>

</p></div>

<h3>SQL</h3>

<div><p>

Normalistion<BR/>
what is database partitioning<BR/>
index key vs unique key vs candidate key vs primary key vs composite key vs foreign key.<BR/>
what is the need of foreign key<BR/>
how to chose primary key (1) Not Null, 2) Unique Value in Table and 3) Static - are the best candidates for Primary Key)<BR/>
why it is not a good practice to make Identity Column as Primary Key.<BR/>
when there is JOIN on Primary Key or when Primary Key is used in the WHERE condition it usually gives better performance than non primary key columns. true<BR/>
different types of Triggrers<BR/>
What are dirty fields<BR/>
What is Sequence<BR/>
What is create index<BR/>
How to join 3 tables<BR/>
Union vs Union All<BR/>
For UNION operation which condition must be true<BR/>
truncate vs delete<BR/>
correlated vs non-correlated sub-queries<BR/>
What is cursor and what will happen if we do not close the cursor<BR/>
Can we use joins for any sub-query?<BR/>
ACID property<BR/>
What is indexing?<BR/>
Does indexing improves Insert and Update<BR/>
clustered vs non-clustered index<BR/>
Primar Key, which index<BR/>
What is Sql injection and how to avoid it<BR/>
When to use char instead of varchar<BR/>
What is Execution plan. How it work for one select statement from the group<BR/>
Materialised view<BR/>
How to delete duplicate records<BR/>
find nth highest numbvler?<BR/>
What is Optimistic concurrency control in database.<BR/>
DECODE and CASE<BR/>
analytical function in oracle<BR/>
tab: col a, b...  display value of a, if col b is null and vice versa (use nvl)
How rownum is assigned to a table? Is it sequential or not?<BR/>
Datatype of rowid and rownum<BR/>
Unique constraint vs Unique index (there is no practical difference)<BR/>
triggers do not accept parameters or arguments<BR/>
Why to use stored procedure (security)<BR/>
mutating trigger (when trigger is manipulating data of same table on which trigger is created)<BR/>
Lock Db row so that others would not able to update it<BR/>
3 types of locking in db (table level, page and row level)<BR/>
how transaction is maintained if connection with 2 dbs<BR/>
can we write commit in trgger? (yes, but need to configure in special way)<BR/>
How to find the duplicate rows count from employees table in oracle?<BR/>

</p></div>

<h3>Web</h3>

<div><p>

Can we use *.do inside welcome-file-list in web.xml<BR/>
/ vs /* in url-pattern of web.xml<BR/>
Web-Inf is under classpath then why config.xml is under classes folder. isn't it bad design?<BR/>
javascript log (console.log())<BR/>
What is error code for server side errors<BR/>
Click on browser's back button. Now, how to preserve the previous session?<BR/>
When to use PUT, DELETE, and TRACE<BR/>
Debugging plugins for browsers<BR/>
When we alter a jsp page, how server get informed of it?<BR/>
How to install weblogic and other Servers<BR/>
How to write file upload<BR/>
What is multiple submit problem and how to solve it<BR/>
Exception handling in jsp<BR/>
how to implement hit-count on web-page<BR/>
before ajax, how web-pages do asynchronous server call<BR/>
what is META tag in html<BR/>
how to share things in multiple web applications?<BR/>
how do you decide between jquery, dojo, and ext-js libraries<BR/>
What are HttpWatch and IEWatch<BR/>
what is .htaccess file<BR/>
what is virtual hosting<BR/>
What are different HTTP code<BR/>
how to invoke a method on server when close is clicked on browser. (js window close event)<BR/>
how to change size of hml page from a new page appeared on click of button on current page.<BR/>
web site count (2 line code)

</p></div>

</div>
</div>