Do as many tasks as possible from the following list


# Fullstack (frontend + backend)
* Get the app to run on your local machine  
--> done, Debugging set up
* There are bugs - find and fix them  
--> bug in host and port declaration for frontend to allow CORS in property file (removed underscores)
--> Endless loop in frontend result.tsx: Added useEffect Statement to API-Call
* To display better ads we need more data - extend the form with more inputs and store the data in the database  
--> Added favourite Animal und Zodiac Sign to provide targeted ads
* At the moment anyone can use our app - implement authentication with a registration process  
--> backend done, frontend done.
* Filling in the same data over and over again is annoying - prefill some of the form from the login data  
--> done
* Who wrote that code? - Improve the code quality  
--> **TBD**

# Frontend
* The cancel button does not do anything - implement it  
--> done
* Navigation is bad - add a back to start link on all pages  
--> Home Button in Header
* Why can I enter my name in the phone input? - Restrict the input to only allow E.164 phone numbers  
-->
* The text "Game" might not be a game - create a game the user has to win to get to the price
* We need to proudly present our company - add a header banner with our company logo and a link to our website  
--> Done
* Who implemented that ugly UI? - make it beautiful  
--> Added Footer


# Backend
* To get more people to use our app we need a good jackpot - add an entity + table for the price and fill it with different jackpots during the next startup
* Fetch a random Jackpot
* Something is wrong with the JackpotHistory - save formdata and a reference to the jackpot entity in the database
* Be gone hackers! - Verify in the backend if the user is allowed to get a jackpot
* The user can just fill any game in the frontend and the result is lost - add the full mvc for the game inputs
* What is currently broken and what is working? - Implement some junit tests
