# 💱 Currency Center


---

## 🧠 Description



---

## 📌 Tech Stack



---

## 🚀 Project Goals (Roadmap)

- [x] Authentication system with JWT
- [x] Add basic currency model


---

## 🔐 Authentication provided by JWT tokens

---

##  💻 Endpoints

### 🧾 Register

- **POST** `/auth/register`
- **Request**
```json
{
  "username": String,
  "password": String
}

example:
{
"username": "john9283",
"password": "StrongPassword123"
}
```
- **Response** `201`
```json
{
  "token": String
}

example:
{
  "token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaWtlIiwiaWF0IjoxNzQ0Nzg5Njc1LCJleHAiOjE3NDQ4NzYwNzV9.LQYxN5PAP-97H3wWPBFQ3Yff1Kow-WyvkiocGGpaQEsMjo6Q522kDIokto5nwxnHVxDCTi1117hG_hdbwTGSPLKM"
}
```







# Currency center

### WEB Application supports exchange rate business

### In progress...





## ENDPOINTS:


**login:**

method: POST
url : /auth/login
json:
{
    "username":STRING,
    "password":STRING
}
success status code: 201
success response : {"token":STRING}


**register:**

method: POST
url : /auth/register
json:
{
"username":STRING,
"password":STRING
}
success status code: 201
success response : {"token":STRING}

**get all currency rates**

method: GET
url : /api/get

success status code: 200
success response : {
    {
    "code":STRING,
    "name":STRING,
    "buy_rate":DOUBLE,
    "sell_rate":DOUBLE
    }
    }

**get rate for specific currency**

method: GET
url : /api/get/{currency_code}
example url : /api/get/USD
success status code: 200
success response : 
{
"code":STRING,
"name":STRING,
"buy_rate":DOUBLE,
"sell_rate":DOUBLE
}

**update rates based on nbp currency rates**

method: GET
url : /api/update-nbp/{percent}
example url : /api/update-nbp/8

success status code: 201
success response : {
"status": 201,
"message": "successfully updated the rates based on nbp rates"
}


**update specific currency rate**

method: PUT
url: /api/update/{currency_code}
example url: /api/update/USD

json : {
"buy_rate":DOUBLE,
"sell_rate":DOUBLE
}

success status code: 200
success response:
{
"status": 200,
"message": "Successfully updated value..."
}

**create new transaction**

method: POST
url : /api/transaction/add

json: {
"type":"BUY",
"amount":120.00,
"currency":"EUR"
}

success status code : 201
example success response:
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

**show all transactions**

method: GET
url: /api/transaction/get

success status code: 200
example success response:
[
{
"id": 1,
"date": "2025-04-16T14:24:21.901712",
"type": "BUY",
"currency": "EUR",
"amount": 120.0,
"exchange_rate": 4.35,
"exchanged_amount": 522.0,
"employee": {
"id": 1,
"username": "mike"
}
}
]

**show specific transaction by id**
method: GET
url: /api/transaction/get/{id}

success status code: 200
example success response:

{
    "id": 1,
    "date": "2025-04-16T14:24:21.901712",
    "type": "BUY",
    "currency": "EUR",
    "amount": 120.0,
    "exchange_rate": 4.35,
    "exchanged_amount": 522.0,
    "employee": {
    "id": 1,
    "username": "mike"
    }
}

