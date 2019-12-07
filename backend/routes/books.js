const express = require('express')

const router = express.Router()


router.use(express.json())


router.get("/:id"  , async (req, res) => {
  const id = req.params.id;
    Book.findById(id)
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
module.exports = router;