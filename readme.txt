

1) mvn clean install 
   to build in directory with root pom.xml

2) to start server; do the following
   from project root directory:

(assumes you are using correct *.yml Server configuration; 
 and have told JVM to start Server on debug port 5005;
 and made the *.bsh file executable)

java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar target/WarmupREST-1.0-SNAPSHOT.jar server WarmupREST.yml

./debug.bsh

3) to start the debugger; select the RemoteHelloBaby Configuration;
   then hit DEBUG toolbar button

(assumes you have setup IntelliJ Remote Debug configuration to connect to port 5005)

for running remote JVM, then connection on localhost port 5005
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005

4) hit GET on Service from localhost browser:

localhost:8080/baby/talk

should expect to see the following JSON:
{"id":1,"content":"Biyau, Biyau Mama!"}

5) THEN, add some query params with HTML escape char for space for:

curl GET 'http://localhost:8080/baby/talk?saying=What%20Happened&person=grandma'

{"id":3,"content":"What Happened, grandma!"}

***NOTE:  On browser, can just pass in URL; but on CMD-LINE with CURL, need to put single quotes around URL so that all params will be recognized!

6) THEN, GET latest state of baby

curl http://localhost:8080/baby/view

Should expect to see this JSON content:

{"id":1,"countSayings":0,"latestSaying":{"id":1,"content":"Biyau, Biyau"},"weightInPounds":"18","isDiaperDirty":false}

7) THEN, change state idempotently with a PUT: 
curl -i -H "Content-Type: application/json" -X PUT -d @change-diaper.json http://localhost:8080/baby/change-diaper  

*** NOTE:  *.json format MATCHes DUMP of elements from GET

HTTP/1.1 200 OK
Date: Tue, 26 Nov 2013 19:34:14 GMT
Content-Type: application/json
Transfer-Encoding: chunked

{"id":9,"content":"Thank you for change-diaper, mama!!"}

8) THEN, GET latest state of baby

Should expect to see this JSON content:

{"id":1,"countSayings":9,"latestSaying":{"id":1,"content":"Thank you for change-diaper, mama!!"},"weightInPounds":"18","isDiaperDirty":false}i

9) TODO:  FIX to use NOUN-attributes in URL and NOT verbs to change state
   TODO:  ADD support for returning COLLECTIONS

10) 

