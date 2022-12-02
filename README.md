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

