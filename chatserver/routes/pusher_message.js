var express = require('express');
var router = express.Router();

var Pusher = require('pusher');
var PUSHER_PUBLIC_CHANNEL = "public-chat-message";
var PUSHER_EVENT_CHAT_MESSAGE = "event-chat-message";

var pusher = new Pusher({
  appId: '278754',
  key: 'f5f06119dc9c4c5c2bcb',
  secret: '2617f6e308150321146f',
  encrypted: true
});

router.post('/', function(req, res){

  	console.log("message post");	
  	var userName = req.body.user_name;
  	var chatMessage = req.body.chat_message;
	var eventData = {
	                  user_name: userName,
	                  chat_message: chatMessage
	                };
	pusher.trigger( PUSHER_PUBLIC_CHANNEL, PUSHER_EVENT_CHAT_MESSAGE, eventData );

  	return res.json({success: 200});
});

module.exports = router;



