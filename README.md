
Author: Mir Derakhshan, 1 Oct 2016
Title: Readme for program Shopping

1. Importing and Building Project
---------------------------------
Maven is used to build this project. You can therefore easily import the project into your Java IDE (such as
Intellij IDEA or Eclipse) by using the IDE provided Maven import capability.

To build the project from scratch, use the mvn command with the argument "clean install".

2. Running this program
------------------------
You can run this program either by supplying a list of products (separated by the space character) as
program argument (e.g. milk bread soup), or do not specify an argument, in which case you will be
prompted to enter the list from the console.

3. Code Design & Architecture
-----------------------------
The code employs a service-oriented architecture using service/component interface and implementation
classes. The main set of services are defined in the interface class **CartService**, which is implemented by
the class **CartServiceImpl*.

The code uses core Spring Boot for dependency injection and as a result it can easily be extended
to exploit other Spring modules, such as Spring MVC, to provide web service APIs  (RESTful or otherwise)
for the core services defined in the CartService interface class.

NOTE:
There are two Dao interface classes for loading menu data and special offers data: **MenuDao** and
**SpecialOffersDso**. The code provides default implementations of these classes, **MenuDAOImplSample** and
**SpecialOffersImplSample** respectively. These implementations however use hard coded sample data.  Clearly
in a production environment alternative implementations should be provided to read menu and special offers
data from a Dao store such as a file or a database.

4. Types of Special Offers Supported
------------------------------------
The program provides support for the following types of special offers for a given product, say Apple,
(see class **SpecialOffer** for how these offers are represented):

- Standard offer: e.g. "Apples 10% off for every bag"
- Conditional offer type 1: e.g. "Apples discounted at 20% for every 2 bags of Carrots"
- Conditional offer type 2: e.g. "Buy three bags of Apples for the price of two"

5. Unit Tests
-------------
Unit tests are provided to ensure regressions are detected in any future code change/refactoring.

6. Sample Menu and Special Offers Data
--------------------------------------
The sample menu includes the following product details:
- Soup, 0.65p, "price is per tin"
- Bread, 0.80p, "price is per loaf
- Milk, £1.30, "price is per bottle
- Cheese, £1.30, "price is per pack
- Apples, £1.00, "price is per bag

The sample special offers data contains the following offers:
- Apples 10% off
- Bread 50% off for 2 tins of soup
- Milk buy one get one free
