# Read Me First

* [Spring Initializr](https://start.spring.io/#!type=gradle-project-kotlin&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.felipeborba&artifactId=webTemplate&name=webTemplate&description=Demo%20project%20for%20Spring%20Boot&packageName=com.felipeborba.webTemplate&dependencies=web,lombok,h2,postgresql,data-jpa,devtools,validation,security,oauth2-resource-server)

# Como gerar uma nova chave privada e publica para o spring security
```shell
openssl genrsa > private.pem
```
```shell
openssl rsa -in private.pem -pubout -out public.pem
```
