# Rusantara API

> API untuk mencari berbagai macam jajanan tradisional yang ada di indonesia

<!-- == api == -->
## Endpoint

https://rusantara-api-tsbwg7l2lq-et.a.run.app


## Sign Up

Create new user

* URL
  * `/signup`

* Method
  * `POST`

* Content-Type
  * `application/json`

* Request Body
  * `username` as `string`
  * `email` as `string`
  * `password` as `string`

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

## Sign In

To Sign In, you need to add the Firebase client SDK to your project. For android studio. You also need to add firebase authentication to your app by using the firebase plugin on android studio. [Guide](https://drive.google.com/file/d/1IaDXdd9RcsF1tk1fsBpHK3JoVm4RsW7a/view?usp=sharing)

Once the step is done, in LoginActivity before the `onCreate` method, declare the firebase auth using:
```kotlin
private lateinit var auth: FirebaseAauth
```
inside the `onCreate` method, initialize the auth :
```kotlin
auth = FirebaseAuth.getInstance()
```
the code below is the example of a function to signIn with firebase auth
```kotlin
private fun loginUser(email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this){
            if (it.isSuccessful){
                Intent(this@LoginActivity, MainActivity::class.java).also {intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    val firebaseUser = auth.currentUser
                }
            } else {
                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
}
```

## Predict

Return json data about the food predicted.

* URL
  * `/predict`

* Method
  * `POST`

* Headers
  * `Content-Type` : `mulitpart/form-data`
  * `Authorization` : `Bearer <token>`

  To get the token, you need to run `getIdToken` on the currrent logged in user in the client side. Example code on how to get the token:
  ```kotlin
  private fun getToken() {
    val firebaseUser = auth.currentUser
    if (firebaseUser != null) {
        firebaseUser.getIdToken(false).addOnSuccessListener(OnSuccessListener<GetTokenResult> { result ->
            val idToken = result.token
            return idToken    
        })
    } else {
        return
    }
  }
  ```
  > You might want to run the function above everytime the client want to access the API where the token needed.

* Request Body
  * `image` as `file`

* Response
  ```json
  {
    "name": "clorot",
    "info": "Clorot adalah makanan khas yang terbuat dari adonan tepung beras dan gula merah yang di kukus. Makanan ini merupakan salah satu jajanan khas dari Purworejo, Jawa Tengah.",
    "ingredients": "175 gr gula merah, 400 ml santan, 1/4 sdt garam, 125 gr tepung beras, 50 gr tepung tapioka, Cetakan kue clorot dari daun pisang/daun kelapa",
    "nutrition": ""
  }
  ```

## Get All Foods

Return json data about all foods.

* URL
  * `/foods`

* Method
  * `GET`

* Headers
  * `Authorization` : `Bearer \<token\>`

* Response
```json
[
  {
    "name": "a",
    "info": "Lorem Ipsum",
    "ingredients": "Lorem Ipsum",
    "nutritions": "",
  },
  {
    "name": "b",
    "info": "Lorem Ipsum",
    "ingredients": "Lorem Ipsum",
    "nutritions": "",
  },
  {
    "name": "c",
    "info": "Lorem Ipsum",
    "ingredients": "Lorem Ipsum",
    "nutritions": "",
  },
  {
    "name": "d",
    "info": "Lorem Ipsum",
    "ingredients": "Lorem Ipsum",
    "nutritions": "",
  }
]
```

## Search For Foods

* URL
  * `/foods/{search query}`

* Method
  * `GET`

* Headers
  * `Authorization` : `Bearer \<token\>`

* Response
```json
[
  {
    "name": "a",
    "info": "Lorem Ipsum",
    "ingredients": "Lorem Ipsum",
    "nutritions": "",
  },
  ...
]
```

> The response can be multiple, single, or no data.
