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
