# Ex11_FragmentsAndNavigation
Lecture 02 - Development of Graphical User Interfaces (GUI)

The user can customize the Froyo of her dreams (size, topping, sauce) and place an order.
- There is a single Activity that holds a FragmentContainerView to display all the screens (implemented as Fragment).
- A single ViewModel is shared between the Fragments to access the state (size, topping, sauce) of the Froyo.
- Navigation between Fragments is automatically managed by the NavController. The navigation graph has been declared as an XMl resource and the layout for the MainActivity contains the navHostFragment.  