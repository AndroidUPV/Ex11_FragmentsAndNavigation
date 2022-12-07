# Ex11_FragmentsAndNavigation
Lecture 02 - Development of Graphical User Interfaces (GUI)

The user can customize the Froyo of her dreams (size, topping, sauce) and place an order.
- There is a single Activity that holds a FragmentContainerView to display all the screens (implemented as Fragment).
- A single ViewModel is shared between the Fragments to access the state (size, topping, sauce) of the Froyo.
- Navigation between Fragments is automatically managed by the NavController. The navigation graph has been declared as an XML resource and the layout for the MainActivity contains the NavHostFragment.
- The Safe Args Gradle plugin is used to pass arguments to the WelcomeFragment. As it is the start Destination, things get a bit more complex: the Navigation graph is set programmatically, instead of declaring it in the XML layout, to pass the initial argument. 