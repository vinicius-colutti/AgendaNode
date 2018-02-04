var app = require('express')();
var http = require('http').createServer(app);
var mysql = require('mysql');
var lstItem = [];

//CONEXÃO mysql
var con = mysql.createConnection({
  host: "127.0.0.1",
  user: "root",
  password: "bcd127",
  database: "dbnode"
});

con.connect(function(err){
    if (err) throw err;
    console.log("Connected!");
});

//INDEX
app.get('/', function(req, res){
  res.sendFile( __dirname + "/index.html");
});

//SELECT
app.get('/mostrar', function(req, res){
  con.query("SELECT * FROM tbl_node", function (err, result, fields) {
      if (err) throw err;
      res.send(result);
    });
});

//INSERT
app.get('/inserir', function(req, res){
  var _nome = req.query.nome;


  con.query("INSERT INTO tbl_node (nome) values('"+ _nome + "')", function (err, result, fields){
    if(err) throw err;
    res.send("Inserido com sucesso!")
  });
});

//DELETE
app.get('/excluir', function(req, res){
  var _id = req.query.id;
  con.query("DELETE FROM tbl_node WHERE id_node="+_id, function(err, result, fields){
    if(err) throw err;
    res.send("Deletado com sucesso!");
  });
});

//UPDATE
app.get('/editar', function(req, res){
  var _id = req.query.id;
  var _titulo = req.query.nome; 
  con.query("UPDATE tbl_node SET nome='"+_nome+"' WHERE id_node="+_id, function(err, result, fields){
    if(err) throw err;
    res.send("Atualizado com sucesso");
  });
});

//PORTA
http.listen(8888, function(){
   console.log("Servidor rodando na porta 8888");
});
