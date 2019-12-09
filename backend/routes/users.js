const express = require('express')

const router = express.Router()

const validator = require('../Validations/userValidations')

router.use(express.json())
const User = require('../models/User')


// create a new member and add it to the database



router.get('/all', async (req, res) => {

  const users = await User.find()
  res.json({data: users})

 
});

router.post('/user', async (req,res) => {
  try {
   const isValidated = validator.createValidation(req.body)
   if (isValidated.error) return res.status(400).send({ error: isValidated.error.details[0].message })
  const email = req.body.email
   var user = await User.findOne({email})
  if(user) return res.status(400).json({error: 'Email already exists'})
 
  const username = req.body.username
    user = await User.findOne({username})
  if(user) return res.status(400).json({error: 'username already exists'})
 
  
   const newUser = await User.create(req.body)
   res.json({msg:'User was created successfully', data: newUser})
  }
  catch(error) {
      console.log(error)
  }  
})


// router.get("/:id"  , async (req, res) => {
//   const id = req.params.id;
//     User.findById(id)
//     .exec()
//     .then(r => {
//       if (r) {
//         res.status(200).json(r);
//       } else {
//         res
//           .status(404)
//           .json({ message: "Error" });

//       }
//     })
// });
router.get('/all/:userName', async (req,res) => {
  const username=req.params.userName
  const user = await User.findOne({username})
  if(!user) return res.status(404).send({error: 'User does not exist'})
  res.json({data: user})
})
   
/*
router.put('/:_id', async (req, res) => {
  const id = req.params._id;
  console.log("in put")
  const admin = await Admin.findById(req.user.id )
  let id2 = '';
  if (admin)
  id2 = admin._id
  const id3=req.user.id 
  console.log("put request")
  console.log(req.user.id) ;
  console.log(id2);
  console.log(req.params._id);
  if(id3 == id2 || id3 == id) {
  try {
       const isValidated = validator.updateValidation(req.body)
    if (isValidated.error) return res.status(400).send({ error: isValidated.error.details[0].message })

    const username = req.body.userName;
    const found = await Member.findOne({ "userName": username });
    if (found)
      return res.status(400).send({ error: "username is already in use" });
    else {
      const email = req.body.email;
      const found = await Member.findOne({ "email": email });
      if (found)
        return res.status(400).send({ error: "email is already in use" });
      else {
        const updated = await Member.findByIdAndUpdate(req.params._id, req.body)
        return res.send(updated)
      }}
    }
  catch (err) {
    res.send(err)
  }
}
else {
  res.json("Not authenticated")
}
});

*/

router.delete('/:_id' , async (req, res) => {
  const id = req.params._id;
  try { 
     const deletedUser = await User.findOneAndDelete ({ _id : req.params._id})
     res.json({msg :'Member deleted successfully' , data : deletedMember})
    }
  catch (error) {
    console.log(error)
  }});


module.exports = router;