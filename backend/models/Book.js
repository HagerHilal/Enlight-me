const mongoose = require('mongoose')
const ObjectId = mongoose.Schema.Types.ObjectId


const bookSchema  = mongoose.Schema({
    id :  {
        type : ObjectId
       },
    title  : String ,
    saved : Boolean,
    favourite : Boolean ,
    // applicants  : { type:[{type: mongoose.Schema.Types.ObjectId, ref: 'Member'}] } 
})


module.exports = mongoose.model('Book', bookSchema)