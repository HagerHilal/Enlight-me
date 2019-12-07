const mongoose = require('mongoose')
const Schema = mongoose.Schema
const ObjectId = mongoose.Schema.Types.ObjectId

const userSchema = new Schema({
    id  :
    {
     type : ObjectId
    },
    email: {type: String, required: true},
    name: {type: String,required: true},
    username :{type:String , required : true},
    password: {type: String,required: true},
    saved  : { type:[{type: mongoose.Schema.Types.ObjectId, ref: 'Book'}] } ,
    Favourite  : { type:[{type: mongoose.Schema.Types.ObjectId, ref: 'Book'}] } 
})
module.exports = User = mongoose.model('User', userSchema)