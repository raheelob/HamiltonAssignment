# Android Coding Challenge Raheel Masood

#The Brief
User selects the current and the target currency for the conversion and enters the amount to be converted. 
The calculated final amount is shown to the user and a timer starts which starts counting from 30. If the user wants
to make the conversion a confirmation alert is shown to the user and if confirmed a success view is 
shown to the user. If the transaction is not completed in 30 seconds user is asked to start from the beginning.

App data is refresh every 5 hours.

#Architecture and design.
Single Activity Architecture using:
1) MVVM + Clean
2) DI Using HILT.
3) Coroutine with flow
4) Channels and MutableStateFlow are used.
5) 2 way dataBinding.
6) Unit test of repository and view model included.
7) Navigation graph.
8) Error Handling, in case of no internet or error from network. Once the internet resumes, user can hit the request again.
9) In case user already has data once from the remote, it can use local cache to fetch the exchange rates.
10) Room Database in case no internet. (Module is added)

#General Logic for the code
Steps...
Views will call view model.
View model is injected with use case.
Use case makes a call to repository.
Repository returns the data to the view model.
View model returns the response to the view I.e fragment.
Local DB is updated every time after every 5 hours 

#Test:
#Unit Tests are included in test folder:
FetchExchangeRatesTest: Using mockwebserver to test network layer.
CurrencyViewModelTest: We are checking error and data events.

#AndroidTest:
EntityRoomDBTest: Simple read and write operations are tested in this folder. 
To run these test a device are required, since no Activity is attached, It will run quick.