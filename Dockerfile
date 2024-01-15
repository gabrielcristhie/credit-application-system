# Use uma imagem base do OpenJDK
FROM openjdk:17-jre-slim

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o JAR construído para o contêiner
COPY build/libs/credit-application-system.jar app.jar

# Exponha a porta que sua aplicação está usando (pode variar dependendo da configuração do Spring Boot)
EXPOSE 8080

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "app.jar"]
