# -*- coding: utf-8 -*-
from websocket import create_connection
ws = create_connection("ws://localhost:444/websockets/uppercasemaker")
ws.send("hello\nahoj")
message = ws.recv()
print(message)
ws.close()