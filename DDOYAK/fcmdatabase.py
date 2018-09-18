from firebase import firebase
from pyfcm import FCMNotification
import json

firebase = firebase.FirebaseApplication("https://test3-2816e.firebaseio.com/", None)
result = firebase.get('/',None)

#print(result["test2"]["koreainbae"])

rawtoken = firebase.get("/TOKEN",None)
token = rawtoken.replace('"',"",2)
#print(token)

push_service = FCMNotification(api_key="AAAA3fpRKgo:APA91bF7T-1-jeKCG-4dm05_LLvHajS3YjXlQLTZY_HyUjzbNW-KHvc9jZFcpVwJ9gViMhfj_6L2Gd3Z9662UDOAznDiuk_88as-lsN6DZULDkbI8NpLSd4-sSExfQEBuVtx_qi8Z1Z0C-vB3FD_j2xktHpqFC6B7w")
data_message={
    "message_body":"Hello2",
}

result = push_service.notify_single_device(registration_id=token,message_body="Hello2",data_message=data_message)
#print(result)
result2 = firebase.patch("/OUTING",{"o2" : ["s#2018#08#22#09#00", "e#2018#08#22#18#00"]})
#print(result2)