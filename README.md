# 💱 Currency Center

A **Currency Center** application is my pet project that allows me to discover Java & Spring Boot technologies.
It is constantly improve by adding new features and finally I'd  like to built a Full Stack Web Application .

---

## 🧠 Description




---

## 📌 Tech Stack

- Java 21
- Spring Boot 3.4+
- Spring Security
- PostgreSQL

---

## 🚀 Project Goals (Roadmap)

- [x] Add exchange rates (manual + NBP update)
- [x] Add basic currency model
- [x] Authentication system with JWT (login / register)
- [x] Add transaction system (BUY / SELL)
- [x] Add requests validation
- [x] Add UI
- [ ] Add unit & integration tests
- [ ] Dockerize the app
- [ ] Add monthly reports
-  **And many more...**

---

## 🔐 Authentication provided by JWT tokens

---

##  💻 Endpoints

### 🟣 Register

- **POST** `/auth/register`
- **Request**
```json
{
  "username": "<String>",
  "password": "<String>"
}
```

- **Response** `201`
```json
{
  "token": "<String>"
}
```

### 🟣 Login

- **POST** `/auth/login`
- **Request Body**
```json
{
  "username": "<String>",
  "password": "<String>"
}
```
- **Response** `201`
```json
{
  "token": "<String>"
}
```

### 🟣 Change password

- **POST** `/auth/change-password`
- **Request Body**
```json
{
  "old_password": "<String>",
  "new_password": "<String>"
}
```
- **Response** `201`
```json
{
  "status": "<Int>",
  "message": "<String>"
}
```

### 🟣 Get all currency rates

- **GET** `/api/get`
- **Request Header**
```json
{"Authorization": "Bearer {token}"}
```
- **Response** `200`
```json
[{
    "code":"<String>",
    "name":"<String>",
    "buy_rate":"<Double>",
    "sell_rate":"<Double>"
},
"..."
]
```

### 🟣 Get rate for specific currency

- **GET** `/api/get/{currency_code}`
- **URL EXAMPLE** `/api/get/USD`
- **Request Header**
```json
{"Authorization": "Bearer {token}"}
```
- **Response** `200`
```json
{
    "code":"<String>",
    "name":"<String>",
    "buy_rate":"<Double>",
    "sell_rate":"<Double>"
}
```

### 🟣 Update rates based on NBP currency rates

#### Application sets new sell rate and buy rate based on NBP currency rates + adding or subtract percent

- **GET** `/api/update-nbp/{percent}`
- **URL Example** `/api/update-nbp/7`
- **Request Header**
```json
{"Authorization": "Bearer {token}"}
```
- **Response** `201`
```json
 {
  "status": 201,
  "message": "successfully updated the rates based on nbp rates"
}
```

### 🟣 Update specific currency rate

- **PUT** `/api/update/{currency_code}`
- **URL Example** `/api/update/EUR`
- **Request Header**
```json
{"Authorization": "Bearer {token}"}
```
- **Request Body**
```json
{
  "buy_rate":"<Double>",
  "sell_rate":"<Double>"
}
```
- **Response** `200`
```json
{
  "status": 200,
  "message": "Successfully updated value..."
}
```

### 🟣 Create new transaction

- **POST** `/api/transaction/add`
- **Request Header**
```json
{"Authorization": "Bearer {token}"}
```
- **Request Body**
```json
{
  "type":"<BUY | SELL>",
  "amount":"<Double>",
  "currency":"<String>"
}
```
- **Response** `201`
```json
{
    "message": "Successfully added new transaction!",
    "body": {
        "id": 1,
        "type": "BUY",
        "amount": 120.0,
        "exchangeRate": 4.35,
        "exchangedAmount": 522.0,
        "currency": "EUR"
    }
}
```

### 🟣 Show all transactions

- **GET** `/api/transaction/get`
- **Request Header**
```json
{"Authorization": "Bearer {token}"}
```
- **Response** `200`
```json
[
  {
    "id": "<Long>",
    "date": "<String>",
    "type": "<BUY | SELL>",
    "currency": "<String>",
    "amount": "<Double>",
    "exchange_rate": "<Double>",
    "exchanged_amount": "<Double>",
    "employee": {
      "id": "<Long>",
      "username":"<String>"
    }
  },
  "..."
]
```

### 🟣 Show specific transaction by id

- **GET** `/api/transaction/get/{id}`
- **URL Example** `/api/transaction/get/1`
- **Request Header**
```json
{"Authorization": "Bearer {token}"}
```
- **Response** `200`
```json
{
  "id": "<Long>",
  "date": "<String>",
  "type": "<BUY | SELL>",
  "currency": "<String>",
  "amount": "<Double>",
  "exchange_rate": "<Double>",
  "exchanged_amount": "<Double>",
  "employee": {
    "id": "<Long>",
    "username":"<String>"
  }
}
```

### 🟣 Delete transaction by id

- **GET** `/api/transaction/delete/{id}`
- **URL Example** `/api/transaction/delete/1`
- **Request Header**
```json
{"Authorization": "Bearer {token}"}
```
- **Response** `201`
```json
{
  "status": 200,
  "message": "Successfully deleted transaction..."
}
```

