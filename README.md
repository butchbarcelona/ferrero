# Ferrero

## Setup of server

Run 
```
npm install
```
when inside the server directory.

And then run the NodeJS server by typing 

```
node app.js
```
in the command line. Your REST services can be called in your localhost through port 8081. 


## Services
#### User

* Get User List 
```
/user/list
```

* Add User 
```
/user/add?name=<name>&id=<id>
```

* Get User data
```
/user/<id>
```

* Delete User
```
/user/delete?id=<id>
```

#### Logs
* Get Logs List
```
/logs
```

You can query logs by adding the parameters (date is in epoch format)
```
/logs?user=<user>&date=<date>
```

* Add logs 
```
/logs/add?name=<userid>&desc=<desc>
```

