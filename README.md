- Install mongoDB
For instance, installing mongoDb for mac, see https://docs.mongodb.org/manual/tutorial/install-mongodb-on-os-x/

- Running on Jetty:
./gradlew jettyRun

- To show all customers:
http://localhost:7777/rsm/rest-api/customers

- Use curl to add customers:
curl -H "Content-Type: application/json" -X POST -d '[{"id":"1", "name":"Monica"},{"id":"2", "name":"Chandler"}]' http://localhost:7777/rsm/rest-api/customers
