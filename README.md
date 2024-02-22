#### Kotlin / Spring Boot SDK Embedding Example
This repository replicates an example backend that will play well with the Metabase Embedding SDK. We've used Kotlin and Spring Boot for this example to show that the Metabase SDK doesn't require any specific backend infrastructure, but rather just one endpoint.

## Set up Metabase
#### Enable SSO with JWT

From any Metabase page, click on the **gear** icon in the upper right and select **Admin Settings** > **Settings** > **Authentication**.

On the card that says **JWT**, click the **Setup** button.

#### JWT Identity provider URI

In **JWT IDENTITY PROVIDER URI** field, paste  `localhost:8081/login`.

#### String used by the JWT signing key

Click the **Generate key** button. Copy the key.


## Setup the backend
Ensure that you've cloned this repository to your local machine first.

### (Recommended) IntelliJ IDEA
This method is _by far_ the easiest (due to JetBrains creating IntelliJ and Kotlin). You'll need to install IntelliJ, then use `Open an existing project` at the root of the local version of this repo. Then, go to:
```
src/main/kotlin/com/metabase/mbkotlinspringexample/MbSdkJavaBeExampleApplication.kt
```

You'll see a green play button to the left of `fun main`. Click that, and click `Run MbSdkJavaBeExampleApplication`.
<img width="511" alt="image" src="https://github.com/metabase/sdk-kotlin-spring-be-example/assets/25306947/2745719a-bff0-436b-bfe7-954fa3cea55d">

_Then, go to **Making sure it works**_


### Command Line
###### Installing Gradle
Ensure that you have the `gradle` package, which you can download from the gradle website. Alternatively, on MacOS, you can install the package with `brew` using 
```
brew install gradle
```
Check that your installation was successful by running:
```
gradle -v
```
It should give you some information on its version if installed successfully.


### Running the app

Now, just run `gradle build` to build the app, then run `gradle run`. 



## Making sure it works
Go to `localhost:8081`. The `/` endpoint will redirect you to `localhost:3004` if the server is working correctly.

## Setting up environment variables
You'll want to make sure that your environment variables are set up to connect with your frontend application and your Metabase instance. 

`METABASE_CLIENT_APP_URL`
This URL should point to your frontend application. This app uses this variable to make sure that CORS is set up properly to accept requests from your FE.

`METABASE_SITE_URL`
Your Metabase instance's URL

`METABASE_JWT_SHARED_SECRET`
The JWT shared secret that you can find in your Metabase Admin Settings, in 
```
Settings > Authentication > JWT > String used by the JWT signing key
```
<img width="885" alt="image" src="https://github.com/metabase/sdk-kotlin-spring-be-example/assets/25306947/a03e4446-e415-459c-b8d5-53f001768c02">

If you want to hardcode your variables, go to `application.properties` to set them.
