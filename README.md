# Book a cinema ticket service
Demo application for booking tickets

## Additional functions 
- Reservation confirmation 
- Different ticket prices 

## Demo

### Disclaimer
- Because of data base Id generation strategy h2 file data base is included to repository. (it simplified demo creation)
- Demo script uses json_pp witch prints json in pretty format but fields are in random order. 
- To run demo u can also use postman using *./demo/in booking_app.postman_collection.json*
	
### Requirements
- bash console
- java JDK 11 
	
### How run
- run *./demo/build.sh* to build application (this may take a few minutes)
- run *./demo/run.sh* to run application( to stop app press ctrl+c  on run.sh window )
- run *./demo/demo_happy_path.sh* for demo
To finish your reservation you will have to copy url from last response to your browser.
	
#### Hints
- run *./demo/clear_all_reservations.sh* to clear all reservations
- modify *./demo/user_reservation_details.json* and *demo_happy_path.sh* to customize demo
	
	
## Todo
- Domain value objects for Money and Time instead of BigDecimal and LocalDate/Time
- E2E Test for demo happy path
- REST API Swagger documentation
- Repositories interface segregation see:[ReservationRepository]
- Customize Exception instead of using error()
- User should get information about Voucher validity before post reservation       
- Define cinema management subdomain and use it to initialize demo data  
- forest.FixedIdEntity - don't persist uuid you have id   
- RODO - when asking for user name and surname
- Demo Json pretty print - prints fields in random order   
- Seat recommendation feature
	
## Licence
This project is licensed under the WTFPL License.
    
## Author
Marcin Lesiński
