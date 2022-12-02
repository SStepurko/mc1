# mc1

МС1 создает сообщение следующего формата:  
{
id: integer.
“session_id”: integer,
“MC1_timestamp”: datetime,
“MC2_timestamp”: datetime,
“MC3_timestamp”: datetime,
“end_timestamp”: datetime
}  
“session_id” - номер сеанса взаимодействия;  
записывает в поле “MC1_timestamp” текущее время и отправляет сообщение в МС2 через WebSocket;

МС1 принимает сообщение от МС3, записывает в поле“end_timestamp” текущее время, записывает сообщение в базу данных;  

повторить цикл взаимодействия в течение заданного интервала взаимодействия.  
Длительность интервала взаимодействия задается в секундах параметром в конфигурационном файле.  
В качестве БД использовать СУБД MariaDB. После остановки контейнеров с микросервисами и окружением база данных должна быть доступна для просмотра средствами СУБД.  
Запуск микросервисов и окружения производить в docker-compose.

Старт взаимодействия осуществить отправкой запроса GET на /start/ без параметров в МС1.
Досрочную остановку взаимодействия осуществить отправкой запроса GET на /stop/ без параметров в МС1.  
Начало взаимодействия микросервисов индицировать на консоль.  
Завершение взаимодействия индицировать на консоль с выводом следующих параметров:
1) время взаимодействия;
2) количество сообщений, сгенерированных во время взаимодействия.

# project structure

package:
- restservice
  - Message data entity connected to MariaDB Table;  
  Message to send
  - MessageRepository JPA class to use with DB
  - MessageService service to save and generate the new message
  - WebController servs enpoints
    * http://localhost:10001/api/start/ starts process
    * http://localhost:10001/api/stop/ stops process
    * /api used by microservice 3 for saving message
- service
  - AppConfigFileChecker check if config file (config/app.config) exist and can create it with default comments
  - ConfigReader get from config file properties
  - ProcessController main controller of sending process
  - Results stores and prints result of main process
- websocket
    - SendWebSocketMessage connects to microservice 2 and sends generated message

