<div style="text-align: center;">

# [<img src="https://i.postimg.cc/7hJKBSmK/3.png" width="75%">](https://postimg.cc/m1R3k1x9)

</div>

The Online Book Store Project is an e-commerce platform designed to provide users with a convenient and enjoyable way to discover, purchase, and manage their favorite books online. The project was created with the aim of bridging the gap between book enthusiasts and their desired reads, offering a seamless shopping experience from the comfort of their own home.
## Project Structure👨‍💻

The Project Structure embodies best practices, including adherence to SOLID principles, ensuring a modular and maintainable codebase. Technology like Spring Framework empower robust backend development, while Docker facilitates streamlined deployment across diverse environments. MapStruct simplifies object mapping tasks, promoting code clarity and maintainability. Additionally, Liquibase enables efficient database versioning, enhancing data integrity and management. This structured approach fosters a scalable and adaptable project architecture, poised for seamless growth and evolution.

### Tech Stack

| Categories               | Technologies                                                     |
|--------------------------|------------------------------------------------------------------|
| Backend                  | Spring Boot <br> Spring Data <br> Spring Security <br> Hibernate |
| Database                 | MySQL                                                            |
| Containerization         | Docker                                                           |
| Mapping                  | MapStruct                                                        |
| Database Version Control | Liquibase                                                        |
| API Documentation        | Swagger                                                          |
| Authentication           | JWT (JSON Web Tokens)                                            |
| Testing                  | JUnit                                                            |
| Code Generation          | Lombok                                                           |
| Build Automation         | Maven                                                            |


### Domain Models (Entities)

1. **User**: Contains information about registered users including authentication details and personal information.
2. **Role**: Represents the role of a user in the system, such as admin or user.
3. **Book**: Represents a book available in the store.
4. **Category**: Represents a category that a book can belong to.
5. **ShoppingCart**: Represents a user's shopping cart.
6. **CartItem**: Represents an item in a user's shopping cart.
7. **Order**: Represents an order placed by a user.
8. **OrderItem**: Represents an item in a user's order.

### Endpoints
#### - Authentication Controller:
- **POST /auth/login:** User authentication.
- **POST /auth/registration:** User registration.

#### - Book Controller:
- **GET /books/{id}:** Find book by id.
- **GET /books:** Find all books.
- **GET /books/search:** Find book by parameters.
- **POST /books:** Create a new book.
- **PUT /books/{id}:** Update a book by id.
- **DELETE /books/{id}:** Delete a book by id.

#### - Category Controller:
- **GET /categories:** Find all categories.
- **GET /categories/{id}:** Find category by id.
- **POST /categories:** Save category.
- **PUT /categories/{id}:** Update category.
- **DELETE /categories/{id}:** Delete category.
- **GET /categories/{id}/books:** Get all books by category.

#### - Order Controller:
- **GET /orders:** Find all orders.
- **POST /orders:** Create order.
- **PATCH /orders/{id}:** Update order status.
- **GET /orders/{orderId}/items:** Find all orderItems in order by id.
- **GET /orders/{orderId}/items/{itemId}:** Find all orderItems by id in order by id.

#### - Shopping Cart Controller:
- **GET /cart:** Find shopping cart by user id.
- **POST /cart:** Add book to shopping cart.
- **PUT /cart/cart-items/{cartItemId}:** Change quantity by cart item id.
- **DELETE /cart/cart-items/{id}:** Delete cart item by id

<div>

## Features⚡️
### For Shoppers👫
</div>

1. **Join and Sign In**:
    - Join the store as a new user.
    - Sign in to browse and purchase books.

2. **Browse Books**:
    - View all available books.
    - Search for books by filter.

3. **Explore Bookshelf Sections**:
    - View different bookshelf sections.
    - Browse books within a specific section.

4. **Shopping Cart**:
    - Add books to the shopping cart.
    - View and manage items in the shopping cart.
    - Remove books from the shopping cart.

5. **Purchase Books**:
    - Checkout and buy books in the shopping cart.
    - View past orders.

### For Managers👨‍💼
1. **Category Management**
    - Add new categories.
    - Edit details of existing categories.
    - Remove categories.
2. **Book Management**:
    - Add new books to the store.
    - Edit details of existing books.
    - Remove books from the store.

3. **Bookshelf Management**:
    - Create new bookshelf sections.
    - Edit details of existing sections.
    - Remove sections from the store.

4. **Order Management**:
    - View and update the status of orders (e.g., "Shipped", "Delivered").

## How to use▶️

```sh
# Clone the project from GitHub:
git clone https://github.com/DenysMaksymKachalup/spring-bookstore.git
cd spring-bookstore
```

Set environment variables:
Add the necessary environment variables to the .env file.

After that, execute the command...
```sh
docker-compose up
```
Now you can use the app with Swagger:

<a>http://localhost:8081/swagger-ui/index.html</a>

## Contact📞

I'm always open to collaboration and discussion on any topic. Feel free to reach out to me!
- [Email:](mailto:denys.k82@gmail.com)
- [Telegram](https://t.me/denyskachalup)
- [LinkedIn](https://www.linkedin.com/in/denys-kachalup-358430222/)

Thank you for your interest in my Online Book Store project!

