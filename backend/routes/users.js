const express = require('express')

const router = express.Router()

const validator = require('../Validations/userValidations')

router.use(express.json())
const USer = require('../models/User')


router.get('/all/:userName', async (req,res) => {
  const username=req.params.userName
  const user = await User.findOne({username})
  if(!user) return res.status(404).send({error: 'User does not exist'})
  res.json({data: user})
});
router.get('/all', async (req, res) => {

  const users = await User.find()
  res.json({data: users})

});



router.get('/username/:username' , async(req , res) => {
  const userName  = req.params.username;

  const found = await User.findOne({ "username": userName });
  if (found)
    return res.status(400).send(found._id);
});


router.patch('/save/:userid' , async (req , res )=> {
bookid = req.body.id;
found = await User.findOne({ "_id": req.params.userid });
if ( found ){
  found.saved.push(req.params.bookid);
  return res.status(400).send(found.saved);
}
}
);   








// create a new member and add it to the database

router.post('/', async (req, res) => {

  try {   
    const isValidated = validator.createValidation(req.body)
    console.log(req.body)
    if (isValidated.error) { console.log(isValidated.error.details[0].message)
      return res.status(400).send({ error: isValidated.error.details[0].message })}
    const username = req.body.username;
    const found = await User.findOne({ "username": username });
    if (found)
      return res.status(400).send({ error: "username is already in use" });
    else {
      const email = req.body.email;
      const found = await User.findOne({ "email": email });
      if (found)
        return res.status(400).send({ error: "email is already in use" });
      else {
        const newUser = await User.create(req.body)         
        res.json({ msg: 'Member was created successfully', data: newUser })
      }
    }
  }
  catch (error) {
    // We will be handling the error later
    console.log(error)
  }

});

router.get("/:id"  , async (req, res) => {
  const id = req.params.id;
    User.findById(id)
    .exec()
    .then(r => {
      if (r) {
        res.status(200).json(r);
      } else {
        res
          .status(404)
          .json({ message: "Error" });

      }
    })
});
   
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