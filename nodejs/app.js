const PORT = 3000;
const HOST = 'localhost';

var express = require('express');
var http = require('http');
var app = express();

var server = http.createServer(app).listen(3000);
const io = require('socket.io');
socket = io.listen(server);
const redis = require('redis');
const client = redis.createClient();



//app.listen(PORT);
console.log("Express server listening on port 3000");

app.get('/', function(req, res) {
	res.sendfile(__dirname + '/html/index.html');
    });

app.get('/js/jquery-1.9.1.min.js', function(req, res) {
	res.sendfile(__dirname + '/html/js/jquery-1.9.1.min.js');
    });

app.get('/css/style.css', function(req, res) {
	res.sendfile(__dirname + '/html/style.css');
    });
    
app.get('/icon.png', function(req, res) {
	res.sendfile(__dirname + '/html/icon.png');
    });

socket.on('connection', function(client) {
	const subscribe = redis.createClient();
	subscribe.subscribe('location');
	subscribe.on("message", function(channel, message){
		client.send(message);
	    });
	
	client.on('message', function(channel, message){
		client.send(message);
	    });
	
	client.on('message', function(msg){
		client.send(message + '!!!!');
	    });

	client.on('disconnect', function() {
		subscribe.quit();
	    });
    });
