var express = require('express');
var app = express();

// Static files
app.use('/bower_components', express.static(__dirname + '/bower_components'));
app.use('/templates', express.static(__dirname + '/templates'));
app.use('/style', express.static(__dirname + '/style'));

app.get('*', function (req, res) {
    res.sendFile(__dirname + '/templates/index.html');
});

var server = app.listen(3000, function () {
    var host = server.address().address;
    var port = server.address().port;

    console.log('Example app listening at http://%s:%s', host, port);
});