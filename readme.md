Back-end Assignment 

    Technology used: spring boot, mySQL, JPA, JDK 1.8 Verion
    IDE Used: intelliJ IDEA
    
    NOTE:
    Autthentication & authorization part is completely ignored for this 
    project as I focused only the requirement mentioned for this assignment.
    
    Validation & custom exception handling used.
    
    
    Requirements in a brief:
    Deposit, Withdrawal, transfer, Transaction history and Transaction history filters.
    
    *******************************
    Need to create db in MySQL Server:-
    Name: banking
    After db creation, need to change the username & password from application.properties file
    according to your machine.
    *******************************
    
    To perform each of above operations, first need to create bank account. 
    so I created few bank accounts by using below api:
    
    request Type: POST
    url: localhost:8088/api/v1/account/create
    request Body: 
    {
    	"accountNumber":"1001",
    	"currentBalance":50000
    }
    and 
    {
    	"accountNumber":"2001",
    	"currentBalance":5000
    }
    
    Deposit:
        request Type: POST
        url: localhost:8088/api/v1/account/deposit
        request Body:
    {
    	"accountNumber":"1001",
    	"currentBalance":500
    }
    {
    	"accountNumber":"2001",
    	"currentBalance":1800
    }    
    {
    	"accountNumber":"2001",
    	"currentBalance":800
    }        
   
   Above we performed few deposit operation. Each deposit operation record stored in 
   transaction table so that we can trace.
   
    Withdraw:
        request type: POST
        url: localhost:8088/api/v1/account/withdraw
        request body:
        {
        	"accountNumber":"2001",
        	"currentBalance":1000
        }
        {
        	"accountNumber":"1001",
        	"currentBalance":1000
        }
        {
        	"accountNumber":"1001",
        	"currentBalance":5000
        }
    Here above in withdraw, few withdrawal operation was performed. here also each 
    withdrawal operation stored in transaction table.
    
    Transfer:
        request type: POST
        url: localhost:8088/api/v1/account/transfer
        request body:
        {
            "fromAccountNumber":"1001",
            "toAccountNumber":"2001",
            "amount":2500
        }
    In above transfer operation, also each transfer stored in transaction table.
    
    
    Statement:
    After performing all above operation's the sample statement for 
    the account number: 1001
        request type: GET
        url: localhost:8088/api/v1/account/statement/1001
        Sample Output:
        {
            "status": "OK",
            "payload": {
                "currentBalance": 42000,
                "transactionHistory": [
                    {
                        "transactionId": 3,
                        "accountNumber": "1001",
                        "transactionType": "TRANSFER",
                        "transactionAmount": 2500,
                        "transactionDateTime": "2022-07-06T07:19:23.000+00:00"
                    },
                    {
                        "transactionId": 5,
                        "accountNumber": "1001",
                        "transactionType": "WITHDRAW",
                        "transactionAmount": 5000,
                        "transactionDateTime": "2022-07-06T07:20:23.000+00:00"
                    },
                    {
                        "transactionId": 6,
                        "accountNumber": "1001",
                        "transactionType": "WITHDRAW",
                        "transactionAmount": 1000,
                        "transactionDateTime": "2022-07-06T08:08:19.000+00:00"
                    },
                    {
                        "transactionId": 10,
                        "accountNumber": "1001",
                        "transactionType": "DEPOSIT",
                        "transactionAmount": 500,
                        "transactionDateTime": "2022-07-06T08:09:46.000+00:00"
                    }
                ]
            }
        }
        
    Thank you!
            
   
  
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
    
    