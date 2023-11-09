# Eureka discovery server
## Minimalna konfiguracja application.yaml, dla tworzonych serwisów
```yaml
spring:
  application:
    name: {TUTAJ_NAZWA_SERWISU}
eureka:
  client:
    healthcheck:
      enabled: true
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/ #adres endpointu na który serwisy się komunikują, aby się zajerestrować 
```
## Wymagane zależności
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
## Spring gateway
Konfiguracja ścieżek w module `gateway-service`, klasa `GatewayConfig`.