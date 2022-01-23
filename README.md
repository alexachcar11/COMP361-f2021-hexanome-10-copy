# f2021-hexanome-10

[Link to shared Google Doc folder](https://drive.google.com/drive/folders/19WheCCSq9KSggepEXmfFJuBdx57oNWcC?usp=sharing)



To setup LobbyService the first time: mvn -f LobbyService-master/pom.xml package

Start docker: docker start ls-db

Start the lobby service: mvn clean package spring-boot:run

Test the lobby service connection: http://127.0.0.1:4242/api/online


Potential chat GUI?: https://www.guru99.com/java-swing-gui.html
