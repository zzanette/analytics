# Analytics
Analytics is a Rest Service thats analysis data from files stored in *$HOMEPATH/data/in* and put the final analysis to *$HOMEPATH/data/out*. You can add the files manualy to the folder or you can send an file to analyse calling the endpoints to store or search files.

### File data struture
---
 **Salesman data format**: 001çCPFçNameçSalary
 
 **Customer data format:** 002çCNPJçNameçBusiness Area
 
 **Sales data format:** 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

```txt
001ç1234567891234çPedroç50000
001ç3245678865434çPauloç40000.99
002ç2345675434544345çJose da SilvaçRural
002ç2345675433444345çEduardo PereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
```

 ### Endpoints
 ---
 **GET** /files/{filename}
 
 Response:
 ```txt
Quantity customers: 2
Salesman quantity: 3
Most expensive sale: 10
Worst seller: Paulo
 ```
Return the file analysed.


**POST** /files
Content-Type=multipart/form-data;
body:
| key  |   value   |
|------|-----------|
| file | <THE-FILE>|

Post an file to be analysed.

**Note:** You can put the files manuly in *$HOMEPATH/data/in* and the files will be analysed equaly.
### How to run
---

#### Pre-requirements
* [Dcoker](https://docs.docker.com/desktop/) _(if you want to run over container)_
* [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

#### Running with gradlew
```shell
$ ./gradlew bootRun
```

#### Running with docker
```shell
$ ./gradlew clean build
$ docker build -t analytics-img .
$ docker run -p 8080:8080 --name analytics analytics-img
```
#### How to run Unit Tests
```shell
$ ./gradlew clean test
```

### To do
---
* Integration test
* Docs
