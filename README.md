# Rusantara API

> API untuk mencari berbagai macam jajanan tradisional yang ada di indonesia

<!-- == api == -->
# Endpoint

http://34.101.66.254:8080

---
## Register

* URL
  * /signup
* Method
  * POST
* Content-Type
  * application/json
* Request Body
  * **username** as ***string**
  * **email** as **string**
  * **password** as **string**
* Request Body Example
  ```json
  {
    "username": "tester",
    "email": "test@gmail.com",
    "password": "tes123"
  }
  ```
* response
  ```json
  {
    "uid": "0QnZ6LV5LeYTtdRGIqhkwrJuOel2",
    "email": "test@gmail.com",
    "emailVerified": false,
    "displayName": "tester",
    "disabled": false,
    "metadata": {
        "lastSignInTime": null,
        "creationTime": "Thu, 02 Jun 2022 14:26:15 GMT"
    },
    "tokensValidAfterTime": "Thu, 02 Jun 2022 14:26:15 GMT",
    "providerData": [
        {
          "uid": "test@gmail.com",
          "displayName": "tester",
          "email": "test@gmail.com",
          "providerId": "password"
        }
    ]
  }
  ```

## Predict
* URL
  * /predict
* Method
  * POST
* Headers
  * Content-Type : mulitpart/form-data
  * Authorization : Bearer \<token\>
* Request Body
  * image as file
* Response
  ```json
  {
    "name": "clorot",
    "info": "Clorot adalah makanan khas yang terbuat dari adonan tepung beras dan gula merah yang di kukus. Makanan ini merupakan salah satu jajanan khas dari Purworejo, Jawa Tengah.",
    "ingredients": "175 gr gula merah, 400 ml santan, 1/4 sdt garam, 125 gr tepung beras, 50 gr tepung tapioka, Cetakan kue clorot dari daun pisang/daun kelapa",
    "nutrition": ""
  }
  ```
