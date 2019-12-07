const Joi = require('joi');
const express = require('express')
const mongoose = require('mongoose')
const MongoClient = require('mongodb').MongoClient;
const users = require('./routes/users')
const books = require('./routes/books')
const app = express()

const db = require('./config/keys').mongoURI
// console.log(db);
mongoose
   .connect(db,{useNewUrlParser: true})
    // .connect(db)
    .then(() => console.log('Connected to MongoDB'))
    .catch(err => console.log(err))


    process.on('uncaughtException', function (err) {
      console.log(err);
  }); 

  app.get('/', (req, res) => {

    res.send(`<h1>♥ Welcome to Enlight-me ♥</h1>
    <a href="/users">Users</a>
    <a href="/books">Books</a>
    `);
  });

  app.use('/books', books),
  app.use('/users', users)

  app.use((req, res) => {

    res.status(404).send({ err: "We can not find what you are looking for" });
  
  });

  const port = process.env.PORT || 3000
  app.listen(port, () => console.log(`Server on ${port}`))
  
    
  
   
