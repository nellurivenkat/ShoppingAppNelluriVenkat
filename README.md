# ShoppingAppNelluriVenkat

The online shopping mobile app created with the requirements stated in the noodle, this app aims to provide a seamless and user-friendly shopping experience to its users. The app fulfills all the requirements mentioned and provides additional features as a bonus. The app uses the fake store API available at https://fakestoreapi.com/ to consume resources, and all resources (User, Product, Cart, etc.) have the same structure as the JSON API responses for auto-parse using Gson and to build JSON back from objects for POST requests without much manual intervention.

The app starts with an authentication page where the user can either sign up or log in using their username and password. Upon successful login, the user's details are stored, and they are directed to the home page of the app, which shows a list of product categories. The app uses a RecyclerView to display the list of categories, and on clicking a category, it shows the list of products available in that category. The user can view the product description, and if interested, they can add the product to their cart by specifying the quantity they want to purchase.

The cart feature of the app shows the total amount of the user's purchases and provides a purchase button to place an order. Upon placing an order, the user is notified of the order's status. The app also allows the user to view their order history, and on clicking an order, it shows the order details and the products ordered. Additionally, the app shows the user's details, a random circular profile image, and a logout button.

The app also includes a RecyclerView for all lists, attaching an authentication token to all requests until logout, and adding a small "About this app" button in the profile page that shows a page with copyright details and credits on click.

The app also uses a ViewPager2 with a bottom TabLayout for Shop, Cart, Orders, and Profile icons, providing a seamless navigation experience. The app also shows a map fragment based on the GPS coordinates in the user's profile.

The app uses the fake store API to consume resources, and all resources have the same structure as the JSON API responses.


Login details

Username: mor_2314 

Password: 83r5^_



Student Details

Student Name - Nelluri Venkat

student ID - 25463
